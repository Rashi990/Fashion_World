package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.OrderDTO;
import com.rash.Fashion.World.model.PurchaseOrder;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public OrderDTO createOrder(OrderRequest orderRequest, User user) throws Exception;

    public OrderDTO updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<OrderDTO> getUsersOrder(Long userId) throws Exception;

    public List<OrderDTO> getShopsOrder(Long shopId, String orderStatus) throws Exception;

    public OrderDTO findOrderById(Long orderId) throws Exception;
}
