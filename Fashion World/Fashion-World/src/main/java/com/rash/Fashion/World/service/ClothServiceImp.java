package com.rash.Fashion.World.service;

import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.repository.ClothRepository;
import com.rash.Fashion.World.request.CreateClothRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClothServiceImp implements ClothService{

    @Autowired
    private ClothRepository clothRepository;

    @Override
    public Cloth createCloth(CreateClothRequest request, Category category, Shop shop) {
        Cloth cloth = new Cloth();
        cloth.setClothCategory(category);
        cloth.setShop(shop);
        cloth.setDescription(request.getDescription());
        cloth.setClothName(request.getClothName());
        cloth.setImages(request.getImages());
        cloth.setPrice(request.getPrice());
        cloth.setColors(request.getColors());
        cloth.setAvailable(request.isAvailable());

        // Default gender
        cloth.setMale(false);
        cloth.setFemale(false);

        // Gender mapping using enum
        if(request.getGender() != null){
            switch(request.getGender()){
                case MALE -> { cloth.setMale(true); cloth.setFemale(false); }
                case FEMALE -> { cloth.setMale(false); cloth.setFemale(true); }
            }
        }

        Cloth savedCloth = clothRepository.save(cloth);
        shop.getCloths().add(savedCloth);

        return savedCloth;
    }

    @Override
    public void deleteCloth(Long clothId) throws Exception {

        Cloth cloth = findClothById(clothId);
        cloth.setShop(null);
        clothRepository.save(cloth);
    }

    @Override
    public List<Cloth> getShopsCloth(
            Long shopId,
            boolean isMale,
            boolean isFemale,
            String clothCategory
    ) {

        List<Cloth> cloths = clothRepository.findByShopId(shopId);

        // Apply gender filters only if one of them is true
        if (isMale && !isFemale) {       // Only male requested
            cloths = filterByMale(cloths);
        } else if (!isMale && isFemale) { // Only female requested
            cloths = filterByFemale(cloths);
        }
            // if both are true or both are false, return all (no filtering)

        if (clothCategory!=null && !clothCategory.equals("")){
            cloths = filterByCategory(cloths,clothCategory);
        }

        return cloths;
    }

    private List<Cloth> filterByCategory(List<Cloth> cloths, String clothCategory) {
        return cloths.stream().filter(cloth -> {
            if (cloth.getClothCategory()!=null){
                return cloth.getClothCategory().getCategoryName().equals(clothCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Cloth> filterByMale(List<Cloth> cloths) {
        return cloths.stream().filter(cloth -> cloth.isMale()).collect(Collectors.toList());
    }

    private List<Cloth> filterByFemale(List<Cloth> cloths) {
        return cloths.stream().filter(cloth -> cloth.isFemale()).collect(Collectors.toList());
    }

    @Override
    public List<Cloth> searchCloth(String keyword) {
        return clothRepository.searchCloth(keyword);
    }

    @Override
    public Cloth findClothById(Long clothId) throws Exception {
        Optional<Cloth> optionalCloth = clothRepository.findById(clothId);

        if (optionalCloth.isEmpty()){
            throw new Exception("Cloth not exist...");
        }
        return optionalCloth.get();
    }

    @Override
    public Cloth updateAvailability(Long clothId) throws Exception {
        Cloth cloth = findClothById(clothId);
        cloth.setAvailable(!cloth.isAvailable());
        return clothRepository.save(cloth);
    }
}
