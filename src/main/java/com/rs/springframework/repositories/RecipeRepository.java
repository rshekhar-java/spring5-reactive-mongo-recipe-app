package com.rs.springframework.repositories;

import com.rs.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * created by rs 1/2/2022.
 */
public interface RecipeRepository extends CrudRepository<Recipe,String> {

}
