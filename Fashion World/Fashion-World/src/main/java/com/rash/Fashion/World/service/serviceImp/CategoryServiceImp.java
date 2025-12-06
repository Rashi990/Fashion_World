package com.rash.Fashion.World.service.serviceImp;

import com.rash.Fashion.World.dto.CategoryResponseDTO;
import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.Shop;
import com.rash.Fashion.World.repository.CategoryRepository;
import com.rash.Fashion.World.service.CategoryService;
import com.rash.Fashion.World.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

//    DTO Converter
    private CategoryResponseDTO convertToDTO(Category category){
        return modelMapper.map(category,CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO createCategory(String name, Long userId) throws Exception{
        Shop shop = shopService.getShopEntityByUserId(userId);

        Category category = new Category();
        category.setCategoryName(name);
        category.setShop(shop);

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public List<CategoryResponseDTO> findCategoryByShopId(Long id) throws Exception {
        Shop shop = shopService.findShopByIdEntity(id);

        return categoryRepository.findByShopId(shop.getId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()){
            throw new Exception("Category not found");
        }

        return optionalCategory.get();
    }
}
