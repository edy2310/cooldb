package com.cooldb.security.repositories;

import com.cooldb.security.models.SecureUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecureUserRepository extends CrudRepository<SecureUser, Long> {
    Optional<SecureUser> findByEmail(String email);
}
