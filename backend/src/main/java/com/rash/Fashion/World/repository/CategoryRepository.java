package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByShopId(Long id);
}
