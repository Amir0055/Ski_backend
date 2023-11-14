package tn.esprit.spring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SkierServicesImpl;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Skier skier1 = new Skier();
        Skier skier2 = new Skier();
        skierRepository.save(skier1);
        skierRepository.save(skier2);

        // Act
        List<Skier> result = skierServices.retrieveAllSkiers();

        // Assert
        assertEquals(2, result.size());
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
    }

    @Test
    void assignSkierToSubscription() {
        // Arrange
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        skierRepository.save(skier);
        subscriptionRepository.save(subscription);

        // Act
        skierServices.assignSkierToSubscription(skier.getNumSkier(), subscription.getNumSub());

        // Assert
        assertEquals(subscription, skierRepository.findById(skier.getNumSkier()).get().getSubscription());
    }

}
