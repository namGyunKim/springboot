package com.example.firstproject.dto;


import com.example.firstproject.entity.Coffees;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoffeeDto {
    private Long id;
    private String title;
    private String content;
    private int price;

    public Coffees toEntity(){
        return new Coffees(id, title, content, price);
    }
}

