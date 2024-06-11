package com.example.zti_project.service;

import com.example.zti_project.model.Offer;
import com.example.zti_project.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public List<Offer> getOffersByCarId(Long carId) {
        return offerRepository.findByCarId(carId);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
}
