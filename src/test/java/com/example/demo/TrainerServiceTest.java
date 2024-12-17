package com.example.demo;

import com.example.demo.api.model.Trainer;
import com.example.demo.api.repository.TrainerRepository;
import com.example.demo.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;

  /*  @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer(1L, "John Doe", "Fitness", 5); // Örnek Trainer nesnesi
    }*/

    @Test
    void testGetAllTrainers() {
        // Setup mock
        when(trainerRepository.findAll()).thenReturn(List.of(trainer));

        // Test
        List<Trainer> trainers = trainerService.getAllTrainers();
        assertFalse(trainers.isEmpty());
        assertEquals(1, trainers.size());
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTrainer() {
        // Setup mock
        doNothing().when(trainerRepository).deleteById(1L);

        // Test
        trainerService.deleteTrainer(1L);
        verify(trainerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTrainerNotFound() {
        // Setup mock
        doThrow(new RuntimeException("Trainer not found")).when(trainerRepository).deleteById(1L);

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> trainerService.deleteTrainer(1L));
        assertEquals("Trainer not found", exception.getMessage());
        verify(trainerRepository, times(1)).deleteById(1L);
    }
}
