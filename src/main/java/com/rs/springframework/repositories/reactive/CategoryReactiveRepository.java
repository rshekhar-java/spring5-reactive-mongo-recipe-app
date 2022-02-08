package com.rs.springframework.repositories.reactive;

import com.rs.springframework.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * created by rs 2/7/2022.
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category,String> {

    Mono<Category> findByDescription(String description);
}
