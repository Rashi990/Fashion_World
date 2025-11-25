package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query("SELECT s FROM Shop s WHERE lower(s.shopName) LIKE lower(concat('%',:query, '%')) "+
            "OR lower(s.type) LIKE lower(concat('%', :query, '%')) ")
    List<Shop> findBySearchQuery(String query);

    Shop findByOwnerId(Long userId);
}
