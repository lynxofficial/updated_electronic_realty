package ru.realty.erealty.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@EqualsAndHashCode(exclude = {"passwordResetToken", "realtyObjects"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "full_name")
    @Setter
    private String fullName;
    @Column(name = "email")
    @Setter
    private String email;
    @Column(name = "password")
    @Setter
    private String password;
    @Column(name = "balance")
    @Setter
    private BigDecimal balance;
    @Column(name = "password_for_digital_signature")
    @Setter
    private String passwordForDigitalSignature;
    @Column(name = "digital_signature")
    @Setter
    private String digitalSignature;
    @Column(name = "role")
    @Setter
    private String role;
    @Column(name = "enable")
    @Setter
    private Boolean enable;
    @Column(name = "verification_code")
    @Setter
    private String verificationCode;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PasswordResetToken passwordResetToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RealtyObject> realtyObjects;
}
