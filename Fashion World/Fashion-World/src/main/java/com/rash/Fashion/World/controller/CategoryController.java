package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.CategoryResponseDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.service.CategoryService;
import com.rash.Fashion.World.service.ShopService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        CategoryResponseDTO createdCategory = categoryService.createCategory(category.getCategoryName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/shop")
    public ResponseEntity<List<CategoryResponseDTO>> getShopCategory(
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        ShopResponseDTO shop = shopService.getShopByUserId(user.getId());
        List<CategoryResponseDTO> categories = categoryService.findCategoryByShopId(shop.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
