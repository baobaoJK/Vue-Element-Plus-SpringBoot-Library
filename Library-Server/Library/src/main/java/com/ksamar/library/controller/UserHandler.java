package com.ksamar.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksamar.library.entitys.User;
import com.ksamar.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserHandler {
    /**
     * 用户数据库操作接口
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 获取用户数据
     * @param page 页码
     * @param size 大小
     * @return 用户数据
     */
    @GetMapping("/{page}/{size}")
    public Page<User> getPageUser(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(pageable);
    }

    /**
     * 用户信息搜索
     * @param text 搜索信息
     * @return 用户信息
     */
    @GetMapping("/search/{type}/{text}/{page}/{size}")
    public Page<User> findUser(@PathVariable("type") String type,
                               @PathVariable("text") String text,
                               @PathVariable("page") Integer page,
                               @PathVariable("size") Integer size) {
        Page<User> userPage;
        PageRequest pageable = PageRequest.of(page - 1, size);
        switch (type) {
            case "username":
                userPage = userRepository.findByUserNameLike(text, pageable);
                break;
            case "idCard":
                userPage = userRepository.findByIdCardLike(text, pageable);
                break;
            case "phone":
                userPage = userRepository.findByPressLike(text, pageable);
                break;
            default:
                userPage = null;
                break;
        }

        return userPage;
    }

    /**
     * 添加用户
     * 0 - 添加用户失败
     * 1 - 添加用户成功
     * 2 - 用户名存在
     * 3 - 借书卡存在
     * 4 - 手机号存在
     * @param user 用户信息
     * @return 添加用户状态
     */
    @PostMapping("/save")
    public JSONObject save(@RequestBody User user) {
        JSONObject userMessage = new JSONObject();
        userMessage.put("message", "addUser");
        userMessage.put("statusCode", 0);
        User tempUser;

        // 判断用户是否为空
        if (user != null) {
            tempUser = userRepository.findByUsername(user.getUsername());
            // 判断用户名是否存在
            if (tempUser == null) {
                tempUser = userRepository.findByIdCard(user.getIdCard());
                // 判断借书卡是否存在
                if (tempUser == null) {
                    tempUser = userRepository.findByPhone(user.getPhone());
                    // 判断手机号是否存在
                    if (tempUser == null) {
                        // 设置用户信息
                        Integer bookCount = 3;
                        String userIdentity = user.getIdentity();
                        if ("教师".equals(userIdentity)) {
                            bookCount = 5;
                        }
                        if ("管理员".equals(userIdentity)) {
                            bookCount = 999;
                        }
                        user.setBookCount(bookCount);
                        user.setState(1);

                        // 添加用户
                        userRepository.save(user);
                        userMessage.replace("statusCode", 1);
                    }
                    else {
                        userMessage.replace("statusCode", 4);
                    }
                }
                else {
                    userMessage.replace("statusCode", 3);
                }
            }
            else {
                userMessage.replace("statusCode", 2);
            }
        }

        return userMessage;
    }

    /**
     * 编辑用户
     * 0 - 编辑用户失败
     * 1 - 编辑用户成功
     * 2 - 手机号存在
     * @param user 用户信息
     * @return 编辑用户状态
     */
    @PostMapping("/update")
    public JSONObject edit(@RequestBody User user) {
        JSONObject userMessage = new JSONObject();
        userMessage.put("message", "editUser");
        userMessage.put("statusCode", 0);
        User tempUser;

        // 判断用户是否为空
        if (user != null) {
            tempUser = userRepository.findByPhone(user.getPhone());
            // 判断手机号是否存在
            if (tempUser == null || user.getId().equals(tempUser.getId())) {
                // 添加用户
                userRepository.save(user);
                userMessage.replace("statusCode", 1);
            } else {
                userMessage.replace("statusCode", 2);
            }
        }

        return userMessage;
    }

    /**
     * 删除用户
     * 0 - 删除失败
     * 1 - 删除成功
     * 2 - 删除失败 Id 不存在
     * @param id Id
     * @return 删除用户信息
     */
    @PostMapping("/delete/{id}")
    public JSONObject delete(@PathVariable("id") Integer id) {
        JSONObject userMessage = new JSONObject();
        userMessage.put("message", "deleteUser");
        userMessage.put("statusCode", 0);

        // 判断用户是否存在
        boolean book = userRepository.existsById(id);
        if (book) {
            userRepository.deleteById(id);
            userMessage.replace("statusCode", 1);
        }
        else {
            userMessage.replace("statusCode", 2);
        }

        return userMessage;
    }

    /**
     * 修改管理员密码
     * @param user 管理员密码信息
     * @return 修改信息
     */
    @PostMapping("/update/admin")
    public JSONObject updateAdmin(@RequestBody User user) {
        JSONObject updateMessage = new JSONObject();
        updateMessage.put("message", "updatePassword");
        updateMessage.put("statusCode", 0);

        // 判断用户信息是否为空
        if (user != null) {
            Optional<User> optionalUser = userRepository.findById(1);
            // 判断用户是否存在
            if (optionalUser.isPresent()) {
                // 设置密码
                User admin = optionalUser.get();
                admin.setPassword(user.getPassword());

                userRepository.save(admin);
                updateMessage.replace("statusCode", 1);
            }
        }

        return updateMessage;
    }

    /**
     * 用户名搜索用户
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/get/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 修改用户密码
     * @param jsonObject 用户密码信息
     * @return 修改信息
     */
    @PostMapping("/update/user/{username}")
    public JSONObject updateUser(@RequestBody JSONObject jsonObject,
                                  @PathVariable("username") String username) {
        JSONObject updateMessage = new JSONObject();
        updateMessage.put("message", "updatePassword");
        updateMessage.put("statusCode", 0);

        // 判断用户信息是否为空
        if (jsonObject != null) {
            User user = userRepository.findByUsername(username);
            // 判断用户是否存在
            if (user != null) {
                // 判断密码是否一致
                if (user.getPassword().equals(jsonObject.get("oldPassword"))) {
                    // 修改密码
                    user.setPassword(String.valueOf(jsonObject.get("newPassword")));
                    userRepository.save(user);
                    updateMessage.replace("statusCode", 1);
                }
                else {
                    updateMessage.replace("statusCode", 2);
                }
            }
        }

        return updateMessage;
    }
}