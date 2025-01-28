package com.example.shoppingcart.service.product;

import com.example.shoppingcart.dto.ImageDto;
import com.example.shoppingcart.dto.ProductDto;
import com.example.shoppingcart.exceptions.ProductNotFoundException;
import com.example.shoppingcart.model.Category;
import com.example.shoppingcart.model.Image;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.CategoryRepository;
import com.example.shoppingcart.repository.ImageRepository;
import com.example.shoppingcart.repository.ProductRepository;
import com.example.shoppingcart.request.AddProductRequest;
import com.example.shoppingcart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
//        check if the category is found in the database
//        if yes, set it as the new product category
//        if no, the save it as a new category
//        the set as the new product category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(request.getCategory().getName());
                        return categoryRepository.save(newCategory);
        });

        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }


    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                () -> {throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
         return productRepository.findById(productId)
                 .map(existingProduct -> updateExistingProduct(existingProduct, request))
                 .map(productRepository::save)
                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryNameAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
