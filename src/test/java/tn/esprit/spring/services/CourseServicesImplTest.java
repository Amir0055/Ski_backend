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

        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();

        verify(courseRepository, times(1)).findAll();

        assertEquals(2, result.size());
    }

    @Test
    void addCourse() {
        Course courseToAdd = new Course();
        when(courseRepository.save(courseToAdd)).thenReturn(courseToAdd);

        Course result = courseServices.addCourse(courseToAdd);

        verify(courseRepository, times(1)).save(courseToAdd);

        assertEquals(courseToAdd, result);
    }

    @Test
    void updateCourse() {
        Course courseToUpdate = new Course();
        when(courseRepository.save(courseToUpdate)).thenReturn(courseToUpdate);

        Course result = courseServices.updateCourse(courseToUpdate);

        verify(courseRepository, times(1)).save(courseToUpdate);

        assertEquals(courseToUpdate, result);
    }

    @Test
    void retrieveCourse() {
        Long courseId = 1L;
        Course courseToRetrieve = new Course();
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(courseToRetrieve));

        Course result = courseServices.retrieveCourse(courseId);

        verify(courseRepository, times(1)).findById(courseId);

        assertEquals(courseToRetrieve, result);
    }

}
