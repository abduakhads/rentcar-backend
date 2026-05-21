package com.example.controllers;

import com.example.rentcar.RentCar;
import com.example.models.Location;
import com.example.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = RentCar.class)
@AutoConfigureMockMvc
@Transactional
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testGetAllLocationsPublicly() throws Exception {
        Location loc1 = new Location();
        loc1.setCode("T1");
        loc1.setName("Test Location 1");
        locationRepository.save(loc1);

        Location loc2 = new Location();
        loc2.setCode("T2");
        loc2.setName("Test Location 2");
        locationRepository.save(loc2);

        mockMvc.perform(get("/api/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[*].code", hasItems("T1", "T2")))
                .andExpect(jsonPath("$[*].name", hasItems("Test Location 1", "Test Location 2")));
    }
}
