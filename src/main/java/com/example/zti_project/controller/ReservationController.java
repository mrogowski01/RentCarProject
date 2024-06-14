package com.example.zti_project.controller;

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
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping("/{idReservation}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long idReservation) {
        Optional<Reservation> reservation = reservationService.getReservationById(idReservation);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idReservation}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long idReservation) {
        reservationService.deleteReservation(idReservation);
        return ResponseEntity.ok().build();
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
