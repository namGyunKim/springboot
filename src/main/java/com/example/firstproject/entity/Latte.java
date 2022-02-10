package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Latte {

    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int price;
}
