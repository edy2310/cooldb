package com.cooldb.core.repositories;

import com.cooldb.core.models.Database;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepository extends CrudRepository<Database, Long> {
}
