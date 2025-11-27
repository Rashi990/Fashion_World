package com.rash.Fashion.World.service;

import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.request.CreateClothRequest;

import java.util.List;

public interface ClothService {

    public Cloth createCloth(CreateClothRequest request, Category category, Shop shop);

    void deleteCloth(Long clothId) throws Exception;

    public List<Cloth> getShopsCloth(
            Long shopId,
            boolean isMale,
            boolean isFemale,
            String clothCategory
    );

    public List<Cloth> searchCloth(String keyword);

    public Cloth findClothById(Long clothId) throws Exception;

    public Cloth updateAvailability(Long clothId) throws Exception;

}
