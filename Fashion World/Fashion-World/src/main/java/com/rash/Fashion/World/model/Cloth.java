package com.rash.Fashion.World.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cloth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clothName;
    private String description;

    private Long price;

    @ManyToOne
    private Category clothCategory;

    @Column(length = 1000)
    @ElementCollection //create a separate table for the collection
    private List<String> images;

    private boolean available;

    @ManyToOne // inside one shop multiple cloths
    private Shop shop;

    private boolean isMale;
    private boolean isFemale;

    @ManyToMany
    private List<ColorItem> colors= new ArrayList<>();

    private Date creationDate;
}
