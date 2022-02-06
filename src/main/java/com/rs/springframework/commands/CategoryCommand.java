package com.rs.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * created by rs 1/10/2022.
 */
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;

}
