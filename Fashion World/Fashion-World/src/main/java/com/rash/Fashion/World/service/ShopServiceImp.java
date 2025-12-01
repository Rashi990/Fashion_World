package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ShopDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Address;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.model.User;
import com.rash.Fashion.World.repository.AddressRepository;
import com.rash.Fashion.World.repository.ShopRepository;
import com.rash.Fashion.World.repository.UserRepository;
import com.rash.Fashion.World.request.CreateShopRequest;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    // Convert Entity → DTO
    private ShopResponseDTO convertToDTO(Shop shop){
        return modelMapper.map(shop, ShopResponseDTO.class);
    }

    // Convert Entity List → DTO List
    private List<ShopResponseDTO> convertToDTOList(List<Shop> shops) {
        return shops.stream().map(this::convertToDTO).toList();
    }

    @Override
    public ShopResponseDTO createShop(CreateShopRequest request, User user) {

        Address address = addressRepository.save(request.getAddress());

        Shop shop = new Shop();
        shop.setAddress(address);
        shop.setContactInformation(request.getContactInformation());
        shop.setType(request.getType());
        shop.setDescription(request.getDescription());
        shop.setImages(request.getImages());
        shop.setShopName(request.getShopName());
        shop.setOpeningHours(request.getOpeningHours());
        shop.setRegistrationDate(LocalDateTime.now());
        shop.setOwner(user);

        shopRepository.save(shop);
        return convertToDTO(shop);

    }

    @Override
    public ShopResponseDTO updateShop(Long shopId, CreateShopRequest updatedShop) throws Exception {

        Shop shop = findShopById(shopId);

        if (shop.getType()!=null){
            shop.setType(updatedShop.getType());
        }

        if (shop.getDescription()!=null){
            shop.setDescription(updatedShop.getDescription());
        }

        if (shop.getShopName()!=null){
            shop.setShopName(updatedShop.getShopName());
        }

        shopRepository.save(shop);
        return convertToDTO(shop);
    }

    @Override
    public void deleteShop(Long shopId) throws Exception {
        Shop shop = findShopById(shopId);
        shopRepository.delete(shop);
    }

    @Override
    public List<ShopResponseDTO> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        return convertToDTOList(shops);
    }

    @Override
    public List<ShopResponseDTO> searchShop(String keyword) {
        List<Shop> shops = shopRepository.findBySearchQuery(keyword);
        return convertToDTOList(shops);
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
    public ShopResponseDTO getShopByUserId(Long userId) throws Exception {
        Shop shop = shopRepository.findByOwnerId(userId);
        if (shop==null){
            throw new Exception("Shop not found with owner id"+userId);
        }
        return convertToDTO(shop);
    }

    @Override
    public Shop getShopEntityByUserId(Long userId) throws Exception {
        Shop shop = shopRepository.findByOwnerId(userId);

        if (shop == null) {
            throw new Exception("Shop not found with owner id " + userId);
        }
        return shop;  // returning entity
    }


    @Override
    public ShopDTO addFavourites(Long shopId, User user) throws Exception {

        Shop shop = findShopById(shopId);

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setDescription(shop.getDescription());
        shopDTO.setTitle(shop.getShopName());
        shopDTO.setImages(shop.getImages());
        shopDTO.setId(shopId);

//        if (user.getFavorites().contains(shopDTO)){
//            user.getFavorites().remove(shopDTO);
//        }
//        else user.getFavorites().add(shopDTO);

        boolean isFavorited = false;
        List<ShopDTO> favorites = user.getFavorites();
        for (ShopDTO favorite : favorites){
            if (favorite.getId().equals(shopId)){
                isFavorited = true;
                break;
            }
        }

        if (isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(shopId));
        }else {
            favorites.add(shopDTO);
        }

        userRepository.save(user);
        return shopDTO;
    }

    @Override
    public ShopResponseDTO updateShopStatus(Long id) throws Exception {

        Shop shop = findShopById(id);
        shop.setOpen(!shop.isOpen());
        shopRepository.save(shop);
        return convertToDTO(shop);
    }
}
