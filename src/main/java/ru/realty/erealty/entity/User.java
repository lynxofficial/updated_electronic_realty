package ru.realty.erealty.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.realty.erealty.entity.support.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "password_for_digital_signature")
    private String passwordForDigitalSignature;
    @Column(name = "digital_signature")
    private String digitalSignature;
    @Column(name = "role")
    private String role;
    @Column(name = "enable")
    private Boolean enable;
    @Column(name = "verification_code")
    private String verificationCode;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PasswordResetToken passwordResetToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RealtyObject> realtyObjects;
}
