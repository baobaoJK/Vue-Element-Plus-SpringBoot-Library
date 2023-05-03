package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 借阅信息实体类
 * @author KSaMar
 */
@Table(name = "BORROWLIST")
@Entity
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "book_name")
    private String bookName;
    private String isbn;
    private String username;
    @Column(name = "id_card")
    private String idCard;
    private String phone;
    @Column(name = "borrow_time")
    private Date borrowTime;
    @Column(name = "return_time")
    private Date returnTime;
}
