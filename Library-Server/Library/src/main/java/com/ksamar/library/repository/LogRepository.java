package com.ksamar.library.repository;

import com.ksamar.library.entitys.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 日志信息数据库操作接口
 * @author KSaMar
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
}
