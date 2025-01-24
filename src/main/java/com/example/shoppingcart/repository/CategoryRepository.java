package com.example.shoppingcart.repository;

import com.example.shoppingcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}
