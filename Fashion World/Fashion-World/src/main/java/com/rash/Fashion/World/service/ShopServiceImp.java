package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.model.Address;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.repository.AddressRepository;
import com.rash.Fashion.World.repository.ShopRepository;
import com.rash.Fashion.World.repository.UserRepository;
import com.rash.Fashion.World.request.CreateShopRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImp implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Shop createShop(CreateShopRequest request, User user) {

        Address address = addressRepository.save(request.getAddress());

        Shop shop = new Shop();
        shop.setAddress(address);
        shop.setContactInformation(request.getContactInformation());
        shop.setType(request.getType());
        shop.setDescription(request.getDescription());
        shop.setImages(request.getImages());
        shop.setName(request.getShopName());
        shop.setOpeningHours(request.getOpeningHours());
        shop.setRegistrationDate(LocalDateTime.now());
        shop.setOwner(user);

        return shopRepository.save(shop);
    }

    @Override
    public Shop updateShop(Long shopId, CreateShopRequest updatedShop) throws Exception {

        Shop shop = findShopById(shopId);

        if (shop.getType()!=null){
            shop.setType(updatedShop.getType());
        }

        if (shop.getDescription()!=null){
            shop.setDescription(updatedShop.getDescription());
        }

        if (shop.getName()!=null){
            shop.setName(updatedShop.getShopName());
        }

        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Long shopId) throws Exception {

        Shop shop = findShopById(shopId);

        shopRepository.delete(shop);
    }

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> searchShop(String keyword) {
        return shopRepository.findBySearchQuery(keyword);
    }

    @Override
    public Shop findShopById(Long id) throws Exception {
        Optional<Shop> opt = shopRepository.findById(id);

        if (opt.isEmpty()){
            throw new Exception("Shop not found with id"+id);
        }
        return opt.get();
    }

    @Override
    public Shop getShopByUserId(Long userId) throws Exception {

        Shop shop = shopRepository.findByOwnerId(userId);
        if (shop==null){
            throw new Exception("Shop not found with owner id"+userId);
        }
        return shop;
    }

    @Override
    public ShopDTO addFavourites(Long shopId, User user) throws Exception {

        Shop shop = findShopById(shopId);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setDescription(shop.getDescription());
        shopDTO.setTitle(shop.getName());
        shopDTO.setImages(shop.getImages());
        shopDTO.setId(shopId);

        if (user.getFavorites().contains(shopDTO)){
            user.getFavorites().remove(shopDTO);
        }
        else user.getFavorites().add(shopDTO);

        userRepository.save(user);
        return shopDTO;
    }

    @Override
    public Shop updateShopStatus(Long id) throws Exception {

        Shop shop = findShopById(id);
        shop.setOpen(!shop.isOpen());
        return shopRepository.save(shop);
    }
}
