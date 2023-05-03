package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.*;

@Table(name = "BOOKLIST")
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String groups;
    private String name;
    private String author;
    private String press;
    private Double price;
    private Integer quantity;
    private String isbn;
}
