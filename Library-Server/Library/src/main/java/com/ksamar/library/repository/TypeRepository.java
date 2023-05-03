package com.ksamar.library.repository;

import com.ksamar.library.entitys.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 图书种类数据库操作接口
 * @author KSaMar
 */
public interface TypeRepository extends JpaRepository<Type, Integer> {

    /**
     * 搜索图书种类名称
     * @param typeName 图书种类名称
     * @return 图书种类
     */
    Type findByTypeName(String typeName);
}
