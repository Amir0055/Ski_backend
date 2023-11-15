package tn.esprit.spring.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

@SpringBootTest
public class PisteServicesImplTest {
    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    void testAddPiste() {
        // Given
        Piste newPiste = new Piste();
        when(pisteRepository.save(newPiste)).thenReturn(newPiste);

        // When
        Piste addedPiste = pisteServices.addPiste(newPiste);

        // Then
        assertNotNull(addedPiste);
        assertEquals(newPiste, addedPiste);

        // Verify
        verify(pisteRepository, times(1)).save(any(Piste.class));
    }

    @Test
    void testRetrievePiste() {
        // Given
        Long pisteId = 1L;
        Piste retrievedPiste = new Piste();
        when(pisteRepository.findById(pisteId)).thenReturn(java.util.Optional.of(retrievedPiste));

        // When
        Piste foundPiste = pisteServices.retrievePiste(pisteId);

        // Then
        assertNotNull(foundPiste);
        assertEquals(retrievedPiste, foundPiste);

        // Verify
        verify(pisteRepository, times(1)).findById(pisteId);
    }

    @Test
    void testRetrievePistePisteNotFound() {
        // Given
        Long pisteId = 1L;
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> pisteServices.retrievePiste(pisteId));

        // Verify
        verify(pisteRepository, times(1)).findById(pisteId);
    }

    @Test
    void testRetrieveAllPistes() {
        // Given
        List<Piste> pisteList = new ArrayList<>();
        when(pisteRepository.findAll()).thenReturn(pisteList);

        // When
        List<Piste> retrievedPistes = pisteServices.retrieveAllPistes();

        // Then
        assertNotNull(retrievedPistes);
        assertEquals(pisteList, retrievedPistes);

        // Verify
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testRemovePiste() {
        // Given
        Long pisteId = 1L;

        // When
        pisteServices.removePiste(pisteId);

        // Verify
        verify(pisteRepository, times(1)).deleteById(pisteId);
    }
}
