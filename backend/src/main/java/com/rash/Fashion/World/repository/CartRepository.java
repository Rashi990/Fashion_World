package com.rash.Fashion.World.repository;

import com.rash.Fashion.World.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

//    Optional<Cart> findByCustomerId(Long userId);
    public Cart findByCustomerId(Long userId);

}
