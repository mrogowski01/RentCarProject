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

    @ManyToOne
    @JoinColumn(name = "id_car", nullable = false)
    private Car car;

    private Boolean isAvailable;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}
