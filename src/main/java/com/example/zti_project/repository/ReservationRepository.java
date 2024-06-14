package com.example.zti_project.repository;

import com.example.zti_project.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByIdUser(Long idUser);
    List<Reservation> findByIdOffer(Long idOffer);
}
