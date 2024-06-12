package com.example.zti_project.service;

import com.example.zti_project.exceptions.ResourceNotFoundException;
import com.example.zti_project.model.Car;
import com.example.zti_project.model.UserCar;
import com.example.zti_project.model.Users;
import com.example.zti_project.repository.CarRepository;
import com.example.zti_project.repository.UserCarRepository;
import com.example.zti_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserCarRepository userCarRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public UserCar assignCarToUser(Long userId, Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserCar userCar = new UserCar();
        userCar.setCar(car);
        userCar.setUser(user);

        return userCarRepository.save(userCar);
    }

    public List<Car> getCarsByUserId(Long userId) {
        return carRepository.findByIdUser(userId);
    }

//    public List<Car> getCarsByUserId(Long userId) {
//        Users user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        Collection<Object> userCars = userCarRepository.findByUser(user);
//        List<Car> cars = userCars.stream()
//                .map(userCar -> carRepository.findById(userCar.getCar().getId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Car not found")))
//                .collect(Collectors.toList());
//        return cars;
//    }
}
