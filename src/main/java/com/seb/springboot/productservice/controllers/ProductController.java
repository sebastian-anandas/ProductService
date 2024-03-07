package com.seb.springboot.productservice.controllers;

import com.seb.springboot.productservice.dtos.CreateProductRequestDto;
import com.seb.springboot.productservice.dtos.ErrorDto;
import com.seb.springboot.productservice.exceptions.ProductNotFoundException;
import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import com.seb.springboot.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private RestTemplate restTemplate;

    @Autowired
    public ProductController(ProductService theProductService, RestTemplate theRestTemplate) {
        this.productService = theProductService;
        this.restTemplate = theRestTemplate;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getPrice(),
                request.getDescription(),
                request.getCategory(),
                request.getImageUrl()
        );
    }

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return productService.getSingleProduct(productId);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody CreateProductRequestDto request) {
        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        Category category = new Category();
        category.setTitle(request.getTitle());
        product.setCategory(category);

        return productService.updateProduct(
            productId, product
        );
    }

    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/products/categories")
    public List<String> getAllCategory() {
        return productService.getAllCategories();
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProductInCategory(@PathVariable("category") String category) {
        return productService.getProductInCategory(category);
    }


    // Limited to only the exceptions thrown from this controller
    // Controller Advices: Global
    /*
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException exception) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);

    }
     */

}
