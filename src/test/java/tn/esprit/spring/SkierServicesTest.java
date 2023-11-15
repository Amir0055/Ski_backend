package tn.esprit.spring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.SkierServicesImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest

public class SkierServicesTest {
    @Autowired
    private ISkierRepository skierRepository;

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    @Autowired
    private SkierServicesImpl skierServices;

    @Test
    void retrieveAllSkiers() {
        // Arrange
        ISkierRepository skierRepository = mock(ISkierRepository.class);
        SkierServicesImpl skierServices = new SkierServicesImpl(skierRepository, null, null, null, null);

        Skier skier1 = new Skier();
        Skier skier2 = new Skier();
        when(skierRepository.findAll()).thenReturn(Arrays.asList(skier1, skier2));

        // Act
        List<Skier> skiers = skierServices.retrieveAllSkiers();

        // Assert
        assertNotNull(skiers);
        assertEquals(2, skiers.size());
    }

    @Test
    void addSkier_withAnnualSubscription_shouldSetEndDateCorrectly() {
        // Arrange
        ISkierRepository skierRepository = mock(ISkierRepository.class);
        IPisteRepository pisteRepository = mock(IPisteRepository.class);
        ICourseRepository courseRepository = mock(ICourseRepository.class);
        IRegistrationRepository registrationRepository = mock(IRegistrationRepository.class);
        ISubscriptionRepository subscriptionRepository = mock(ISubscriptionRepository.class);

        SkierServicesImpl skierServices = new SkierServicesImpl(
                skierRepository, pisteRepository, courseRepository, registrationRepository, subscriptionRepository
        );

        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.of(2023, 1, 1));
        skier.setSubscription(subscription);

        // Mock the behavior of skierRepository.save()
        when(skierRepository.save(skier)).thenReturn(skier);

        // Act
        Skier result = skierServices.addSkier(skier);

        // Assert
        assertEquals(TypeSubscription.ANNUAL, result.getSubscription().getTypeSub());
        assertEquals(subscription.getStartDate().plusYears(1), result.getSubscription().getEndDate());
    }

    @Test
    void assignSkierToSubscription_shouldAssignSubscriptionCorrectly() {
        // Arrange
        ISkierRepository skierRepository = mock(ISkierRepository.class);
        ISubscriptionRepository subscriptionRepository = mock(ISubscriptionRepository.class);

        SkierServicesImpl skierServices = new SkierServicesImpl(
                skierRepository, null, null, null, subscriptionRepository
        );

        Long numSkier = 1L;
        Long numSubscription = 2L;

        Skier existingSkier = new Skier();
        Subscription subscription = new Subscription();
        subscription.setNumSub(numSubscription);

        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(existingSkier));
        when(subscriptionRepository.findById(numSubscription)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(existingSkier)).thenReturn(existingSkier);

        // Act
        Skier result = skierServices.assignSkierToSubscription(numSkier, numSubscription);

        // Assert
        assertEquals(subscription, result.getSubscription());
    }

}
