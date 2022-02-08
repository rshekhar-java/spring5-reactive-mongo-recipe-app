package com.rs.springframework.services;

import com.rs.springframework.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

/**
 * created by rs 1/13/2022.
 */
public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUoms();
}
