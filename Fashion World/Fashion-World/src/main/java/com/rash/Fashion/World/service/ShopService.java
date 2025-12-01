package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateShopRequest;

import java.util.List;

public interface ShopService {

    public ShopResponseDTO createShop (CreateShopRequest request, User user);

    public ShopResponseDTO updateShop (Long shopId, CreateShopRequest updatedShop) throws Exception;

    public void deleteShop (Long shopId) throws Exception;

    public List<ShopResponseDTO> getAllShops();

    public List<ShopResponseDTO> searchShop(String keyword);

    public Shop findShopById(Long id) throws Exception;

    public ShopResponseDTO getShopByUserId(Long userId) throws Exception;

    //Method to add or remove favourite items
    public ShopDTO addFavourites(Long shopId, User user) throws Exception;

    //Method to update open/close status
    public ShopResponseDTO updateShopStatus(Long id) throws Exception;

    public Shop getShopEntityByUserId(Long userId) throws Exception;

}
