package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateShopRequest;
import com.rash.Fashion.World.service.ShopService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@CrossOrigin
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<ShopResponseDTO>> searchShop(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<ShopResponseDTO> shop = shopService.searchShop(keyword);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ShopResponseDTO>> getAllShops(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<ShopResponseDTO> shop = shopService.getAllShops();
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponseDTO> findShopById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
            ) throws Exception {
        userService.findUserByJwtToken(jwt);

        ShopResponseDTO shop = shopService.findShopById(id);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<ShopDTO> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        ShopDTO shop = shopService.addFavourites(id,user);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

}
