package com.example.zti_project.repository;

import com.example.zti_project.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCarId(Long carId);
}
