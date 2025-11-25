package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.request.CreateShopRequest;

import java.util.List;

public interface ShopService {

    public Shop createShop (CreateShopRequest request, User user);

    public Shop updateShop (Long shopId, CreateShopRequest updatedShop) throws Exception;

    public void deleteShop (Long shopId) throws Exception;

    public List<Shop> getAllShops();

    public List<Shop> searchShop(String keyword);

    public Shop findShopById(Long id) throws Exception;

    public Shop getShopByUserId(Long userId) throws Exception;

    //Method to add or remove favourite items
    public ShopDTO addFavourites(Long shopId, User user) throws Exception;

    //Method to update open/close status
    public Shop updateShopStatus(Long id) throws Exception;
}
