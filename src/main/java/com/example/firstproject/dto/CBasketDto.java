package com.example.firstproject.dto;


import com.example.firstproject.entity.Cbasket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CBasketDto {
    private String userid;
    private Long id;
    private String title;
    private String content;
    private int price;

    public Cbasket toEntity(){
        return new Cbasket(userid,id, title, content, price);
    }
}

