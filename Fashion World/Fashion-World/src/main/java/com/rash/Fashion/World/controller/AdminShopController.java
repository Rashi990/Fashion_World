package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateShopRequest;
import com.rash.Fashion.World.response.MessageResponse;
import com.rash.Fashion.World.service.ShopService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/shop")

public class AdminShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Shop> createShop(
        @RequestBody CreateShopRequest request,
        @RequestHeader("Authorization") String jwt
        ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.createShop(request, user);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(
            @RequestBody CreateShopRequest request,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.updateShop(id, request);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteShop(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        shopService.deleteShop(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Shop deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Shop> updateShopStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.updateShopStatus(id);

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Shop> findShopByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Shop shop = shopService.getShopByUserId(user.getId());

        return new ResponseEntity<>(shop, HttpStatus.OK);

    }
}
