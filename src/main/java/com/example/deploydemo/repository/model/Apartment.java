package com.example.deploydemo.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "address")
    private String address;
    @Column(name = "note")
    private String note;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
