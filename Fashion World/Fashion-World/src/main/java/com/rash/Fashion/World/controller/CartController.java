package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.CartDTO;
import com.rash.Fashion.World.dto.CartItemDTO;
import com.rash.Fashion.World.request.AddCartItemRequest;
import com.rash.Fashion.World.request.UpdateCartItemRequest;
import com.rash.Fashion.World.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")

public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addItemToCart(
            @RequestBody AddCartItemRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception{
        CartItemDTO cartItemDTO = cartService.addItemToCart(request, jwt);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemDTO> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest request,
            @RequestHeader("Authorization") String jwt
            ) throws Exception{
        CartItemDTO cartItemDTO = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return new ResponseEntity<>(cartItemDTO,HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<CartDTO> removeCartItem(
         @PathVariable Long id,
         @RequestHeader("Authorization") String jwt
    ) throws Exception{
        CartDTO cartDTO = cartService.removeCartItem(id,jwt);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

    @PutMapping("/clear")
    public ResponseEntity<CartDTO> clearCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        CartDTO cartDTO = cartService.clearCart(jwt);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<CartDTO> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        CartDTO cartDTO = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

}
