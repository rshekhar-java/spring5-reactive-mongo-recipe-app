package com.rs.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * created by rs 1/6/2022.
 */
public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }
    @Test
    public void getId() {
        String idValue = "4";
        category.setId(idValue);
        assertEquals(idValue,category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}