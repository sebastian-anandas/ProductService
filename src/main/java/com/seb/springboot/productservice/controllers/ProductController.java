package com.seb.springboot.productservice.controllers;

import com.seb.springboot.productservice.dtos.CreateProductRequestDto;
import com.seb.springboot.productservice.dtos.ReplaceProductRequestDto;
import com.seb.springboot.productservice.dtos.UpdateProductRequestDto;
import com.seb.springboot.productservice.exceptions.ProductNotFoundException;
import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import com.seb.springboot.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private RestTemplate restTemplate;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService theProductService, RestTemplate theRestTemplate) {
        this.productService = theProductService;
        this.restTemplate = theRestTemplate;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getPrice(),
                request.getDescription(),
                request.getImageUrl(),
                request.getCategory()
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
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody ReplaceProductRequestDto replaceRequest) throws ProductNotFoundException {

        return productService.replaceProduct(productId,
                replaceRequest.getTitle(),
                replaceRequest.getDescription(),
                replaceRequest.getPrice(),
                replaceRequest.getImageUrl(),
                replaceRequest.getCategory()
        );
    }


//    @PutMapping("/products/{id}")
    @PatchMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody UpdateProductRequestDto updateRequest) throws ProductNotFoundException {

        return productService.updateProduct( productId,
                updateRequest.getTitle(),
                updateRequest.getDescription(),
                updateRequest.getPrice(),
                updateRequest.getImageUrl(),
                updateRequest.getCategory()
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
