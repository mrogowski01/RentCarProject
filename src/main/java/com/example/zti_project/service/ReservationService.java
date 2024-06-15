package com.example.zti_project.service;

import com.example.zti_project.exceptions.InvalidReservationDateException;
import com.example.zti_project.exceptions.OfferNotFoundException;
import com.example.zti_project.model.Car;
import com.example.zti_project.model.Offer;
import com.example.zti_project.model.Reservation;
import com.example.zti_project.repository.CarRepository;
import com.example.zti_project.repository.OfferRepository;
import com.example.zti_project.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        validateReservationDates(reservation);

        Optional<Offer> offerOptional = offerRepository.findById(reservation.getIdOffer());
        if (offerOptional.isEmpty()) {
            throw new OfferNotFoundException("Offer not found for id: " + reservation.getIdOffer());
        }

        Offer offer = offerOptional.get();
        if (offer.isReserved()) {
            throw new InvalidReservationDateException("Offer is already reserved.");
        }

        offer.setReserved(true);
        offerRepository.save(offer);

        return reservationRepository.save(reservation);
    }

    private void validateReservationDates(Reservation reservation) {
        Optional<Offer> offer = offerRepository.findById(reservation.getIdOffer());
        if (offer.isEmpty()) {
            throw new OfferNotFoundException("Offer not found for id: " + reservation.getIdOffer());
        }

        if (reservation.getDateTo().isBefore(reservation.getDateFrom())) {
            throw new InvalidReservationDateException("dateTo must be before dateTo");
        }

        if (reservation.getDateTo().isAfter(offer.get().getAvailableTo()) &&
                reservation.getDateFrom().isBefore(offer.get().getAvailableFrom())) {
            throw new InvalidReservationDateException("Date of reservation must be in range of car availability");
        }

        if (reservation.getDateFrom().isBefore(offer.get().getAvailableFrom())) {
            throw new InvalidReservationDateException("dateFrom must be after offer's availableFrom: " + offer.get().getAvailableFrom());
        }

        if (reservation.getDateTo().isAfter(offer.get().getAvailableTo())) {
            throw new InvalidReservationDateException("dateTo must be before offer's availableTo: " + offer.get().getAvailableTo());
        }
    }

    public Optional<Reservation> getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation);
    }

    public Optional<Offer> getOfferByIdOffer(Long idOffer) {
        return offerRepository.findById(idOffer);
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

    @Transactional
    public void deleteReservationAndUpdateOffer(Long idReservation) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(idReservation);
        if (reservationOptional.isEmpty()) {
            throw new RuntimeException("Reservation not found for id: " + idReservation);
        }

        Reservation reservation = reservationOptional.get();
        Optional<Offer> offerOptional = offerRepository.findById(reservation.getIdOffer());
        if (offerOptional.isEmpty()) {
            throw new OfferNotFoundException("Offer not found for id: " + reservation.getIdOffer());
        }

        Offer offer = offerOptional.get();
        offer.setReserved(false);
        offerRepository.save(offer);

        reservationRepository.deleteById(idReservation);
    }
}
