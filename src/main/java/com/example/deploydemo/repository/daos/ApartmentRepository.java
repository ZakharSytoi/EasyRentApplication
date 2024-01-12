package com.example.deploydemo.repository.daos;

import com.example.deploydemo.repository.model.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT a FROM Apartment a WHERE a.owner.id = ?1")
    Page<Apartment> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT a FROM Apartment a WHERE a.id=?1 AND a.owner.id = ?2")
    Optional<Apartment> findByIdAndUserId(Long id, Long userId);

    @Query("DELETE FROM Apartment a WHERE a.id=?1 AND a.owner.id = ?2")
    void deleteByIdAndUserId(Long id, Long userId);
}
