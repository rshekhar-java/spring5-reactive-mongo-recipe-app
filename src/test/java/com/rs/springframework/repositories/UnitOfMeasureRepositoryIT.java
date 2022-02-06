package com.rs.springframework.repositories;

import com.rs.springframework.bootstrap.RecipeBootstrap;
import com.rs.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created by rs 1/6/2022.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
@Ignore
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);

        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
//    @DirtiesContext
    public void findByDescription() {
        Optional<UnitOfMeasure> uomOptional=unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon",uomOptional.get().getDescription());
    }
    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional=unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup",uomOptional.get().getDescription());
    }
}