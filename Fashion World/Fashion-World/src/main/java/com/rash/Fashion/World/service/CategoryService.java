package com.rash.Fashion.World.service;

import com.rash.Fashion.World.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByShopId(Long id) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
