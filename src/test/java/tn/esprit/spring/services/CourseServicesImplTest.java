package tn.esprit.spring.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CourseServicesImplTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveAllCourses() {
        // Mocking data
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Test the service method
        List<Course> result = courseServices.retrieveAllCourses();

        // Verify that the repository method was called
        verify(courseRepository, times(1)).findAll();

        // Assertions
        assertEquals(2, result.size());
    }

    @Test
    void addCourse() {
        // Mocking data
        Course courseToAdd = new Course();
        when(courseRepository.save(courseToAdd)).thenReturn(courseToAdd);

        // Test the service method
        Course result = courseServices.addCourse(courseToAdd);

        // Verify that the repository method was called
        verify(courseRepository, times(1)).save(courseToAdd);

        // Assertions
        assertEquals(courseToAdd, result);
    }

    @Test
    void updateCourse() {
        // Mocking data
        Course courseToUpdate = new Course();
        when(courseRepository.save(courseToUpdate)).thenReturn(courseToUpdate);

        // Test the service method
        Course result = courseServices.updateCourse(courseToUpdate);

        // Verify that the repository method was called
        verify(courseRepository, times(1)).save(courseToUpdate);

        // Assertions
        assertEquals(courseToUpdate, result);
    }

    @Test
    void retrieveCourse() {
        // Mocking data
        Long courseId = 1L;
        Course courseToRetrieve = new Course();
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(courseToRetrieve));

        // Test the service method
        Course result = courseServices.retrieveCourse(courseId);

        // Verify that the repository method was called
        verify(courseRepository, times(1)).findById(courseId);

        // Assertions
        assertEquals(courseToRetrieve, result);
    }

    // Add similar tests for other methods...
}
