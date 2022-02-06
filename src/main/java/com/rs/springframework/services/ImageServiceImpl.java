package com.rs.springframework.services;

import com.rs.springframework.domain.Recipe;
import com.rs.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * created by rs 1/15/2022.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(String recipeId, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i =0;
            for (byte b : file.getBytes()){
                byteObjects[i++] =b;
            }

            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);
            log.debug("Received a file in DB");

        } catch (IOException e) {
            //todo handler
            log.error("Error Occured Saving image",e);
            e.printStackTrace();
        }

    }
}
