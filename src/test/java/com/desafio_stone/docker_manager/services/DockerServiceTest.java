package com.desafio_stone.docker_manager.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Container;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DockerServiceTest {

    @Mock
    private DockerClient dockerClient;

    @Mock
    private ListContainersCmd listContainersCmd;

    @InjectMocks
    private DockerService dockerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Teste se o método consulta os containers quando recebe showAll=true")
    public void testListContainersTrue() {
        // Arrange
        List<Container> mockContainers = Collections.emptyList();

        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(true)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockContainers);

        // Act
        List<Container> result = dockerService.listContainers(true);

        // Assert
        assertEquals(mockContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(true);
        verify(listContainersCmd).exec();
    }

    @Test
    @DisplayName("Teste se o método consulta os containers quando recebe showAll=false")
    public void testListContainersFalse() {
        // Arrange
        List<Container> mockContainers = Collections.emptyList();

        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(false)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockContainers);

        // Act
        List<Container> result = dockerService.listContainers(false);

        // Assert
        assertEquals(mockContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(false);
        verify(listContainersCmd).exec();
    }

    @Test
    @DisplayName("Teste se o inicia um container com sucesso passando um ID")
    public void testStartContainer() {
        // Arrange
        String containerId = UUID.randomUUID().toString();
        when(dockerClient.startContainerCmd(eq(containerId))).thenReturn(dockerClient.startContainerCmd(containerId));

        // Act
        dockerService.startContainer(containerId);

        // Assert
        verify(dockerClient).startContainerCmd(containerId);
        verify(dockerClient.startContainerCmd(containerId)).exec();

    }

    @Test
    @DisplayName("Teste se o inicia um container com erro passando um ID")
    public void testStartContainerFail() {
        // Arrange
        String containerId = UUID.randomUUID().toString();
        when(dockerClient.startContainerCmd(eq(containerId))).thenReturn(dockerClient.startContainerCmd(containerId));
        when(dockerClient.startContainerCmd("1234").exec()).thenThrow(new NotFoundException("Container not found"));


        assertThrows(NotFoundException.class, () -> dockerService.startContainer(containerId));

        // Assert
        verify(dockerClient).startContainerCmd(containerId);
        verify(dockerClient.startContainerCmd(containerId)).exec();
    }
}