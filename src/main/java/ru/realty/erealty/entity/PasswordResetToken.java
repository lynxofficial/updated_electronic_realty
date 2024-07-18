package ru.realty.erealty.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.realty.erealty.entity.support.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "passwordresettoken")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken extends BaseEntity {
    private String token;
    private LocalDateTime expiryDateTime;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
