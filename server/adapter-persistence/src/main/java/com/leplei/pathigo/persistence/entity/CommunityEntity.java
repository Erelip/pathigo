package com.leplei.pathigo.persistence.entity;

import com.leplei.pathigo.domain.Community;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class CommunityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String avatar;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;

    public CommunityEntity(UUID id, String name, String description, String avatar, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CommunityEntity(Community community) {
        this.id = community.getId();
        this.name = community.getName();
        this.description = community.getDescription();
        this.avatar = community.getAvatar();
        this.createdAt = community.getCreatedAt();
        this.updatedAt = community.getUpdatedAt();
    }

    public CommunityEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}