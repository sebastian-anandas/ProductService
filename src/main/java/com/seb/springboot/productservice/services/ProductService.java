package com.seb.springboot.productservice.services;

import com.seb.springboot.productservice.exceptions.ProductNotFoundException;
import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(String title, double price, String description, String image, String category);

    Product deleteProduct(Long productId);

    Product replaceProduct(Long productId, String title, String description, double price, String image, String category) throws ProductNotFoundException;
    Product updateProduct(Long productId, String title, String description, double price, String image, String category) throws ProductNotFoundException;

    List<String> getAllCategories();

    List<Product> getProductInCategory(String category);


}
