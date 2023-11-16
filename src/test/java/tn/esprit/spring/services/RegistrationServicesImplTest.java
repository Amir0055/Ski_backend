package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RegistrationServicesImplTest {
    @Mock
    private IRegistrationRepository registrationRepository;
    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Test
    void addRegistrationAndAssignToSkier() {
     /*   // Create a sample Registration object
        Registration registration = new Registration();
        Long numSkier = 1L; // Replace with a valid skier ID

        // Mock the behavior of skierRepository.findById
        Skier skier = new Skier(); // Replace with a valid Skier object
        Mockito.when(skierRepository.findById(numSkier)).thenReturn(java.util.Optional.ofNullable(skier));

        // Mock the behavior of registrationRepository.save
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        // Call the method to be tested
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, numSkier);

        // Assertions
        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        // Add more assertions as needed
*/

    }

    @Test
    void assignRegistrationToCourse() {

        // Create sample data
        Long numRegistration = 1L; // Replace with a valid registration ID
        Long numCourse = 2L; // Replace with a valid course ID

        // Mock the behavior of registrationRepository.findById
        Registration registration = new Registration(); // Replace with a valid Registration object
        Mockito.when(registrationRepository.findById(numRegistration)).thenReturn(Optional.ofNullable(registration));

        // Mock the behavior of courseRepository.findById
        Course course = new Course(); // Replace with a valid Course object
        Mockito.when(courseRepository.findById(numCourse)).thenReturn(Optional.ofNullable(course));

        // Mock the behavior of registrationRepository.save
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        // Call the method to be tested
        Registration result = registrationServices.assignRegistrationToCourse(numRegistration, numCourse);

        // Assertions
        assertNotNull(result);
        assertEquals(course, result.getCourse());
        // Add more assertions as needed

    }

    @Test
    void addRegistrationAndAssignToSkierAndCourse() {
      // Create sample data
        Registration registration = new Registration();
        Long numSkieur = 1L; // Replace with a valid skier ID
        Long numCours = 1L; // Replace with a valid course ID

        // Mock the behavior of skierRepository.findById
        Skier skier = new Skier();
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1)); // Set a valid date of birth
        Mockito.when(skierRepository.findById(numSkieur)).thenReturn(java.util.Optional.ofNullable(skier));

        // Mock the behavior of courseRepository.findById
        Course course = new Course(); // Replace with a valid Course object
        Mockito.when(courseRepository.findById(numCours)).thenReturn(java.util.Optional.ofNullable(course));

        // Mock the behavior of registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse
        Mockito.when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(Mockito.anyInt(), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(5L); // Assuming no existing registration for the given week, skier, and course

        // Mock the behavior of registrationRepository.countByCourseAndNumWeek
        Mockito.when(registrationRepository.countByCourseAndNumWeek(Mockito.any(Course.class), Mockito.anyInt()))
                .thenReturn(5L); // Assuming no existing registrations for the given course and week

        // Call the method to be tested
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours);

        // Assertions
        assertNotNull(result);
        // Add more assertions based on the expected behavior of your method

        // Verify that the necessary methods were called
        Mockito.verify(skierRepository, Mockito.times(1)).findById(numSkieur);
        Mockito.verify(courseRepository, Mockito.times(1)).findById(numCours);
        Mockito.verify(registrationRepository, Mockito.times(1)).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(Mockito.anyInt(), Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(registrationRepository, Mockito.times(1)).countByCourseAndNumWeek(Mockito.any(Course.class), Mockito.anyInt());

    }



    @Test
    void numWeeksCourseOfInstructorBySupport() {
        // Create sample data
        Long numInstructor = 1L; // Replace with a valid instructor ID
        Support support = Support.SKI; // Replace with a valid Support enum value

        // Mock the behavior of registrationRepository.numWeeksCourseOfInstructorBySupport
        List<Integer> expectedResult = Arrays.asList(1, 2, 3); // Replace with expected result
        Mockito.when(registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support)).thenReturn(expectedResult);

        // Call the method to be tested
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedResult, result);
        // Add more assertions as needed
    }
}
