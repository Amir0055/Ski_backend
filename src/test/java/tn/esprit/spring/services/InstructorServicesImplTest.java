package tn.esprit.spring.services;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Test
    void testAddInstructor() {


        Instructor instructor=new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);


        Instructor addedInstructor = instructorServices.addInstructor(instructor);


        assertNotNull(addedInstructor);
        assertEquals(instructor, addedInstructor);


        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }



    @Test
    void testRetrieveInstructor() {

        Long instructorId = 1L;
        Instructor retrievedInstructor = new Instructor();
        when(instructorRepository.findById(instructorId)).thenReturn(java.util.Optional.of(retrievedInstructor));


        Instructor foundInstructor = instructorServices.retrieveInstructor(instructorId);


        assertNotNull(foundInstructor);
        assertEquals(retrievedInstructor,foundInstructor);


        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void testRetrieveAllInstructors() {

        List<Instructor> instructorList = new ArrayList<>();
        when(instructorRepository.findAll()).thenReturn(instructorList);


        List<Instructor> retrievedInstructors = instructorServices.retrieveAllInstructors();


        assertNotNull(retrievedInstructors);
        assertEquals(instructorList, retrievedInstructors);


        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testRemoveInstructor() {

        Long instructorId = 1L;



        instructorServices.removeInstructor(instructorId);


        verify(instructorRepository, times(1)).deleteById(instructorId);
    }

}