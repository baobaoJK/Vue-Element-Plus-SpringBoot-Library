package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BOOKLIST")
@Entity
@Data
public class Book {
    @Id
    private Integer id;
    private String name;
    private String author;
}
