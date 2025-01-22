package com.example.shoppingcart.service.product;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(long id);
    void deleteProductById(long id);
    void updateProduct(Product product,long ProductId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryNameAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    long countProductsByBrandAndName(String brand,String name);
}
