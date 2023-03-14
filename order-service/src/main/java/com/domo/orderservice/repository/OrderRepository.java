package com.domo.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domo.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
