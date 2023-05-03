package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 日志信息实体类
 * @author KSaMar
 */
@Table(name = "OPERATIONLIST")
@Entity
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date time;
    private String name;
    @Column(name = "book_name")
    private String bookName;
    private String info;
}
