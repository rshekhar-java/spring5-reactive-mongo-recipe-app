package com.rs.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * created by rs 2/2/2022.
 */
@Getter
@Setter
@Document
public class Category {
    @Id
    private String id;
    private String description;
    private Set<Recipe> recipes;
}
