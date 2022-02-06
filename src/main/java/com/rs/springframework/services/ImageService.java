package com.rs.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * created by rs 1/15/2022.
 */
public interface ImageService {
    void saveImageFile(String recipeId, MultipartFile file);
}
