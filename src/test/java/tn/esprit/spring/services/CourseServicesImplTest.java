package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.controllers.CourseRestController;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CourseServicesImplTest {

    @Autowired
    CourseRestController courseRestController;
@Autowired
    CourseServicesImpl courseServices;
@Autowired
    ICourseRepository courseRepository;


    @Test
    void retrieveAllCourses() {
        List<Course> courseList = courseRestController.getAllCourses();
        assertThat(courseList).isNotNull();
        assertThat(courseList).isNotEmpty();

    }

    @Test
    void addCourse() {

                Course course = new Course().builder().price(4554F).build();



        Course result = courseServices.addCourse(course);

        assertThat(result).isEqualTo(course);
        assertThat(result).isNotNull();
    }


    @Test
    void updateCourse() {
        Course course = new Course().builder().price(21564869F).build();
        Course result = courseServices.updateCourse(course);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(course);
    }

    @Test
    void retrieveCourse() {
        Course course = new Course().builder().price(6545647F).build();
        course.setNumCourse(1L);
        Course result = courseServices.retrieveCourse(1L);
        assertThat(result.getNumCourse()).isEqualTo(1L);
        assertThat(result).isNotNull();

    }
}
