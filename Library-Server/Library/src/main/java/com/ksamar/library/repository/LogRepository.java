package com.ksamar.library.repository;

import com.ksamar.library.entitys.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}
