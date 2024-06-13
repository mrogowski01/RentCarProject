package com.example.zti_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffer;

    @Column(name = "id_car", nullable = false)
    private Long carId;

    @Column(name = "id_user")
    private Long idUser;

//    private Boolean isAvailable;
    private Long price;
    private LocalDate availableFrom;
    private LocalDate availableTo;

    @Transient
    private Car carDetails;  // Transient field to hold car details

    public void setCarDetails(Car carDetails) {
        this.carDetails = carDetails;
    }

    public Long getIdCar() {
        return carId;
    }
}
