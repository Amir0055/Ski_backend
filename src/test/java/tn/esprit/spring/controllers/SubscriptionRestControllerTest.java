package tn.esprit.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISubscriptionServices;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = SubscriptionRestController.class)
class SubscriptionRestControllerTest {
    @MockBean
    private ISubscriptionServices subscriptionServices;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;  // ObjectMapper is needed to convert Java objects to JSON
    @Test
    @DisplayName("Should i add Subsecribtion")
    void addSubscription() throws Exception {
        // Arrange
        Subscription newSubscription =  new Subscription(142L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);
        Subscription savedSubscription = new Subscription(142L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);

        when(subscriptionServices.addSubscription(any(Subscription.class))).thenReturn(savedSubscription);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/subscription/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSubscription)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeSub").value("ANNUAL"));

        // Optionally, you can verify that the service method was called with the correct argument
        verify(subscriptionServices, times(1)).addSubscription(any(Subscription.class));

    }

    @Test
    @DisplayName("Should i get Subsecribtion")
    void getById() throws Exception {
        Long subscriptionId = 1L;
        Subscription mockSubscription = new Subscription(1L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);
        when(subscriptionServices.retrieveSubscriptionById(subscriptionId)).thenReturn(mockSubscription);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/subscription/get/{id-subscription}", subscriptionId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSub", Matchers.equalTo(1L)));
        // Optionally, you can verify that the service method was called with the correct argument
        verify(subscriptionServices, times(1)).retrieveSubscriptionById(subscriptionId);


    }

    @Test
    @DisplayName("Should i get Subsecribtion by type")
    void getSubscriptionsByType() {
    }

    @Test
    @DisplayName("Should i update Subsecribtion")
    void updateSubscription() throws Exception {
        // Arrange
        Subscription subscriptionToUpdate =  new Subscription(145L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);
        Subscription updatedSubscription = new Subscription(145L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);

        when(subscriptionServices.updateSubscription(any(Subscription.class))).thenReturn(updatedSubscription);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/subscription/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionToUpdate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSub").value(145L));

        // Verify that the service method was called with the correct argument
        verify(subscriptionServices).updateSubscription(any(Subscription.class));
    }

    @Test
    @DisplayName("Should i get Subsecribtion by date this  api : api/subscription/all/{{date1}}/{{date2}}")
    void getSubscriptionsByDates() throws Exception {
        Subscription subscription1 = new Subscription(142L,
                LocalDate.of(1999, 5, 6),
                LocalDate.of(2023, Month.SEPTEMBER, 5),
                (float) 15.47, TypeSubscription.ANNUAL);

        Subscription subscription2 = new Subscription(144L,
                LocalDate.of(2003, 9, 6),
                LocalDate.of(2023, Month.JANUARY, 10),
                (float) 10.0, TypeSubscription.ANNUAL);

        Subscription subscription3 = new Subscription(146L,
                LocalDate.of(2023, 10, 20),
                LocalDate.of(2023, Month.NOVEMBER, 23),
                (float) 15.47, TypeSubscription.ANNUAL);

        List<Subscription> expectedResultList = Arrays.asList(subscription1, subscription2, subscription3);

        // given
        LocalDate startDate = LocalDate.of(1990, 5, 6);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(subscriptionServices.retrieveSubscriptionsByDates(startDate, endDate))
                .thenReturn(expectedResultList);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/subscription/all/{date1}/{date2}", startDate, endDate));
        verify(subscriptionServices, times(1)).retrieveSubscriptionsByDates(startDate,endDate);
    }
}
