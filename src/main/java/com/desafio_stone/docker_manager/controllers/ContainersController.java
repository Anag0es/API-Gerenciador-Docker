package com.desafio_stone.docker_manager.controllers;

import com.desafio_stone.docker_manager.services.DockerService;
import com.github.dockerjava.api.model.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class ContainersController {

    @Autowired
    private DockerService containersService;

    @GetMapping("")
    public List<Container> listContainer(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return containersService.listContainers(showAll);
    }

    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable String id) {
        containersService.startContainer(id);
    }

    @PostMapping("/{id}/stop")
    public void stopContainer(@PathVariable String id) {
        containersService.stopContainer(id);
    }

    @DeleteMapping("/{id}")
    public void deleteContainer(@PathVariable String id) {
        containersService.deleteContainer(id);
    }

    @PostMapping("/{id}/create")
    public void createContainer(@PathVariable String id) {
        containersService.createContainer(id);
    }
}
