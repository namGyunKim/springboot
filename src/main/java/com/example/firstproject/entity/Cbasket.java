package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Cbasket {

    @Column
    private String userid;
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int price;
}
