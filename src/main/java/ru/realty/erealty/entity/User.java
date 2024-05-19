package ru.realty.erealty.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
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
    @Column(name="verification_code")
    private String verificationCode;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PasswordResetToken passwordResetToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RealtyObject> realtyObjects;

    public User() {
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public List<RealtyObject> getRealtyObjects() {
        return realtyObjects;
    }

    public void setRealtyObjects(List<RealtyObject> realtyObjects) {
        this.realtyObjects = realtyObjects;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPasswordForDigitalSignature() {
        return passwordForDigitalSignature;
    }

    public void setPasswordForDigitalSignature(String passwordForDigitalSignature) {
        this.passwordForDigitalSignature = passwordForDigitalSignature;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId)
                && Objects.equals(fullName, user.fullName)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(balance, user.balance)
                && Objects.equals(passwordForDigitalSignature, user.passwordForDigitalSignature)
                && Objects.equals(digitalSignature, user.digitalSignature)
                && Objects.equals(role, user.role) && Objects.equals(enable, user.enable)
                && Objects.equals(verificationCode, user.verificationCode)
                && Objects.equals(passwordResetToken, user.passwordResetToken)
                && Objects.equals(realtyObjects, user.realtyObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fullName, email, password, balance, passwordForDigitalSignature, digitalSignature,
                role, enable, verificationCode, passwordResetToken, realtyObjects);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
