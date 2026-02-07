package com.rash.Fashion.World.controller;

import com.rash.Fashion.World.dto.OrderDTO;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.OrderRequest;
import com.rash.Fashion.World.service.OrderService;
import com.rash.Fashion.World.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
@CrossOrigin
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/shopOrders/{id}")
    public ResponseEntity<List<OrderDTO>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false)String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<OrderDTO> orders = orderService.getShopsOrder(id,order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}/{orderStatus}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        OrderDTO orders = orderService.updateOrder(orderId,orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
