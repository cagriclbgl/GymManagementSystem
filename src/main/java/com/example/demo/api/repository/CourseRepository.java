package com.example.demo.api.repository;
import com.example.demo.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ICourseRepository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}