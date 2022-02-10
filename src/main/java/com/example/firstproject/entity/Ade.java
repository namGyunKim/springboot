package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ade {

    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int price;
}
