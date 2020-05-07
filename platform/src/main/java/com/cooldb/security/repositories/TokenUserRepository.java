package com.cooldb.security.repositories;

import com.cooldb.security.models.TokenUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenUserRepository extends CrudRepository<TokenUser, Long> {
    Optional<TokenUser> findByUserId(Long userId);

    Optional<TokenUser> findByToken(String token);
}
