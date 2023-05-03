package com.ksamar.library.repository;

import com.ksamar.library.entitys.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
