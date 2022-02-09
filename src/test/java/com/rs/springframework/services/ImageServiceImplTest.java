package com.rs.springframework.services;

import com.rs.springframework.domain.Recipe;
import com.rs.springframework.repositories.RecipeRepository;
import com.rs.springframework.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * created by rs 1/15/2022.
 */
public class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeReactiveRepository);
    }

    @Test
    public void saveImageFile() throws Exception{
        //given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt","text/plain",
                "COM RS SpringFrame".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id,multipartFile);

        //then
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());

        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);
    }
}