package com.rs.springframework.repositories.reactive;

import com.rs.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * created by rs 2/7/2022.
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {
}
