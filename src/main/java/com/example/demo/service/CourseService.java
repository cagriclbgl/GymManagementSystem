package com.example.demo.service;

import com.example.demo.api.model.Course;
import com.example.demo.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Tüm dersleri listeleme
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // ID'ye göre ders getirme
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    // Yeni ders ekleme
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // Ders güncelleme
    public Course updateCourse(Integer id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            course.setName(updatedCourse.getName());
            course.setDescription(updatedCourse.getDescription());
            course.setInstructor(updatedCourse.getInstructor());
            course.setCapacity(updatedCourse.getCapacity());
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Ders bulunamadı"));
    }

    // Ders silme
    public boolean deleteCourse(Integer id) {
        courseRepository.deleteById(id);
        return false;
    }
}
