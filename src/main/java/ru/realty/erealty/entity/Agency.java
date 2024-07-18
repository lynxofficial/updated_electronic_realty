package ru.realty.erealty.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.realty.erealty.entity.support.BaseEntity;

@Entity
@Table(name = "agencies")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Agency extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
}
