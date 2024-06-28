package ru.realty.erealty.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
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
