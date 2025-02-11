package com.onlinestore.backend.Repositories;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.onlinestore.backend.user.MyUser;

public interface UserRepositories extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByLogin(String login);
}
