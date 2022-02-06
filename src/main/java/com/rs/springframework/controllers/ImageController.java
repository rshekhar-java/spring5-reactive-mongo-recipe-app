package com.rs.springframework.controllers;

import com.rs.springframework.commands.RecipeCommand;
import com.rs.springframework.services.ImageService;
import com.rs.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * created by rs 1/15/2022.
 */
@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){

        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id,@RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(id,file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        if(recipeCommand.getImage()!= null){
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i =0;

            for (Byte wrapperByte : recipeCommand.getImage()){
                byteArray[i++] = wrapperByte;//auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is =  new ByteArrayInputStream(byteArray);
            IOUtils.copy(is,response.getOutputStream());
        }
    }
}
