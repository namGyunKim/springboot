package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Coffees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 알아서 1,2,3 자동생성 어노테이션
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int price;
}
