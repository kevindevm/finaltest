package com.techlab.kevin.repository;

import com.techlab.kevin.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryJPA extends JpaRepository<Order, Integer> {
}
