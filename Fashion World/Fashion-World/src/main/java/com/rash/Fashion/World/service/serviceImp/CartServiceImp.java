package com.rash.Fashion.World.service.serviceImp;

import com.rash.Fashion.World.dto.CartDTO;
import com.rash.Fashion.World.dto.CartItemDTO;
import com.rash.Fashion.World.model.*;
import com.rash.Fashion.World.repository.CartItemRepository;
import com.rash.Fashion.World.repository.CartRepository;
import com.rash.Fashion.World.request.AddCartItemRequest;
import com.rash.Fashion.World.service.CartService;
import com.rash.Fashion.World.service.ClothService;
import com.rash.Fashion.World.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ClothService clothService;

    @Autowired
    private ModelMapper modelMapper;

    //    DTO Converters
    private CartItemDTO convertCartItemToDTO(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getCloth().getId(),
                item.getCloth().getClothName(),
                item.getQuantity(),
                item.getColors(),
                item.getCloth().getPrice(),
                item.getTotalPrice()
        );
    }

    private CartDTO convertCartToDTO(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getCustomer().getId(),
                cart.getTotal(),
                cart.getItem().stream().map(this::convertCartItemToDTO).toList()
        );
    }

//    @Override
//    public CartItemDTO addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
//        Cloth cloth = clothService.findClothById(request.getClothId());
//
//        Cart cart = cartRepository.findByCustomerId(user.getId())
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setCustomer(user);
//                    newCart.setTotal(0L);
//                    return cartRepository.save(newCart);
//                });
//
//        // Check if cloth already exists in cart
//        for (CartItem item : cart.getItem()) {
//            if (item.getCloth().getId().equals(cloth.getId())) {
//                item.setQuantity(item.getQuantity() + request.getQuantity());
//                item.setTotalPrice(item.getQuantity() * cloth.getPrice());
//                cart.setTotal(calculateCartTotal(cart));
//                return convertCartItemToDTO(cartItemRepository.save(item));
//            }
//        }
//
//        // New cart item
//        CartItem newItem = new CartItem();
//        newItem.setCart(cart);
//        newItem.setCloth(cloth);
//        newItem.setQuantity(request.getQuantity());
//        newItem.setTotalPrice(request.getQuantity() * cloth.getPrice());
//
//        cart.getItem().add(newItem);
//        cart.setTotal(calculateCartTotal(cart));
//
//        cartItemRepository.save(newItem);
//        cartRepository.save(cart);
//
//        return convertCartItemToDTO(newItem);
//    }

    @Override
    public CartItemDTO addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cloth cloth = clothService.findClothById(request.getClothId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItem()){
            if (cartItem.getCloth().equals(cloth)){
                int newQuantity = cartItem.getQuantity()+ request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        // New cart item
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setCloth(cloth);
        newItem.setQuantity(request.getQuantity());
        newItem.setTotalPrice(request.getQuantity() * cloth.getPrice());

        cart.getItem().add(newItem);
        cart.setTotal(calculateCartTotal(cart));

        cartItemRepository.save(newItem);
        cartRepository.save(cart);

        return convertCartItemToDTO(newItem);
    }

//    @Override
//    public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
//
//        CartItem item = cartItemRepository.findById(cartItemId)
//                .orElseThrow(()-> new Exception("Cart item not found"));
//
//        item.setQuantity(quantity);
//        item.setTotalPrice(quantity*item.getCloth().getPrice());
//
//        Cart cart = item.getCart();
//        cart.setTotal(calculateCartTotal(cart));
//
//        cartItemRepository.save(item);
//        cartRepository.save(cart);
//
//        return convertCartItemToDTO(item);
//    }

    @Override
    public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getCloth().getPrice()*quantity);

        Cart cart = item.getCart();
        cart.setTotal(calculateCartTotal(cart));

        cartItemRepository.save(item);
        cartRepository.save(cart);

        return convertCartItemToDTO(item);
    }

//    @Override
//    public CartDTO removeCartItem(Long cartItemId, String jwt) throws Exception {
//
//        User user = userService.findUserByJwtToken(jwt);
//        Cart cart = cartRepository.findByCustomerId(user.getId())
//                .orElseThrow(()->new Exception("Cart not found"));
//
//        CartItem cartItem = cartItemRepository.findById(cartItemId)
//                .orElseThrow(()->new Exception("Cart item not found"));
//
//        cart.getItem().remove(cartItem);
//        cartItemRepository.delete(cartItem);
//
//        cart.setTotal(calculateCartTotal(cart));
//        cartRepository.save(cart);
//
//        return convertCartToDTO(cart);
//    }
@Override
public CartDTO removeCartItem(Long cartItemId, String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartRepository.findByCustomerId(user.getId());

    Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
    if (cartItemOptional.isEmpty()){
        throw new Exception("Cart item not found");
    }

    CartItem cartItem = cartItemOptional.get();

    cart.getItem().remove(cartItem);
    cartItemRepository.delete(cartItem);

    cart.setTotal(calculateCartTotal(cart));
    cartRepository.save(cart);

    return convertCartToDTO(cart);
}

//    @Override
//    public Long calculateCartTotal(Cart cart){
//        return cart.getItem().stream()
//                .mapToLong(CartItem::getTotalPrice)
//                .sum();
//    }

    @Override
    public Long calculateCartTotal(Cart cart){
        Long total = 0L;

        for (CartItem cartItem : cart.getItem()){
            total+=cartItem.getCloth().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

//    @Override
//    public CartDTO findCartByID(Long id) throws Exception {
//        Cart cart = cartRepository.findById(id)
//                .orElseThrow(() -> new Exception("Cart not found"));
//        return convertCartToDTO(cart);
//    }

    @Override
    public CartDTO findCartByID(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("Cart not found with id "+id);
        }

        return convertCartToDTO(optionalCart.get());
    }

//    @Override
//    public CartDTO findCartByUserId(Long userId) throws Exception {
//        Cart cart = cartRepository.findByCustomerId(userId)
//                .orElseThrow(() -> new Exception("Cart not found"));
//        return convertCartToDTO(cart);
//    }

    @Override
    public CartDTO findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());
        return convertCartToDTO(cart);
    }

//    @Override
//    public CartDTO clearCart(Long userId) throws Exception {
//        Cart cart = cartRepository.findByCustomerId(userId)
//                .orElseThrow(() -> new Exception("Cart not found"));
//
//        cart.getItem().clear();
//        cart.setTotal(0L);
//        cartRepository.save(cart);
//
//        return convertCartToDTO(cart);
//    }

    @Override
    public CartDTO clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        cart.getItem().clear();
        cart.setTotal(0L);
        cartRepository.save(cart);

        return convertCartToDTO(cart);
    }
}
