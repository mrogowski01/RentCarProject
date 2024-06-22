package com.example.zti_project.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

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

    private Long price;

    private LocalDate availableFrom;

    private LocalDate availableTo;

    private boolean reserved = false;

    @Transient
    private Car carDetails;

    public void setCarDetails(Car carDetails) {
        this.carDetails = carDetails;
    }

    public Long getIdCar() {
        return carId;
    }

}
