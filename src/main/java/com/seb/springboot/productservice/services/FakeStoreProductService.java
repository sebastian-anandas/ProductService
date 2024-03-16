package com.seb.springboot.productservice.services;

import com.seb.springboot.productservice.dtos.ErrorDto;
import com.seb.springboot.productservice.dtos.FakeStoreProductDto;
import com.seb.springboot.productservice.exceptions.ProductNotFoundException;
import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    @Autowired
    FakeStoreProductService(RestTemplate theRestTemplate) {
        this.restTemplate = theRestTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        // Response Entity will return additional content in the response. eg. headers, cookies, etc
        ResponseEntity<FakeStoreProductDto> fakeStoreProductResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId, FakeStoreProductDto.class);

//        if(fakeStoreProductResponse.getStatusCode() != HttpStatusCode.valueOf(200)) {
//            // define error
//            throw new RuntimeException();
//        }

        FakeStoreProductDto fakeStoreProduct = fakeStoreProductResponse.getBody();

        if(fakeStoreProduct == null) {
            throw new ProductNotFoundException("Product with id: " + productId + " doesn't exist. Retry with some other product.");
        }

        return fakeStoreProduct.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {

        FakeStoreProductDto[] fakeStoreProducts = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        List<Product> allProducts = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProducts) {
            allProducts.add(fakeStoreProductDto.toProduct());
        }

        return allProducts;
    }

    @Override
    public Product createProduct(String title, double price, String description, String image, String category) {

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setCategory(category);

        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products", //url
                fakeStoreProductDto, // request body
                FakeStoreProductDto.class // data type of response
                );

        return response.toProduct();
    }

    @Override
    public Product deleteProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public Product replaceProduct(Long productId, String title, String description, double price, String image, String category) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productId);
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setCategory(category);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto, headers);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.PATCH,
                requestEntity,
                FakeStoreProductDto.class
        );

        return fakeStoreProductDto.toProduct();

    }

    @Override
    public Product updateProduct(Long productId, String title, String description, double price, String image, String category) throws ProductNotFoundException {

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productId);

        if(title != null) {
            fakeStoreProductDto.setTitle(title);
        }
        if(description != null) {
            fakeStoreProductDto.setDescription(description);
        }
        if(price != 0.0d) {
            fakeStoreProductDto.setPrice(price);
        }
        if(image != null) {
            fakeStoreProductDto.setImage(image);
        }
        if(category != null) {
            fakeStoreProductDto.setCategory(category);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto, headers);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreProductDto.class
        );

        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<String> getAllCategories() {

        String[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        if(categories == null) {
            throw new RuntimeException();
        }

        List<String> allCategories = new ArrayList<>();
        for(String category : categories) {
            allCategories.add(category);
        }

        return allCategories;
    }

    @Override
    public List<Product> getProductInCategory(String category) {

        FakeStoreProductDto[] fakeStoreProducts = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDto[].class
        );

//        if(fakeStoreProducts == null) {
//            throw new RuntimeException();
//        }

        List<Product> allProductsInCategory = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProducts) {
            allProductsInCategory.add(fakeStoreProductDto.toProduct());
        }

        return allProductsInCategory;

    }

}
