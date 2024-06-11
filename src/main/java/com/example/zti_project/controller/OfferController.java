package com.example.zti_project.controller;

import com.example.zti_project.model.Offer;
import com.example.zti_project.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.ok(createdOffer);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Offer>> getOffersByCarId(@PathVariable Long carId) {
        List<Offer> offers = offerService.getOffersByCarId(carId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }
}
