package com.rash.Fashion.World.request;

import com.rash.Fashion.World.model.Category;
import com.rash.Fashion.World.model.ColorItem;
import com.rash.Fashion.World.model.Gender;
import lombok.Data;

import java.util.List;

@Data
public class CreateClothRequest {

    private String clothName;
    private String description;
    private Long price;

//    private Category clothCategory;
    private Long clothCategoryId;
    private List<String> images;
    private Long shopId;

//    boolean gender;
    private Gender gender;
    private List<ColorItem> colors;

    private boolean available;

}
