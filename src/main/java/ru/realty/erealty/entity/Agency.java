package ru.realty.erealty.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "agencies")
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer agencyId;
    @Column(name = "agency_name")
    private String agencyName;
    @Column(name = "address")
    private String address;
}
