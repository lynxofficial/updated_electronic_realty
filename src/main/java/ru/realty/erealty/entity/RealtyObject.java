package ru.realty.erealty.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "realty_objects")
public class RealtyObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "realty_object_id")
    private Integer realtyObjectId;
    @Column(name = "realty_object_name")
    private String realtyObjectName;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "square")
    private Double square;
    @Column(name = "address")
    private String address;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
