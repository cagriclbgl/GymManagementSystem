package com.example.demo.api.controller;

import com.example.demo.api.model.Trainer;
import com.example.demo.api.model.User;
import com.example.demo.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
    // Tüm eğitmenleri listeleme
    @GetMapping("/gettrainers")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        if (trainers.isEmpty()) {
            return ResponseEntity.noContent().build(); // Eğitmen yoksa 204 döner
        }
        return ResponseEntity.ok(trainers);
    }
    // Yeni eğitmen oluşturma
    @PostMapping("/addtrainers")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        try {
            Trainer createdTrainer = trainerService.createTrainer(trainer);
            return ResponseEntity.status(201).body(createdTrainer); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Hata durumunda 500
        }
    }

    // Eğitmeni silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        try {
            trainerService.deleteTrainer(id);
            return ResponseEntity.noContent().build(); // 204 No Content (başarıyla silindi)
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // 500 Internal Server Error (hata durumu)
        }
    }


}
