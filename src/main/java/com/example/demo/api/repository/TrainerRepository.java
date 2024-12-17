package com.example.demo.api.repository;

import com.example.demo.api.model.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//ITrainerRepository
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}
