package com.example.zti_project.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private Long year_prod;
    private Double engine;
    private String fuel_type;
    private String color;
    private String gear_type;
    private Long price;
    private String imageurl;

    @Column(name = "id_user")
    private Long idUser;
}
