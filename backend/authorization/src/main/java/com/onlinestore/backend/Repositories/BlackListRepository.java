package com.onlinestore.backend.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.onlinestore.backend.Models.BlackListTokens;

public interface BlackListRepository
    extends JpaRepository<BlackListTokens, Integer> {
    Optional<BlackListTokens> findByToken(String token);
}
