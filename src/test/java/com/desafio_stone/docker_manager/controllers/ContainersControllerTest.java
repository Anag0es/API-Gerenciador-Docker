package com.desafio_stone.docker_manager.controllers;

import com.desafio_stone.docker_manager.services.DockerService;
import com.github.dockerjava.api.model.Container;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContainersControllerTest {

    @Mock
    private DockerService dockerService;

    @InjectMocks
    private ContainersController containersController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(containersController).build();
    }

    @Test
    @DisplayName("Deve listar containers com sucesso como true")
    void listContainerTrue() throws Exception {
        List<Container> mockContainersList = Collections.emptyList();
        when(dockerService.listContainers(true)).thenReturn(mockContainersList);

        mockMvc.perform(get("/api/containers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(dockerService).listContainers(true);
        verify(dockerService, times(1)).listContainers(true);
    }

    @Test
    @DisplayName("Deve listar containers com sucesso como false")
    void listContainerFalse() throws Exception {
        List<Container> mockContainersList = Collections.emptyList();
        when(dockerService.listContainers(false)).thenReturn(mockContainersList);

        mockMvc.perform(get("/api/containers").param("showAll", "false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(dockerService).listContainers(false);
        verify(dockerService, times(1)).listContainers(false);
    }
}