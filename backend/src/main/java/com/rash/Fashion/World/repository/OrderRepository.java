package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<PurchaseOrder,Long> {

    public List<PurchaseOrder> findByCustomerId(Long userId);

    public List<PurchaseOrder> findByShopId(Long shopId);
}
