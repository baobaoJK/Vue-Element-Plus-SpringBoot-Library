package com.ksamar.library.controller;

import com.ksamar.library.entitys.Log;
import com.ksamar.library.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nav")
public class LogHandler {
    @Autowired
    private LogRepository logRepository;

    /**
     * 获取日志信息
     * @return 日志信息
     */
    @GetMapping("/logs")
    public Page<Log> findLogs() {
        PageRequest pageable = PageRequest.of(0, 50, Sort.Direction.DESC,"id");
        return logRepository.findAll(pageable);
    }
}
