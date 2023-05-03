package com.ksamar.library.repository;

import com.ksamar.library.entitys.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    /**
     * IdCard 搜索借阅信息
     * @param idCard IdCard
     * @param pageable 分页
     * @return 借阅信息
     */
    Page<Borrow> findByIdCard(String idCard, Pageable pageable);

    /**
     * 获取超时未归还图书信息
     * @param date 当前时间
     * @param pageable 分页
     * @return 借阅信息
     */
    Page<Borrow> findByReturnTimeBefore(Date date, Pageable pageable);

    /**
     * 获取借阅卡超时为归还图书信息
     * @param idCard 借阅卡号
     * @param date 当前时间
     * @param pageable 分页
     * @return 借阅信息
     */
    Page<Borrow> findByIdCardAndReturnTimeBefore(String idCard, Date date, Pageable pageable);

    /**
     * 用户名搜索借阅信息
     * @param username 用户名
     * @return 借阅信息
     */
    List<Borrow> findByUsername(String username);
}
