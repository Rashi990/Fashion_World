package com.rash.Fashion.World.service;

import com.rash.Fashion.World.dto.ClothResponseDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.request.CreateClothRequest;

import java.util.List;

public interface ClothService {

    public ClothResponseDTO createCloth(CreateClothRequest request, Category category, Shop shop);

    void deleteCloth(Long clothId) throws Exception;

    public List<ClothResponseDTO> getShopsCloth(
            Long shopId,
            boolean isMale,
            boolean isFemale,
            String clothCategory
    );

    public List<ClothResponseDTO> searchCloth(String keyword);

    public Cloth findClothById(Long clothId) throws Exception; // entity allowed internally

    public ClothResponseDTO updateAvailability(Long clothId) throws Exception;

}
