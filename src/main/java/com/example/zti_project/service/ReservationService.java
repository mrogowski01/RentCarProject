package com.example.zti_project.service;

import com.example.zti_project.model.Car;
import com.example.zti_project.model.Offer;
import com.example.zti_project.model.Reservation;
import com.example.zti_project.repository.CarRepository;
import com.example.zti_project.repository.OfferRepository;
import com.example.zti_project.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CarRepository carRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation);
    }

    public void deleteReservation(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }

    public List<Reservation> getReservationsByUserId(Long idUser) {
        return reservationRepository.findByIdUser(idUser);
    }

    public List<Reservation> getReservationsWithOffersByUserId(Long userId) {
        return reservationRepository.findByIdUser(userId).stream()
                .map(reservation -> {
                    Offer offer = offerRepository.findByIdOffer(reservation.getIdOffer()).orElse(null);
                    if (offer != null) {
                        Car car = carRepository.findById(offer.getCarId()).orElse(null);
                        offer.setCarDetails(car);
                    }
                    reservation.setOfferDetails(offer);
                    return reservation;
                })
                .collect(Collectors.toList());
    }

    public void deleteReservationsByCarId(Long carId) {
        List<Offer> offers = offerRepository.findByCarId(carId);
        for (Offer offer : offers) {
            List <Reservation> reservations = reservationRepository.findByIdOffer(offer.getIdOffer());
            reservationRepository.deleteAll(reservations);

        }
    }

    public void deleteReservationsByOfferId(Long offerId) {
        List<Reservation> reservations = reservationRepository.findByIdOffer(offerId);
        reservationRepository.deleteAll(reservations);

    }
}
