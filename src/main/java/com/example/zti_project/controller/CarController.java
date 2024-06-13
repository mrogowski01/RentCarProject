package com.example.zti_project.controller;

import com.example.zti_project.model.Car;
import com.example.zti_project.service.CarService;
import com.example.zti_project.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.findAll();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = Optional.ofNullable(carService.findById(id));
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.save(car);
        return ResponseEntity.ok(savedCar);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable Long userId) {
        List<Car> cars = carService.getCarsByUserId(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Optional<Car> optionalCar = Optional.ofNullable(carService.findById(id));
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setYear_prod(carDetails.getYear_prod());
            car.setEngine(carDetails.getEngine());
            car.setFuel_type(carDetails.getFuel_type());
            car.setColor(carDetails.getColor());
            car.setGear_type(carDetails.getGear_type());
            car.setImageurl(carDetails.getImageurl());
            Car updatedCar = carService.save(car);
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        Optional<Car> optionalCar = Optional.ofNullable(carService.findById(id));
        if (optionalCar.isPresent()) {
            // Usuwamy oferty powiązane z samochodem
            offerService.deleteOffersByCarId(id);
            // Usuwamy samochód
            carService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
