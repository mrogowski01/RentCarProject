package com.example.zti_project.controller;

import com.example.zti_project.model.Car;
import com.example.zti_project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.save(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Car car = carService.findById(id);
        if (car != null) {
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setYear_prod(carDetails.getYear_prod());
            car.setEngine(carDetails.getEngine());
            car.setFuel_type(carDetails.getFuel_type());
            car.setColor(carDetails.getColor());
            car.setGear_type(carDetails.getGear_type());
            car.setPrice(carDetails.getPrice());
            car.setImageurl(carDetails.getImageurl());
            return carService.save(car);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
    }
}
