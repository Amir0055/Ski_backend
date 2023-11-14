package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;


import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServicesImplTest {
    @Mock
    private ISubscriptionRepository subscriptionRepository;
    @Mock
    private ISkierRepository skierRepository;

    private SubscriptionServicesImpl underTest;

    @BeforeEach
    public void setup() {
        underTest=new SubscriptionServicesImpl(subscriptionRepository,skierRepository);
    }


    @Test
    @DisplayName("Should add The Subsecription ")
    void addSubscription() {
        //given
        Subscription subscription= new Subscription(142L,
                LocalDate.of(1999,5,6),
                LocalDate.of(2023, Month.SEPTEMBER,5),
                (float) 15.47, TypeSubscription.ANNUAL);
        //then
        underTest.addSubscription(subscription);
        //when
        ArgumentCaptor<Subscription> subscriptionArgumentCaptor =
                ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository)
                .save(subscriptionArgumentCaptor.capture());
        Subscription subscriptionCapture= subscriptionArgumentCaptor.getValue();
        assertThat(subscriptionCapture).isEqualTo(subscription);
    }
    @Test
    @DisplayName("Assert that the end date is correctly calculated based on the subscription type ANNUAL")
    void VerifyTypeANNUALSubscriptionCalcul() {
        // ANNUAL subscription
        Subscription annualSubscription = new Subscription();
        annualSubscription.setTypeSub(TypeSubscription.ANNUAL);
        annualSubscription.setStartDate(LocalDate.now());

        when(subscriptionRepository.save(annualSubscription)).thenReturn(annualSubscription);
        Subscription annualResult = underTest.addSubscription(annualSubscription);

        assertEquals(LocalDate.now().plusYears(1), annualResult.getEndDate());
    }

    @Test
    @DisplayName("Assert that the end date is correctly calculated based on the subscription type MONTHLY")
    void VerifyTypeMONTHLYSubscriptionCalcul() {
        Subscription monthlySubscription = new Subscription();
        monthlySubscription.setTypeSub(TypeSubscription.MONTHLY);
        monthlySubscription.setStartDate(LocalDate.now());

        when(subscriptionRepository.save(monthlySubscription)).thenReturn(monthlySubscription);
        Subscription monthlyResult = underTest.addSubscription(monthlySubscription);

        assertEquals(LocalDate.now().plusMonths(1), monthlyResult.getEndDate());
    }
    @Test
    @DisplayName("Assert that the end date is correctly calculated based on the subscription type SEMESTRIEL")
    void VerifyTypeSEMESTRIELSubscriptionCalcul() {
        Subscription semestrielSubscription = new Subscription();
        semestrielSubscription.setTypeSub(TypeSubscription.SEMESTRIEL);
        semestrielSubscription.setStartDate(LocalDate.now());

        when(subscriptionRepository.save(semestrielSubscription)).thenReturn(semestrielSubscription);
        Subscription semestrielResult = underTest.addSubscription(semestrielSubscription);

        assertEquals(LocalDate.now().plusMonths(6), semestrielResult.getEndDate());
    }
    @Test
    @DisplayName("Assert that the Value passe par Service it's the same passe to Repository")
    void updateSubscription() {
        Subscription subscription= new Subscription(142L,
                LocalDate.of(1999,5,6),
                LocalDate.of(2023, Month.SEPTEMBER,5),
                (float) 15.47, TypeSubscription.ANNUAL);
        //then
        underTest.updateSubscription(subscription);
        //when
        ArgumentCaptor<Subscription> subscriptionArgumentCaptor =
                ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository)
                .save(subscriptionArgumentCaptor.capture());
        Subscription subscriptionCapture= subscriptionArgumentCaptor.getValue();
        assertThat(subscriptionCapture).isEqualTo(subscription);
    }

    @Test
    @DisplayName("Assert that the Value passe par Service it's the same passe to Repository")
    void retrieveSubscriptionById() {
        Subscription subscription= new Subscription(142L,
                LocalDate.of(1999,5,6),
                LocalDate.of(2023, Month.SEPTEMBER,5),
                (float) 15.47, TypeSubscription.ANNUAL);
        ArgumentCaptor<Long> subscriptionIdCaptor = ArgumentCaptor.forClass(Long.class);

        when(subscriptionRepository.findById(subscriptionIdCaptor.capture())).thenReturn(Optional.of(subscription));
        //then
        underTest.retrieveSubscriptionById(subscription.getNumSub());
        //when

        // Verify that the repository's findById method was called with the correct argument
        verify(subscriptionRepository).findById(subscription.getNumSub());

        // Assert that the captured subscription ID is equal to the expected ID
        assertEquals(subscription.getNumSub(), subscriptionIdCaptor.getValue());
    }
    @Test
    @DisplayName("In case Not Found Subscription By id ")
     void testRetrieveSubscriptionByIdNotFound() {
        //given
        Long subscriptionId = 2L;
        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());
        //then
        Subscription retrievedSubscription  = underTest.retrieveSubscriptionById(subscriptionId);
        //when
        assertNull(retrievedSubscription);

    }

    @Test
    @DisplayName("Verefy only if findByTypeSubOrderByStartDateAsc was INVOKED and still retive Result")
    void getSubscriptionByType() {
        //given
        TypeSubscription typeExpected = TypeSubscription.ANNUAL;
        //when
        underTest.getSubscriptionByType(typeExpected);
        // then
        ArgumentCaptor<TypeSubscription> subscriptionTypeCaptor = ArgumentCaptor.forClass(TypeSubscription.class);
        // Verify that the repository's findByTypeSubOrderByStartDateAsc method and at the same time we Caputre Value
        verify(subscriptionRepository).findByTypeSubOrderByStartDateAsc(subscriptionTypeCaptor.capture());
        TypeSubscription typeSubscriptionCaptured = subscriptionTypeCaptor.getValue();
        // Assert that the captured subscription Type is equal to the expected Type
        assertEquals( typeExpected,typeSubscriptionCaptured);
    }

    @Test
    @DisplayName("Verefy only if getSubscriptionsByStartDateBetween was INVOKED")
    void retrieveSubscriptionsByDates() {
        //given
        LocalDate startDateExpected =     LocalDate.of(2010,5,6);
        LocalDate endDateExpected =     LocalDate.of(2023,11,6);
        //when
        underTest.retrieveSubscriptionsByDates(startDateExpected,endDateExpected);
        // then
        ArgumentCaptor<LocalDate> subscriptionStartDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> subscriptionEndDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        // Verify that the repository's findByTypeSubOrderByStartDateAsc method and at the same time we Caputre Value
        verify(subscriptionRepository).getSubscriptionsByStartDateBetween(subscriptionStartDateCaptor.capture(),subscriptionEndDateCaptor.capture());
        LocalDate startDateSubscriptionCaptured = subscriptionStartDateCaptor.getValue();
        LocalDate endDateSubscriptionCaptured = subscriptionEndDateCaptor.getValue();
        // Assert that the captured subscription Type is equal to the expected Type
        assertEquals( startDateExpected,startDateSubscriptionCaptured);
        assertEquals( endDateExpected,endDateSubscriptionCaptured);
    }

    @Test
    void retrieveSubscriptions() {
        Subscription subscription1= new Subscription(142L,
                LocalDate.of(1999,5,6),
                LocalDate.of(2023, Month.SEPTEMBER,5),
                (float) 15.47, TypeSubscription.ANNUAL);
        List<Subscription> subscriptionList = Arrays.asList(subscription1);
        Skier skier1 = new Skier( 15L,
                        "firstName",
                       "lastName",
                                LocalDate.of(2003,5,14),
                            "city",
                                 subscription1,
                           null,
                       null);
        Mockito.when(subscriptionRepository.findDistinctOrderByEndDateAsc()).thenReturn(subscriptionList);
        Mockito.when(skierRepository.findBySubscription(subscription1)).thenReturn(skier1);
        //when
        underTest.retrieveSubscriptions();
        //then
        verify(subscriptionRepository, times(1)).findDistinctOrderByEndDateAsc();
        verify(skierRepository, times(1)).findBySubscription(subscription1);

        //verify(log).info(Mockito.eq(subscription1.getNumSub() + " | " + subscription1.getEndDate() + " | " + skier1.getFirstName() + " " + skier1.getLastName()));
    }

    @Test
    void showMonthlyRecurringRevenue() {
        //given
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(100.0f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(200.0f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(300.0f);

        // when
        underTest.showMonthlyRecurringRevenue();

        // then
        // Assert
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
        // Assert log message

    }
}
