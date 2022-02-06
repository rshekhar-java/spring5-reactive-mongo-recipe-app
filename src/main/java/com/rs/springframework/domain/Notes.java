package com.rs.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * created by rs 2/2/2022.
 */
@Getter
@Setter
public class Notes {

    @Id
    private String id;
    private String recipeNotes;

}
