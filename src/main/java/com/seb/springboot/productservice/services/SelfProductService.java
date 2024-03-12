package com.seb.springboot.productservice.services;

import com.seb.springboot.productservice.exceptions.ProductNotFoundException;
import com.seb.springboot.productservice.models.Category;
import com.seb.springboot.productservice.models.Product;
import com.seb.springboot.productservice.repositories.CategoryRepository;
import com.seb.springboot.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository theProductRepository, CategoryRepository theCategoryRepository) {
        this.productRepository = theProductRepository;
        this.categoryRepository = theCategoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdEquals(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, double price, String description, String image, String category) {

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category categoryFromDatabase = categoryRepository.findByTitle(category);

        if(categoryFromDatabase == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
//            categoryFromDatabase = categoryRepository.save(newCategory);

            categoryFromDatabase = newCategory;
        }

        // if the category was found from DB -> categoryFromDatabase will be having an ID
        // else: categoryFromDatabase won't have any ID
        product.setCategory(categoryFromDatabase);

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) {
        return productRepository.deleteProductById(productId);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public List<String> getAllCategories() {

        List<Category> allCategories = categoryRepository.findAll();
        List<String> categoryTitles = new ArrayList<>();

        for(Category category : allCategories) {
            categoryTitles.add(category.getTitle());
        }

        return categoryTitles;
    }

    @Override
    public List<Product> getProductInCategory(String category) {

        List<Product> allProductsInCategory = new ArrayList<>();

        // making 2 calls to the database
//        Category targetCategory = categoryRepository.findByTitle(category);
//        if(category != null) {
//            allProductsInCategory = productRepository.findByCategory(targetCategory);
//        }

        // using just 1 call
        allProductsInCategory = productRepository.findAllByCategory_Title(category);

        return allProductsInCategory;
    }
}
