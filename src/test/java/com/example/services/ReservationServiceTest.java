package com.example.services;

import com.example.rentcar.RentCar;
import com.example.models.*;
import com.example.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RentCar.class)
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ExtraRepository extraRepository;

    private Car testCar;
    private Member testMember;
    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = new Location();
        testLocation.setCode("RES-LOC");
        testLocation.setName("Res Location");
        locationRepository.save(testLocation);

        testCar = new Car();
        testCar.setBarcode("RES-CAR");
        testCar.setStatus(CarStatus.AVAILABLE);
        testCar.setLocation(testLocation);
        testCar.setDailyPrice(100.0);
        carRepository.save(testCar);

        testMember = new Member();
        testMember.setName("John Doe");
        testMember.setDrivingLicenseNumber("DL-12345");
        memberRepository.save(testMember);
    }

    @Test
    void testCreateReservation() {
        Reservation res = new Reservation();
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(testLocation);
        res.setPickupDateTime(LocalDateTime.now().plusDays(1));
        res.setDropoffDateTime(LocalDateTime.now().plusDays(3));
        res.setExtras(new ArrayList<>());

        Reservation created = reservationService.createReservation(res);

        assertNotNull(created.getId());
        assertNotNull(created.getReservationNumber());
        assertEquals(ReservationStatus.ACTIVE, created.getStatus());
    }

    @Test
    void testCancelReservation() {
        Reservation res = new Reservation();
        res.setReservationNumber("99999999");
        res.setStatus(ReservationStatus.ACTIVE);
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(testLocation);
        res.setExtras(new ArrayList<>());
        reservationRepository.save(res);

        boolean result = reservationService.cancelReservation("99999999");

        assertTrue(result);
        Reservation updated = reservationRepository.findByReservationNumber("99999999").get();
        assertEquals(ReservationStatus.CANCELLED, updated.getStatus());
    }

    @Test
    void testReturnCar_UpdatesLocation() {
        Location dropoffLoc = new Location();
        dropoffLoc.setCode("DROP-LOC");
        dropoffLoc.setName("Dropoff Point");
        locationRepository.save(dropoffLoc);

        Reservation res = new Reservation();
        res.setReservationNumber("88888888");
        res.setStatus(ReservationStatus.ACTIVE);
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(dropoffLoc);
        res.setExtras(new ArrayList<>());
        reservationRepository.save(res);

        boolean result = reservationService.returnCar("88888888");

        assertTrue(result);

        Reservation updatedRes = reservationRepository.findByReservationNumber("88888888").get();
        assertEquals(ReservationStatus.COMPLETED, updatedRes.getStatus());

        Car updatedCar = carRepository.findById(testCar.getId()).get();
        assertEquals("DROP-LOC", updatedCar.getLocation().getCode());
    }

    @Test
    void testAddExtraToReservation() {
        Reservation res = new Reservation();
        res.setReservationNumber("EXT-123");
        res.setStatus(ReservationStatus.ACTIVE);
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(testLocation);
        res.setExtras(new ArrayList<>());
        reservationRepository.save(res);

        Extra extra = new Extra();
        extra.setName("Snow Chains");
        extra.setPrice(java.math.BigDecimal.valueOf(50.0));
        extraRepository.save(extra);

        boolean success = reservationService.addExtraToReservation("EXT-123", extra.getId());

        assertTrue(success);
        Reservation updated = reservationRepository.findByReservationNumber("EXT-123").get();
        assertEquals(1, updated.getExtras().size());
        assertEquals("Snow Chains", updated.getExtras().get(0).getName());
    }

    @Test
    void testDeleteReservation() {
        Reservation res = new Reservation();
        res.setReservationNumber("DEL-RES");
        res.setStatus(ReservationStatus.ACTIVE);
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(testLocation);
        res.setExtras(new ArrayList<>());

        reservationRepository.save(res);

        boolean deleted = reservationService.deleteReservation("DEL-RES");

        assertTrue(deleted);
        assertTrue(reservationRepository.findByReservationNumber("DEL-RES").isEmpty());
    }

    @Test
    void testGetAllReservations() {
        Reservation r1 = new Reservation();
        r1.setReservationNumber("ALL-1"); r1.setStatus(ReservationStatus.ACTIVE);
        r1.setCar(testCar); r1.setMember(testMember); r1.setPickupLocation(testLocation); r1.setDropoffLocation(testLocation);
        r1.setExtras(new ArrayList<>());
        reservationRepository.save(r1);

        Reservation r2 = new Reservation();
        r2.setReservationNumber("ALL-2"); r2.setStatus(ReservationStatus.CANCELLED);
        r2.setCar(testCar); r2.setMember(testMember); r2.setPickupLocation(testLocation); r2.setDropoffLocation(testLocation);
        r2.setExtras(new ArrayList<>());
        reservationRepository.save(r2);

        List<Reservation> all = reservationService.getAllReservations();

        assertTrue(all.size() >= 2);
    }

    @Test
    void testGetActiveReservations() {
        Reservation r1 = new Reservation();
        r1.setReservationNumber("ACT-1"); r1.setStatus(ReservationStatus.ACTIVE);
        r1.setCar(testCar); r1.setMember(testMember); r1.setPickupLocation(testLocation); r1.setDropoffLocation(testLocation);
        r1.setExtras(new ArrayList<>());
        reservationRepository.save(r1);

        Reservation r2 = new Reservation();
        r2.setReservationNumber("CAN-1"); r2.setStatus(ReservationStatus.CANCELLED);
        r2.setCar(testCar); r2.setMember(testMember); r2.setPickupLocation(testLocation); r2.setDropoffLocation(testLocation);
        r2.setExtras(new ArrayList<>());
        reservationRepository.save(r2);

        List<Reservation> active = reservationService.getActiveReservations();

        boolean hasActive = active.stream().anyMatch(r -> r.getReservationNumber().equals("ACT-1"));
        boolean hasCancelled = active.stream().anyMatch(r -> r.getReservationNumber().equals("CAN-1"));

        assertTrue(hasActive);
        assertFalse(hasCancelled);
    }

    @Test
    void testGetReservationByNumber() {
        Reservation res = new Reservation();
        res.setReservationNumber("NUM-TEST");
        res.setStatus(ReservationStatus.ACTIVE);
        res.setCar(testCar);
        res.setMember(testMember);
        res.setPickupLocation(testLocation);
        res.setDropoffLocation(testLocation);
        res.setExtras(new ArrayList<>());
        reservationRepository.save(res);

        Optional<Reservation> found = reservationService.getReservationByNumber("NUM-TEST");
        assertTrue(found.isPresent());
    }
}