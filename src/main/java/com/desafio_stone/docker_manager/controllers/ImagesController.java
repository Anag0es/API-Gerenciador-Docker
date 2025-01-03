package com.desafio_stone.docker_manager.controllers;

import com.desafio_stone.docker_manager.services.DockerService;
import com.github.dockerjava.api.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    @Autowired
    private DockerService imagesService;

    @GetMapping("")
    public List<Image> listImages() {
        return imagesService.listImages();
    }

    @GetMapping("/filter")
    public List<Image> filterImages(@RequestParam(required = false, defaultValue = "image-") String imageName) {
        return imagesService.filterImages(imageName);
    }
}
