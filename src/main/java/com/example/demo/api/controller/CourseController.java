package com.example.demo.api.controller;

import com.example.demo.api.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    // Tüm dersleri listeleme
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Boş liste durumunu ele alır
        }
        return ResponseEntity.ok(courses);
    }
    // ID ile ders getirme
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Yeni ders ekleme
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course addedCourse = courseService.addCourse(course);
        return ResponseEntity.status(201).body(addedCourse); // 201 CREATED durum kodu ile döner
    }
    // Ders silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        if (courseService.deleteCourse(id)) {
            return ResponseEntity.noContent().build(); // Başarılı silme işlemi
        } else {
            return ResponseEntity.notFound().build(); // Ders bulunamazsa
        }
    }
}
