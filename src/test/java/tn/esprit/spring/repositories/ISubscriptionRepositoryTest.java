package tn.esprit.spring.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.Locale;

import static java.lang.String.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ISubscriptionRepositoryTest {
    @Autowired
    ISubscriptionRepository underTest;
    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }
    @BeforeEach
    void tearUp(){
        Subscription subscription1= new Subscription(142L,
                LocalDate.of(1999,5,6),
                LocalDate.of(2023, Month.SEPTEMBER,5),
                (float) 15.47,TypeSubscription.ANNUAL);
        Subscription subscription2= new Subscription(144L,
                LocalDate.of(2003,9,6),
                LocalDate.of(2023, Month.JANUARY,10),
                (float) 10.0,TypeSubscription.ANNUAL);
        Subscription subscription3 = new Subscription(146L,
                LocalDate.of(2023,10,20),
                LocalDate.of(2023, Month.NOVEMBER,23),
                (float) 15.47,TypeSubscription.ANNUAL);
        underTest.save(subscription2);
        underTest.save(subscription1);
        underTest.save(subscription3);
    }

    @Test
    @DisplayName("Should Get The List and Chek the Order ASC")
    void findByTypeSubOrderByStartDateAsc() {
        //given
        //then
        Set<Subscription> actualListOfSubscriptionByTypeASC= underTest.findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL);
        //when
        assertAll("actualListOfSubscriptionByTypeASC",
                ()-> assertTrue(actualListOfSubscriptionByTypeASC.size() ==3),
                ()-> assertEquals(actualListOfSubscriptionByTypeASC.stream().findFirst().map(Subscription::getStartDate).orElse(null) , LocalDate.of(1999,5,6))
                );

    }

    //@Query("select distinct s from Subscription s where s.endDate <= CURRENT_TIME order by s.endDate")
   // List<Subscription> findDistinctOrderByEndDateAsc();
    @Test
    @DisplayName("Should Get The List Subscription Not Expired By Order")
    void findDistinctOrderByEndDateAsc(){
        //given

        //then
        List<Subscription> actualListOfSubscriptionByTypeASC= underTest.findDistinctOrderByEndDateAsc();
        //after
        assertAll("actualListOfSubscriptionByTypeASC",
                ()-> assertTrue(actualListOfSubscriptionByTypeASC.size() ==2),
                ()-> assertEquals(actualListOfSubscriptionByTypeASC.stream().findFirst().map(Subscription::getEndDate).orElse(null) , LocalDate.of(2023, Month.JANUARY,10))
        );

    }


    //@Query("select (sum(s.price))/(count(s)) from Subscription s where s.typeSub = ?1")
   // Float recurringRevenueByTypeSubEquals(TypeSubscription typeSub);

    @Test
    @DisplayName("Should Get The REVENU OF ANNUAL")
    void recurringRevenueByTypeSubEquals(){
        //given
        Float expectedResult = (float) ((15.47 + 10.0+15.47 )/ 3);
        //then
        Float actualResult = underTest.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
        //after
        assertThat(actualResult).isCloseTo(expectedResult,  offset(0.001f));
    }
}
