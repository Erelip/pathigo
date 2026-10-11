package com.leplei.pathigo.persistence.entity;

import com.leplei.pathigo.domain.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "username", nullable = true)
    private String username;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;
    @Column(name = "provider", nullable = true)
    private String provider;;
    @Column(name = "providerId", nullable = true)
    private String providerId;

    public UserEntity(UUID id, String email, String username, String password, String nickname, String phoneNumber, String provider, String providerId) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.providerId = providerId;
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
    }

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public User toDomain() {
        return new User.Builder()
                .id(id)
                .email(email)
                .username(username)
                .password(password)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}