package ru.realty.erealty.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
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

    public Agency() {
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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
        Agency agency = (Agency) o;
        return Objects.equals(agencyId, agency.agencyId)
                && Objects.equals(agencyName, agency.agencyName)
                && Objects.equals(address, agency.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencyId, agencyName, address);
    }

    @Override
    public String toString() {
        return "Agency{" +
                "agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
