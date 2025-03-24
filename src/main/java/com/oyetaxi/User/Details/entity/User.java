package com.oyetaxi.User.Details.entity;


import com.oyetaxi.User.Details.misc.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

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
        this.password=user.password;
    }

    public UserType getUserType() {
        return type;
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

}


