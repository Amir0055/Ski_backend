package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;

public class SubscriptionServicesImplTest {
    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldAddSubscriptionWithAnnualTypeAndSetEndDate() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Stubbing the save method of the repository
        when(subscriptionRepository.save(any())).thenAnswer(invocation -> {
            Subscription savedSubscription = invocation.getArgument(0);
            // Simulate the behavior of the repository save method
            savedSubscription.setEndDate(savedSubscription.getStartDate().plusYears(1));
            return savedSubscription;
        });

        // Act
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Assert
        assertNotNull(String.valueOf(result), "The result of addSubscription should not be null");
        assertEquals(TypeSubscription.ANNUAL, result.getTypeSub(), "The subscription type should be ANNUAL");
        // Add more specific assertions for other attributes if needed

        // Verify that the save method of the repository was called once with the correct subscription
        verify(subscriptionRepository, times(1)).save(subscription);
    }


    @Test
    void testRetrieveSubscriptionsByDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        List<Subscription> subscriptions = Arrays.asList(
                new Subscription(), new Subscription(), new Subscription()
        );

        when(subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate))
                .thenReturn(subscriptions);

        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate);

        assertEquals(subscriptions, result);
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(startDate, endDate);
    }

}
