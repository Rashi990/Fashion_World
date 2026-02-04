package com.rash.Fashion.World.service.serviceImp;

import com.rash.Fashion.World.dto.*;
import com.rash.Fashion.World.model.*;
import com.rash.Fashion.World.repository.*;
import com.rash.Fashion.World.request.OrderRequest;
import com.rash.Fashion.World.service.CartService;
import com.rash.Fashion.World.service.OrderService;
import com.rash.Fashion.World.service.ShopService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ClothRepository clothRepository;

    //    DTO Conversion
    // Convert Entity → DTO
    private OrderItemDTO convertOrderItemToDTO(OrderItem item){
        return new OrderItemDTO(
                item.getId(),
                item.getCloth().getId(),
                item.getCloth().getClothName(),
                item.getQuantity(),
                item.getColor(),
                item.getTotalPrice()
        );
    }

    private OrderDTO convertOrderToDTO(PurchaseOrder order){
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getShop().getId(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                order.getTotalItem(),
                order.getItems().stream().map(this::convertOrderItemToDTO).toList()
        );
    }

    @Override
    public OrderDTO createOrder(OrderRequest orderRequest, User user) throws Exception{
        Address shippingAddress = orderRequest.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shippingAddress);

        if (!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        //Get Shop Entity
        Shop shop = shopService.findShopByIdEntity(orderRequest.getShopId());

        PurchaseOrder createdOrder = new PurchaseOrder();
        createdOrder.setCustomer(user);
        createdOrder.setShop(shop);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);

        CartDTO cartDTO = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDTO cartItem : cartDTO.getItems()){

            Cloth cloth = clothRepository.findById(cartItem.getClothId())
                    .orElseThrow(() -> new Exception("Cloth not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setCloth(cloth);
            orderItem.setColor(cartItem.getColors());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartDTO.getTotal();

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);
        createdOrder.setTotalItem(cartDTO.getItems().size());

        PurchaseOrder savedOrder = orderRepository.save(createdOrder);
        shop.getOrders().add(savedOrder);

        return convertOrderToDTO(createdOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, String orderStatus) throws Exception {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

    }

    @Override
    public List<OrderDTO> getUsersOrder(Long userId) throws Exception {
        return null;
    }

    @Override
    public List<OrderDTO> getShopsOrder(Long shopId, String orderStatus) throws Exception {
        return null;
    }
}
