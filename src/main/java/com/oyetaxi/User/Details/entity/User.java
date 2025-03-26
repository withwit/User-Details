package com.oyetaxi.User.Details.entity;


import com.oyetaxi.User.Details.misc.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
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
        this.password = user.password;
    }

    public UserType getUserType() {
        return type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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


