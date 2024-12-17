package com.example.demo;

import com.example.demo.api.model.Course;
import com.example.demo.api.repository.CourseRepository;
import com.example.demo.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

   /* @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course(1, "Mathematics", "Math Course", "Dr. Smith", 30); // Örnek Course nesnesi
    }*/

    @Test
    void testGetAllCourses() {
        // Setup mock
        when(courseRepository.findAll()).thenReturn(List.of(course));

        // Test
        assertFalse(courseService.getAllCourses().isEmpty());
        assertEquals(1, courseService.getAllCourses().size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        // Setup mock
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        // Test
        Optional<Course> foundCourse = courseService.getCourseById(1);
        assertTrue(foundCourse.isPresent());
        assertEquals(course.getName(), foundCourse.get().getName());
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    void testAddCourse() {
        // Setup mock
        when(courseRepository.save(course)).thenReturn(course);

        // Test
        Course savedCourse = courseService.addCourse(course);
        assertNotNull(savedCourse);
        assertEquals(course.getName(), savedCourse.getName());
        verify(courseRepository, times(1)).save(course);
    }

   /* @Test
    void testUpdateCourse() {
        // Setup mock
        Course updatedCourse = new Course(1, "Updated Math", "Updated Description", "Dr. Johnson", 40);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        // Test
        Course result = courseService.updateCourse(1, updatedCourse);
        assertEquals(updatedCourse.getName(), result.getName());
        assertEquals(updatedCourse.getDescription(), result.getDescription());
        verify(courseRepository, times(1)).findById(1);
        verify(courseRepository, times(1)).save(any(Course.class));
    }*/

    @Test
    void testDeleteCourse() {
        // Setup mock
        doNothing().when(courseRepository).deleteById(1);

        // Test
        boolean result = courseService.deleteCourse(1);
        assertFalse(result); // Eğer silme başarılıysa false döndürüyor
        verify(courseRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateCourseNotFound() {
        // Setup mock
    //    Course updatedCourse = new Course(1, "Updated Math", "Updated Description", "Dr. Johnson", 40);
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        // Test
     //   assertThrows(RuntimeException.class, () -> courseService.updateCourse(1, updatedCourse));
    }
}
