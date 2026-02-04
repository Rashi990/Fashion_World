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

//    DTO Conversion

    // Converts CartItem entity to CartItemDTO
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

    // Coverts Cart entity to CartDTO
    private CartDTO convertCartToDTO(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getCustomer().getId(),
                cart.getTotal(),
                cart.getItem().stream().map(this::convertCartItemToDTO).toList()
        );
    }

//    Service Methods

    // Add new item to cart
    @Override
    public CartItemDTO addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cloth cloth = clothService.findClothById(request.getClothId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        // Check if item already exists in cart
        for (CartItem cartItem : cart.getItem()){
            if (cartItem.getCloth().equals(cloth)){
                int newQuantity = cartItem.getQuantity()+ request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        // Create new cart item
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setCloth(cloth);
        newItem.setQuantity(request.getQuantity());
        newItem.setTotalPrice(request.getQuantity() * cloth.getPrice());

        // Add item to cart and recalculate total
        cart.getItem().add(newItem);
        cart.setTotal(calculateCartTotal(cart));

        cartItemRepository.save(newItem);
        cartRepository.save(cart);

        return convertCartItemToDTO(newItem);
    }

    // Update quantity of a cart item and recalculates cart total
    @Override
    public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getCloth().getPrice()*quantity);

        // Update cart total after quantity change
        Cart cart = item.getCart();
        cart.setTotal(calculateCartTotal(cart));

        cartItemRepository.save(item);
        cartRepository.save(cart);

        return convertCartItemToDTO(item);
    }

    // Remove an item from the cart
    @Override
    public CartDTO removeCartItem(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }

        CartItem cartItem = cartItemOptional.get();

        // Remove item and update total
        cart.getItem().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart.setTotal(calculateCartTotal(cart));
        cartRepository.save(cart);

        return convertCartToDTO(cart);
    }

    // Calculate total price of the cart
    @Override
    public Long calculateCartTotal(Cart cart){
        Long total = 0L;

        for (CartItem cartItem : cart.getItem()){
            total+=cartItem.getCloth().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    // Find cart by cart ID
    @Override
    public CartDTO findCartByID(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("Cart not found with id "+id);
        }

        return convertCartToDTO(optionalCart.get());
    }

    // Find cart of currently logged-in user
    @Override
    public CartDTO findCartByUserId(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return convertCartToDTO(cart);
    }

    // Clear all items from the cart
    @Override
    public CartDTO clearCart(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);

        cart.getItem().clear();
        cart.setTotal(0L);
        cartRepository.save(cart);

        return convertCartToDTO(cart);
    }
}
