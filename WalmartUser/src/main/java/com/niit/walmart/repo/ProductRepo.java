package com.niit.walmart.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niit.walmart.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    void deleteById(Long id);
    Optional<Product> findById (Long id);
}
