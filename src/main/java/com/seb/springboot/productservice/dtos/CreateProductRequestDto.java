package com.seb.springboot.productservice.dtos;

import com.seb.springboot.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
}
