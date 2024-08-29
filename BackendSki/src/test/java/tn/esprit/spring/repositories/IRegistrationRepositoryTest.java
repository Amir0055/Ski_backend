package tn.esprit.spring.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import tn.esprit.spring.entities.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class IRegistrationRepositoryTest {

    @Autowired
    IRegistrationRepository underTest;
    @Autowired
    ISkierRepository skierRepository;
    @Autowired
    ICourseRepository courseRepository;
    @Autowired
    IInstructorRepository instructorRepository;
    @AfterEach
    void tearDown() {cleanupTestData();}

    private void cleanupTestData() {
        underTest.deleteAll(); // Delete all registrations
        skierRepository.deleteAll(); // Delete all skiers
        courseRepository.deleteAll(); // Delete all courses
    }
    @BeforeEach
    void tearUp(){
        Instructor instructor = new Instructor(
                10L,
                "amir",
                "Zaaforui",
                LocalDate.of(2023,10,5),
                null
        );

        Course course = new Course();
        course.setNumCourse(5L);
        course.setSupport(Support.SNOWBOARD);
        // Set course properties
        courseRepository.save(course);

        Skier skier = new Skier();
        skier.setNumSkier(140L);
        // Set skier properties
        skierRepository.save(skier);
        Registration registration = new Registration();
        registration.setNumRegistration(20L);
        registration.setSkier(skier);
        registration.setCourse(course);
        underTest.save(registration);
        //------Table Relationaire Affect---------
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);
        instructorRepository.save(instructor);
    }
    @Test
    @Disabled
    @DisplayName("Should Get List OF Nbr of Course by numéro Instructor et Support")
    void numWeeksCourseOfInstructorBySupport() {
        Long instructorId = 10L; // Modify this ID based on your test data
        Support support = Support.SNOWBOARD; // Modify based on your test data

        List<Integer> weeks = underTest.numWeeksCourseOfInstructorBySupport(instructorId, support);
        System.out.println("Weeks : "+weeks);

    }

    @Test
    @Disabled
    void countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse() {
    }
}
