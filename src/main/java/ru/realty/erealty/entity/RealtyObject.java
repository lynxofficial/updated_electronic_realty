package ru.realty.erealty.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "realty_objects")
@Getter
@EqualsAndHashCode(exclude = "user")
public class RealtyObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "realty_object_id")
    private Integer realtyObjectId;
    @Column(name = "realty_object_name")
    @Setter
    private String realtyObjectName;
    @Column(name = "description")
    @Setter
    private String description;
    @Column(name = "image_url")
    @Setter
    private String imageUrl;
    @Column(name = "price")
    @Setter
    private BigDecimal price;
    @Column(name = "square")
    @Setter
    private Double square;
    @Column(name = "address")
    @Setter
    private String address;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @Setter
    private User user;
}
