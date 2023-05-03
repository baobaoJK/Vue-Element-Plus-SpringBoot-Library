package com.ksamar.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksamar.library.entitys.Book;
import com.ksamar.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookHandler {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/all")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
