package com.example.services;

import com.example.rentcar.RentCar;
import com.example.models.*;
import com.example.repositories.CarRepository;
import com.example.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RentCar.class)
@Transactional
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = new Location();
        testLocation.setCode("TEST-LOC");
        testLocation.setName("Test Location");
        locationRepository.save(testLocation);
    }

    @Test
    void testSaveCar() {
        Car car = new Car();
        car.setBarcode("SAVE-BAR");
        car.setBrand("Toyota");
        car.setStatus(CarStatus.AVAILABLE);
        car.setLocation(testLocation);
        car.setDailyPrice(100.0);

        Car savedCar = carService.saveCar(car);

        assertNotNull(savedCar.getId());
        assertEquals("SAVE-BAR", savedCar.getBarcode());
    }

    @Test
    void testGetCarByBarcode() {
        Car car = new Car();
        car.setBarcode("FIND-BAR");
        car.setBrand("Honda");
        car.setStatus(CarStatus.AVAILABLE);
        car.setLocation(testLocation);
        carRepository.save(car);

        Optional<Car> fetchedCar = carService.getCarByBarcode("FIND-BAR");

        assertTrue(fetchedCar.isPresent());
        assertEquals("Honda", fetchedCar.get().getBrand());
    }

    @Test
    void testGetCarById() {
        Car car = new Car();
        car.setBarcode("ID-TEST");
        car.setLocation(testLocation);
        Car saved = carRepository.save(car);

        Optional<Car> found = carService.getCarById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("ID-TEST", found.get().getBarcode());
    }

    @Test
    void testGetAllCars() {
        Car c1 = new Car(); c1.setBarcode("ALL-1"); c1.setLocation(testLocation); carRepository.save(c1);
        Car c2 = new Car(); c2.setBarcode("ALL-2"); c2.setLocation(testLocation); carRepository.save(c2);

        List<Car> all = carService.getAllCars();
        assertTrue(all.size() >= 2);
    }

    @Test
    void testDeleteCar_Success() {
        Car car = new Car();
        car.setBarcode("DEL-123");
        car.setLocation(testLocation);
        carRepository.save(car);

        boolean isDeleted = carService.deleteCar("DEL-123");

        assertTrue(isDeleted);
        assertTrue(carRepository.findByBarcode("DEL-123").isEmpty());
    }

    @Test
    void testSearchAvailableCars() {
        Car car = new Car();
        car.setBarcode("SEARCH-01");
        car.setStatus(CarStatus.AVAILABLE);
        car.setLocation(testLocation);
        car.setCategory("SUV");
        car.setTransmissionType(TransmissionType.AUTOMATIC);
        car.setNumberOfSeats(5);
        car.setDailyPrice(200.0);
        carRepository.save(car);

        List<Car> results = carService.searchAvailableCars(
                "TEST-LOC",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "SUV", null, null, null, null
        );

        assertFalse(results.isEmpty());
        assertEquals("SEARCH-01", results.get(0).getBarcode());
    }
}