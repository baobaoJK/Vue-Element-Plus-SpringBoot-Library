package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.*;

/**
 * 图书种类实体类
 * @author KSaMar
 */
@Table(name = "TYPELIST")
@Entity
@Data
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "type_id")
    private String typeId;
}
