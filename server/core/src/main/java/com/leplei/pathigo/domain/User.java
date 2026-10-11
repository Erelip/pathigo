package com.leplei.pathigo.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String email;
    private final String username;
    private final String password;
    private final String nickname;
    private final String phoneNumber;
    private final String provider;
    private final String providerId;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.phoneNumber = builder.phoneNumber;
        this.provider = builder.provider;
        this.providerId = builder.providerId;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID id;
        private String email;
        private String username;
        private String password;
        private String nickname;
        private String phoneNumber;
        private String provider;
        private String providerId;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}