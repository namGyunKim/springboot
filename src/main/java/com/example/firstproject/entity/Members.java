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
@Entity     //DB가 해당 객체를 인식가능하게 만들어줌
public class Members {

    @Id
    private String id;
    @Column
    private String password;
}
