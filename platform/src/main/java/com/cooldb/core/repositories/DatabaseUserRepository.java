package com.cooldb.core.repositories;

import com.cooldb.core.models.DatabaseUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseUserRepository extends CrudRepository<DatabaseUser, Long> {
}
