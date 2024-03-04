package com.seb.springboot.productservice.services;

import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId);

    List<Product> getProducts();

    Product createProduct(String title, double price, String description, String image, String category);

    Product deleteProduct(Long productId);

    Product updateProduct(Long productId, Product product);

    List<String> getAllCategories();

    List<Product> getProductInCategory(String category);

}
