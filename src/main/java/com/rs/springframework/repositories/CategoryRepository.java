package com.rs.springframework.repositories;

import com.rs.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * created by rs 1/2/2022.
 */
public interface CategoryRepository extends CrudRepository<Category, String> {
    Optional<Category> findByDescription(String description);
}
