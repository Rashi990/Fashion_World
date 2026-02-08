package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
