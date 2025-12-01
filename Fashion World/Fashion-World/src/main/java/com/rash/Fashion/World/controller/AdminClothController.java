package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateClothRequest;
import com.rash.Fashion.World.response.MessageResponse;
import com.rash.Fashion.World.service.CategoryService;
import com.rash.Fashion.World.service.ClothService;
import com.rash.Fashion.World.service.ShopService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/cloths")
public class AdminClothController {

    @Autowired
    private ClothService clothService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Cloth> createCloth(
            @RequestBody CreateClothRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Shop shop = shopService.findShopById(request.getShopId());

        // Fetch category by ID
        Category category = categoryService.findCategoryById(request.getClothCategoryId());

        Cloth cloth = clothService.createCloth(request,category,shop);
        return new ResponseEntity<>(cloth, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCloth(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        clothService.deleteCloth(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Cloth is deleted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cloth> updateClothAvailabilityStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        Cloth cloth = clothService.updateAvailability(id);

        return new ResponseEntity<>(cloth, HttpStatus.OK);
    }
}
