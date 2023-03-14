package com.domo.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.domo.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
