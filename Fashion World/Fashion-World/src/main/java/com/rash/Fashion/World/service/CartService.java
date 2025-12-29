package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.CartDTO;
import com.rash.Fashion.World.dto.CartItemDTO;
import com.rash.Fashion.World.model.Cart;
import com.rash.Fashion.World.request.AddCartItemRequest;

public interface CartService {

 public CartItemDTO addItemToCart(AddCartItemRequest request, String jwt)throws Exception;
 public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity)throws Exception;
 public CartDTO removeCartItem(Long cartItemId, String jwt)throws Exception;
// public Long calculateCartTotal(CartDTO cartDTO)throws Exception;

 Long calculateCartTotal(Cart cart);

 public CartDTO findCartByID(Long id)throws Exception;
 public CartDTO findCartByUserId(String jwt)throws Exception;
 public CartDTO clearCart(String jwt)throws Exception;

}
