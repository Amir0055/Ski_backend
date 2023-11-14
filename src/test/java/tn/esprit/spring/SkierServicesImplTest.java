package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkierServicesImplTest {

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;

    @Test
    void retrieveAllSkiers() {
        // Arrange
        List<Skier> skiers = Arrays.asList(new Skier(), new Skier());
        when(skierRepository.findAll()).thenReturn(skiers);

        // Act
        List<Skier> result = skierServices.retrieveAllSkiers();

        // Assert
        assertEquals(2, result.size());
        verify(skierRepository, times(1)).findAll();
    }

    @Test
    void addSkier() {
        // Arrange
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        skier.setSubscription(subscription);

        // Act
        skierServices.addSkier(skier);

        // Assert
        assertNotNull(skier.getSubscription().getEndDate());
        verify(skierRepository, times(1)).save(any(Skier.class));
    }

    @Test
    void assignSkierToSubscription() {
        // Arrange
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(anyLong())).thenReturn(Optional.of(subscription));

        // Act
        skierServices.assignSkierToSubscription(1L, 2L);

        // Assert
        assertEquals(subscription, skier.getSubscription());
        verify(skierRepository, times(1)).save(any(Skier.class));
    }

    // Add more test methods for other functions

    private Long anyLong() {
        return any(Long.class);
    }
}

