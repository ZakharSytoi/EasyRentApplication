package com.example.deploydemo.repository.daos;

import com.example.deploydemo.repository.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT a FROM Apartment a WHERE a.owner.id = ?1")
    List<Apartment> findAllByUserId(Long userId);
}
