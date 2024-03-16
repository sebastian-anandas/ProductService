package com.seb.springboot.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceProductRequestDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

}
