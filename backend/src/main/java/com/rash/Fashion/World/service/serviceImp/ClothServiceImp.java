package com.rash.Fashion.World.service.serviceImp;

import com.rash.Fashion.World.dto.ClothResponseDTO;
import com.rash.Fashion.World.dto.ShopResponseDTO;
import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Cloth;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.repository.ClothRepository;
import com.rash.Fashion.World.request.CreateClothRequest;
import com.rash.Fashion.World.service.ClothService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClothServiceImp implements ClothService {

    @Autowired
    private ClothRepository clothRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Convert Entity → DTO
    private ClothResponseDTO convertToDTO(Cloth cloth){
        ClothResponseDTO dto = modelMapper.map(cloth, ClothResponseDTO.class);

        if(cloth.getClothCategory() != null){
            dto.setCategoryName(cloth.getClothCategory().getCategoryName());
        }

        return dto;
    }

    @Override
    public ClothResponseDTO createCloth(CreateClothRequest request, Category category, Shop shop) {
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
        return convertToDTO(savedCloth);

    }

    @Override
    public void deleteCloth(Long clothId) throws Exception {
        Cloth cloth = findClothById(clothId);
        cloth.setShop(null);
        clothRepository.save(cloth);
    }

    @Override
    public List<ClothResponseDTO> getShopsCloth(
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

        return cloths.stream().map(this::convertToDTO).toList();
    }

    private List<Cloth> filterByCategory(List<Cloth> cloths, String clothCategory) {
        String lowerCategory = clothCategory.toLowerCase();
        return cloths.stream()
                .filter(cloth -> cloth.getClothCategory() != null &&
                        cloth.getClothCategory().getCategoryName().toLowerCase().equals(lowerCategory))
                .collect(Collectors.toList());
    }

    private List<Cloth> filterByMale(List<Cloth> cloths) {
        return cloths.stream()
                .filter(cloth -> cloth.isMale())
                .collect(Collectors.toList());
    }

    private List<Cloth> filterByFemale(List<Cloth> cloths) {
        return cloths.stream()
                .filter(cloth -> cloth.isFemale())
                .collect(Collectors.toList());
    }

    @Override
    public List<ClothResponseDTO> searchCloth(String keyword) {
    if (keyword == null || keyword.isEmpty()) return List.of();

    String kw = "%" + keyword.toLowerCase() + "%"; // wrap with % for LIKE

        return clothRepository.searchCloth(kw)
                .stream()
                .map(this::convertToDTO)
                .toList();
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
    public ClothResponseDTO updateAvailability(Long clothId) throws Exception {
        Cloth cloth = findClothById(clothId);
        cloth.setAvailable(!cloth.isAvailable());
        return convertToDTO(clothRepository.save(cloth));
    }
}
