package com.ksamar.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksamar.library.entitys.Book;
import com.ksamar.library.entitys.Borrow;
import com.ksamar.library.entitys.Log;
import com.ksamar.library.entitys.User;
import com.ksamar.library.repository.BookRepository;
import com.ksamar.library.repository.BorrowRepository;
import com.ksamar.library.repository.LogRepository;
import com.ksamar.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 借阅图书控制器
 * @author KSaMar
 */
@RestController
@RequestMapping("/borrow")
public class BorrowHandler {

    /**
     * 借阅图书数据库操作接口
     */
    @Autowired
    BorrowRepository borrowRepository;

    /**
     * 图书数据库操作接口
     */
    @Autowired
    BookRepository bookRepository;

    /**
     * 用户数据库操作接口
     */
    @Autowired
    UserRepository userRepository;

    /**
     * 日志数据库操作接口
     */
    @Autowired
    LogRepository logRepository;

    /**
     * 借阅图书
     * @param isbn ISBN号码
     * @param user 用户信息
     * @return 借阅信息
     */
    @PostMapping("/{isbn}")
    public JSONObject borrowBook(@PathVariable("isbn") String isbn, @RequestBody User user) {
        JSONObject borrowMessage = new JSONObject();
        borrowMessage.put("message", "borrowBook");
        borrowMessage.put("statusCode", 0);

        // 判断信息是否为空
        if (isbn != null && user != null) {
            Book book = bookRepository.findByIsbn(isbn);
            // 判断图书是否存在
            if (book != null) {
                // 判断图书数量
                if (book.getQuantity() > 0) {
                    User tempUser = userRepository.findByIdCard(user.getIdCard());
                    // 判断用户是否存在
                    if (tempUser != null) {
                        // 判断用户信息
                        if (user.getName().equals(tempUser.getName()) && user.getPhone().equals(tempUser.getPhone())) {
                            // 判断用户可借阅数量
                            if (tempUser.getBookCount() > 0) {
                                // 设置时间
                                Calendar calendar =Calendar.getInstance();
                                Date borrowTime = calendar.getTime();
                                calendar.add(Calendar.DATE, 7);
                                Date returnTime = calendar.getTime();

                                // 设置借阅信息
                                Borrow borrow = new Borrow();
                                borrow.setBookName(book.getName());
                                borrow.setIsbn(book.getIsbn());
                                borrow.setUsername(tempUser.getName());
                                borrow.setIdCard(tempUser.getIdCard());
                                borrow.setPhone(tempUser.getPhone());
                                borrow.setBorrowTime(borrowTime);
                                borrow.setReturnTime(returnTime);

                                // 借阅图书
                                Borrow result = borrowRepository.save(borrow);
                                // 判断借阅是否成功
                                if (result.getId() > 0) {
                                    borrowMessage.replace("statusCode", 1);
                                    // 图书数量自减
                                    book.setQuantity(book.getQuantity() - 1);
                                    // 用户借阅数量自减
                                    tempUser.setBookCount(tempUser.getBookCount() - 1);

                                    // 设置日志信息
                                    Log log = new Log();
                                    log.setTime(borrowTime);
                                    log.setName(tempUser.getName());
                                    log.setBookName(book.getName());
                                    log.setInfo("借走了 " + book.getName() + " 书籍");

                                    // 添加日志
                                    logRepository.save(log);
                                }
                            }
                            else {
                                borrowMessage.replace("statusCode", 4);
                            }
                        }
                        else {
                            borrowMessage.replace("statusCode", 3);
                        }
                    }
                    else {
                        borrowMessage.replace("statusCode", 3);
                    }
                }
                else {
                    borrowMessage.replace("statusCode", 2);
                }
            }
        }

        return borrowMessage;
    }

    /**
     * 借阅图书信息
     * @param page 页码
     * @param size 大小
     * @return 借阅信息
     */
    @GetMapping("/{page}/{size}")
    public Page<Borrow> getPageBook(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return borrowRepository.findAll(pageable);
    }

    /**
     * 搜索IdCard借阅信息
     * @param idCard IdCard
     * @param page 页码
     * @param size 大小
     * @return 借阅信息
     */
    @GetMapping("/search/{idCard}/{page}/{size}")
    public Page<Borrow> findBorrow(@PathVariable("idCard") String idCard,
                                   @PathVariable("page") Integer page,
                                   @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return borrowRepository.findByIdCard(idCard, pageable);
    }

    /**
     * 归还图书
     * 0 - 归还失败
     * 1 - 归还成功
     * 2 - 借阅失败 借阅记录不存在
     * @param id Id
     * @return 归还图书信息
     */
    @PostMapping("/return/{id}")
    public JSONObject returnBook(@PathVariable("id") Integer id) {
        JSONObject returnMessage = new JSONObject();
        returnMessage.put("message", "returnBook");
        returnMessage.put("statusCode", 0);

        // 判断借阅记录是否存在
        Optional<Borrow> borrow = borrowRepository.findById(id);
        if (borrow.isPresent()) {
            // 用户图书借阅数量增加
            User user = userRepository.findByIdCard(borrow.get().getIdCard());
            user.setBookCount(user.getBookCount() + 1);
            // 图书数量增加
            Book book = bookRepository.findByIsbn(borrow.get().getIsbn());
            book.setQuantity(book.getQuantity() + 1);

            // 添加日志
            Log log = new Log();
            log.setTime(Calendar.getInstance().getTime());
            log.setName(user.getName());
            log.setBookName(book.getName());
            log.setInfo("归还了 " + book.getName() + " 书籍");
            logRepository.save(log);

            // 归还图书
            borrowRepository.deleteById(id);
            returnMessage.replace("statusCode", 1);
        }
        else {
            returnMessage.replace("statusCode", 2);
        }

        return returnMessage;
    }

    /**
     * 搜索超时未归还图书信息
     * @param page 页码
     * @param size 大小
     * @return 超时未归还图书信息
     */
    @GetMapping("/overtime/{page}/{size}")
    public Page<Borrow> findByReturnTimeBefore(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return borrowRepository.findByReturnTimeBefore(Calendar.getInstance().getTime(), pageable);
    }

    /**
     * 搜索IdCard超时未归还图书信息
     * @param idCard IdCard
     * @param page 页码
     * @param size 大小
     * @return 借阅信息
     */
    @GetMapping("/overtime/search/{idCard}/{page}/{size}")
    public Page<Borrow> findByIdCardAndReturnTimeBefore(@PathVariable("idCard") String idCard,
                                   @PathVariable("page") Integer page,
                                   @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return borrowRepository.findByIdCardAndReturnTimeBefore(idCard, Calendar.getInstance().getTime(), pageable);
    }

    /**
     * 用户名搜索图书借阅信息
     * @param username 用户名
     * @return 借阅信息
     */
    @GetMapping("/get/{username}")
    public List<Borrow> findByUsername(@PathVariable("username") String username) {
        return borrowRepository.findByUsername(username);
    }
}
