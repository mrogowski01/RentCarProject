package com.example.zti_project.controller;

import com.example.zti_project.model.Car;
import com.example.zti_project.model.Offer;
import com.example.zti_project.service.OfferService;
import com.example.zti_project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.ok(createdOffer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findByIdOffer(id);
        return offer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Offer>> getOffersByCarId(@PathVariable Long carId) {
        List<Offer> offers = offerService.getOffersByCar(carId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/user/{userId}/offers-with-cars")
    public List<Offer> getOffersWithCarsByUserId(@PathVariable Long userId) {
        return offerService.getOffersWithCarsByUserId(userId);
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffersWithCars();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/user/{userId}")
    public List<Offer> getOffersByUserId(@PathVariable Long userId) {
        return offerService.getOffersByUserId(userId);
    }

    @GetMapping("/user/{userId}/cars")
    public List<Car> getCarsForOffersByUserId(@PathVariable Long userId) {
        return offerService.getCarsForOffersByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offerDetails) {
        System.out.println(id);
        Offer updatedOffer = offerService.updateOffer(id, offerDetails);
        if (updatedOffer != null) {
            return ResponseEntity.ok(updatedOffer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        reservationService.deleteReservationsByOfferId(id);
        if (offerService.deleteOffer(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
