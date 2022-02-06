package com.rs.springframework.repositories;

import com.rs.springframework.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * created by rs 1/2/2022.
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
