package com.oyetaxi.User.Details.entity;


import com.oyetaxi.User.Details.misc.UserType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;
    @Column(name = "create_dt")
    private LocalDateTime createDt;
    @Column(name = "update_dt")
    private LocalDateTime updateDt;
    private String name;
    @Column(name = "mobile_number")
    private String mobileNumber;
    private String email;
    @Column(name = "current_loc")
    private String currentLoc;


    protected User(User user) {
        LocalDateTime nowDate = LocalDateTime.now();
        this.createDt = nowDate;
        this.updateDt = nowDate;
        this.name = user.name;
        this.type = user.type;
        this.mobileNumber = user.mobileNumber;
        this.email = user.email;
        this.currentLoc = user.currentLoc;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentLoc() {
        return currentLoc;
    }

    public void setCurrentLoc(String currentLoc) {
        this.currentLoc = currentLoc;
    }


    public UserType getUserType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", createDt=" + createDt +
                ", updateDt=" + updateDt +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", currentLoc='" + currentLoc + '\'' +
                '}';
    }


    public String getUsername() {
        return String.valueOf(this.id);
    }
}


