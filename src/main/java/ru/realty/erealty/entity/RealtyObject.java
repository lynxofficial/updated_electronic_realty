package ru.realty.erealty.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
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

    public RealtyObject() {
    }

    public Integer getRealtyObjectId() {
        return realtyObjectId;
    }

    public String getRealtyObjectName() {
        return realtyObjectName;
    }

    public void setRealtyObjectName(String realtyObjectName) {
        this.realtyObjectName = realtyObjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRealtyObjectId(Integer realtyObjectId) {
        this.realtyObjectId = realtyObjectId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RealtyObject that = (RealtyObject) o;
        return Objects.equals(realtyObjectId, that.realtyObjectId)
                && Objects.equals(realtyObjectName, that.realtyObjectName)
                && Objects.equals(description, that.description)
                && Objects.equals(imageUrl, that.imageUrl)
                && Objects.equals(price, that.price)
                && Objects.equals(square, that.square)
                && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realtyObjectId, realtyObjectName, description, imageUrl, price, square, address);
    }

    @Override
    public String toString() {
        return "RealtyObject{" +
                "address='" + address + '\'' +
                ", square=" + square +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", realtyObjectName='" + realtyObjectName + '\'' +
                ", realtyObjectId=" + realtyObjectId +
                '}';
    }
}
