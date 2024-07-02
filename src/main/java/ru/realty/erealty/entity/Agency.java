package ru.realty.erealty.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "agencies")
@Getter
@EqualsAndHashCode
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer agencyId;
    @Column(name = "agency_name")
    @Setter
    private String agencyName;
    @Column(name = "address")
    @Setter
    private String address;
}

