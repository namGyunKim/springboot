package com.example.firstproject.dto;

import com.example.firstproject.entity.Members;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PassDto {
    private String id;
    private String password;
    private String password2;

    public Members toEntity(){
        return new Members(id, password);
    }
}
