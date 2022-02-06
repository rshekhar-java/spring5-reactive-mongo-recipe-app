package com.rs.springframework.services;

import com.rs.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * created by rs 1/13/2022.
 */
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
