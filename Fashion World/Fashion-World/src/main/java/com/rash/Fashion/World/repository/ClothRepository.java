package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothRepository extends JpaRepository<Cloth, Long> {

    List<Cloth> findByShopId(Long shopId);

    @Query("""
        SELECT c FROM Cloth c
        WHERE LOWER(c.clothName) LIKE :keyword
        OR (c.clothCategory IS NOT NULL AND LOWER(c.clothCategory.categoryName) LIKE :keyword)
    """)
    List<Cloth> searchCloth(@Param("keyword") String keyword);


}
