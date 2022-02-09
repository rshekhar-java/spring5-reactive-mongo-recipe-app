package com.rs.springframework.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * created by rs 1/15/2022.
 */
public interface ImageService {
    Mono<Void>  saveImageFile(String recipeId, MultipartFile file);
}
