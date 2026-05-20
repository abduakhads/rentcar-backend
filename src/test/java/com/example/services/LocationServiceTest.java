package com.example.services;

import com.example.rentcar.RentCar;
import com.example.models.Location;
import com.example.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RentCar.class)
@Transactional
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testSaveLocation() {
        Location loc = new Location();
        loc.setCode("SAVE-TEST");
        loc.setName("Save Location");

        Location saved = locationService.saveLocation(loc);

        assertNotNull(saved.getId());
        assertEquals("SAVE-TEST", saved.getCode());
    }

    @Test
    void testGetLocationByCode() {
        Location loc = new Location();
        loc.setCode("CODE-TEST");
        loc.setName("Code Location");
        locationRepository.save(loc);

        Optional<Location> found = locationService.getLocationByCode("CODE-TEST");

        assertTrue(found.isPresent());
        assertEquals("Code Location", found.get().getName());
    }

    @Test
    void testGetLocationById() {
        Location loc = new Location();
        loc.setCode("ID-TEST");
        loc.setName("ID Location");
        Location saved = locationRepository.save(loc);

        Optional<Location> found = locationService.getLocationById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("ID-TEST", found.get().getCode());
    }

    @Test
    void testGetAllLocations() {
        Location loc = new Location(); loc.setCode("L1"); loc.setName("L1"); locationRepository.save(loc);

        List<Location> all = locationService.getAllLocations();
        assertFalse(all.isEmpty());
    }

    @Test
    void testDeleteLocation() {
        Location loc = new Location();
        loc.setCode("DEL-LOC");
        loc.setName("Delete Me");
        Location saved = locationRepository.save(loc);

        locationService.deleteLocation(saved.getId());

        assertTrue(locationRepository.findById(saved.getId()).isEmpty());
    }
}