package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateShopRequest;

import java.util.List;

public interface ShopService {

    // Controller-facing methods (return DTO)
    ShopResponseDTO createShop(CreateShopRequest request, User user);
    ShopResponseDTO updateShop(Long shopId, CreateShopRequest updatedShop) throws Exception;
    void deleteShop(Long shopId) throws Exception;
    List<ShopResponseDTO> getAllShops();
    List<ShopResponseDTO> searchShop(String keyword);
    ShopResponseDTO findShopById(Long id) throws Exception;
    ShopResponseDTO getShopByUserId(Long userId) throws Exception;
    //Method to add or remove favourite items
    ShopDTO addFavourites(Long shopId, User user) throws Exception;
    //Method to update open/close status
    ShopResponseDTO updateShopStatus(Long id) throws Exception;


    // Internal / entity-level methods (used inside service)
    Shop findShopByIdEntity(Long id) throws Exception;
    Shop getShopEntityByUserId(Long userId) throws Exception;
}
