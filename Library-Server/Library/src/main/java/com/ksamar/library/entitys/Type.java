package com.ksamar.library.entitys;

import lombok.Data;

import javax.persistence.*;

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
