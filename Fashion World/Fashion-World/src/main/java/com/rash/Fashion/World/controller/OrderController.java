package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.CartItemDTO;
import com.rash.Fashion.World.dto.OrderDTO;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.AddCartItemRequest;
import com.rash.Fashion.World.request.OrderRequest;
import com.rash.Fashion.World.service.OrderService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create/order")
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        OrderDTO orderDTO = orderService.createOrder(request, user);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/userOrders")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<OrderDTO> orders = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
