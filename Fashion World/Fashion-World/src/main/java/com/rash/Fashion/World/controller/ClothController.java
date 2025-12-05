package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.ClothResponseDTO;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateClothRequest;
import com.rash.Fashion.World.service.ClothService;
import com.rash.Fashion.World.service.ShopService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cloths")
public class ClothController {

    @Autowired
    private ClothService clothService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @GetMapping("/search")
    public ResponseEntity<List<ClothResponseDTO>> searchCloth(
            @RequestParam String name,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        if(name == null || name.isEmpty()){
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }

        String keyword = name.trim(); // no need to lowercase here; handled in service
        List<ClothResponseDTO> cloths = clothService.searchCloth(keyword);

        return new ResponseEntity<>(cloths, HttpStatus.OK);
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<ClothResponseDTO>> getShopCloth(
            @RequestParam(required = false) boolean male,
            @RequestParam(required = false) boolean female,
            @RequestParam(required = false) String cloth_category,
            @PathVariable Long shopId,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        List<ClothResponseDTO> cloths = clothService.getShopsCloth(shopId,male,female,cloth_category);

        return new ResponseEntity<>(cloths, HttpStatus.OK);
    }


}
