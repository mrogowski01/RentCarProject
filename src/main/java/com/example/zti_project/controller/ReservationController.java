package com.example.zti_project.controller;

import com.example.zti_project.exceptions.InvalidReservationDateException;
import com.example.zti_project.exceptions.OfferNotFoundException;
import com.example.zti_project.model.Offer;
import com.example.zti_project.model.Reservation;
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
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation createdReservation = reservationService.createReservation(reservation);
            return ResponseEntity.ok(createdReservation);
        } catch (OfferNotFoundException | InvalidReservationDateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating reservation: " + e.getMessage());
        }
    }


    @GetMapping("/{idReservation}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long idReservation) {
        Optional<Reservation> reservation = reservationService.getReservationById(idReservation);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idReservation}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long idReservation) {
        try {
            reservationService.deleteReservationAndUpdateOffer(idReservation);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting reservation: " + e.getMessage());
        }
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long idUser) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(idUser);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{userId}/reservations-with-offers")
    public List<Reservation> getReservationsWithOffersByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsWithOffersByUserId(userId);
    }
}
