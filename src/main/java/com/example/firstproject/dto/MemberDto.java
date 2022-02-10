package com.example.firstproject.dto;

import com.example.firstproject.entity.Members;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {
    private String id;
    private String password;

    public Members toEntity(){
        return new Members(id, password);
    }
}
