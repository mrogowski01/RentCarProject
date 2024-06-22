package com.example.zti_project.service;

import com.example.zti_project.exceptions.InvalidOfferDateException;
import com.example.zti_project.exceptions.InvalidOfferDateExceptionEdit;
import com.example.zti_project.model.Car;
import com.example.zti_project.model.Offer;
import com.example.zti_project.repository.CarRepository;
import com.example.zti_project.repository.OfferRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CarRepository carRepository;

    public Offer createOffer(Offer offer) {
        if (offer.getAvailableFrom().isAfter(offer.getAvailableTo())) {
            throw new InvalidOfferDateException("Offer start date must be before end date.");
        }
        return offerRepository.save(offer);
    }

    public List<Offer> getOffersByCar(Long carId) {
        return offerRepository.findByCarId(carId);
    }

    public Optional<Offer> findByIdOffer(Long IdOffer) {
        return offerRepository.findByIdOffer(IdOffer);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public List<Offer> getOffersByUserId(Long userId) {
        return offerRepository.findByIdUser(userId);
    }

    public List<Car> getCarsForOffersByUserId(Long userId) {
        List<Offer> offers = offerRepository.findByIdUser(userId);
        return offers.stream()
                .map(offer -> carRepository.findById(offer.getCarId()).orElse(null))
                .collect(Collectors.toList()).reversed();
    }

    public List<Offer> getOffersWithCarsByUserId(Long userId) {
        return offerRepository.findByIdUser(userId).stream()
                .map(offer -> {
                    Car car = carRepository.findById(offer.getCarId()).orElse(null);
                    offer.setCarDetails(car);
                    return offer;
                })
                .collect(Collectors.toList());
    }

    public List<Offer> getAllOffersWithCars() {
        return offerRepository.findAll().stream()
                .map(offer -> {
                    Car car = carRepository.findById(offer.getCarId()).orElse(null);
                    offer.setCarDetails(car);
                    return offer;
                })
                .collect(Collectors.toList());
    }

    private static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    public Offer updateOffer(Long id, Offer offerDetails) {
        Optional<Offer> optionalOffer = offerRepository.findByIdOffer(id);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            if (offerDetails.getAvailableFrom().isAfter(offerDetails.getAvailableTo())) {
                throw new InvalidOfferDateExceptionEdit("Offer start date must be before end date.");
            }
            offer.setCarId(offerDetails.getIdCar());
            offer.setIdUser(offerDetails.getIdUser());
            offer.setPrice(offerDetails.getPrice());
            offer.setAvailableFrom(offerDetails.getAvailableFrom());
            offer.setAvailableTo(offerDetails.getAvailableTo());
            return offerRepository.save(offer);
        }
        return null;
    }


//    public Offer updateOffer(Long id, Offer offerDetails) {
//        Optional<Offer> optionalOffer = offerRepository.findById(id);
//        if (optionalOffer.isPresent()) {
//            Offer offer = optionalOffer.get();
//            Long idCar = offerDetails.getCar();
//            // Wczytanie obiektu Car na podstawie idCar
//            Car car = carRepository.findById(idCar).orElse(null);
//            if (car != null) {
//                offer.setCar(idCar);
//                offer.setIdUser(offerDetails.getIdUser());
//                offer.setPrice(offerDetails.getPrice());
//                offer.setAvailableFrom(offerDetails.getAvailableFrom());
//                offer.setAvailableTo(offerDetails.getAvailableTo());
//                System.out.println(idCar);
//                System.out.println(car);
//                offer.setCarDetails(car); // Ustawienie szczegółów samochodu w ofercie
//                return offerRepository.save(offer);
//            } else {
//                throw new RuntimeException("Car with id " + idCar + " not found!");
//            }
//        }
//        return null;
//    }

    public boolean deleteOffer(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isPresent()) {
            offerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteOffersByCarId(Long carId) {
        List<Offer> offers = offerRepository.findByCarId(carId);
        for (Offer offer : offers) {
            offerRepository.delete(offer);
        }
    }
}
