package com.bakerypos.bakery_pos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bakerypos.bakery_pos.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByNoTelpAndPassword(String noTelp, String password);
}