package com.ksamar.library.repository;

import com.ksamar.library.entitys.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户数据库操作接口
 * @author KSaMar
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 借书卡搜索用户
     * @param idCard 借书卡
     * @return 用户信息
     */
    User findByIdCard(String idCard);

    /**
     * 用户名模糊查询
     * @param username 用户名
     * @param pageable 分页
     * @return 用户列表
     */
    @Query(value = "select user from User user where user.username like %?1%")
    Page<User> findByUserNameLike(String username, Pageable pageable);

    /**
     * 借阅卡模糊查询
     * @param idCard 借阅卡好
     * @param pageable 分页
     * @return 用户列表
     */
    @Query(value = "select user from User user where user.idCard like %?1%")
    Page<User> findByIdCardLike(String idCard, Pageable pageable);

    /**
     * 手机号模糊查询
     * @param phone 手机号
     * @param pageable 分页
     * @return 用户列表
     */
    @Query(value = "select user from User user where user.phone like %?1%")
    Page<User> findByPressLike(String phone, Pageable pageable);

    /**
     * 用户名查询
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 手机号查询
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);
}
