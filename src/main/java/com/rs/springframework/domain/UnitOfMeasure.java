package com.rs.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * created by rs 2/2/2022.
 */
@Getter
@Setter
@Document
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;
}
