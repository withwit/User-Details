package com.oyetaxi.User.Details.repository;

import com.oyetaxi.User.Details.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


}
