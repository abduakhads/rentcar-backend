package com.example.config;

import com.example.models.*; // Entity sınıflarının olduğu paket
import com.example.repositories.*; // Repository'lerin olduğu paket
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final ExtraRepository extraRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (locationRepository.count() == 0) {
            System.out.println("--- INITIAL DATA LOADING... ---");

            Location loc1 = new Location();
            loc1.setCode("IST-AIR");
            loc1.setName("Istanbul Airport");
            locationRepository.save(loc1);

            Location loc2 = new Location();
            loc2.setCode("SAW-AIR");
            loc2.setName("Sabiha Gokcen Airport");
            locationRepository.save(loc2);

            Extra gps = new Extra();
            gps.setName("GPS Navigation");
            gps.setPrice(BigDecimal.valueOf(150.00));
            extraRepository.save(gps);

            Extra babySeat = new Extra();
            babySeat.setName("Baby Seat");
            babySeat.setPrice(BigDecimal.valueOf(200.50));
            extraRepository.save(babySeat);

            Member member = new Member();
            member.setName("Ahmet Yilmaz");
            member.setAddress("Kadikoy, Istanbul");
            member.setEmail("ahmet@example.com");
            member.setPhone("5551234567");
            member.setDrivingLicenseNumber("TR12345678");
            memberRepository.save(member);

            Car car1 = new Car();
            car1.setBarcode("BAR-001");
            car1.setLicensePlateNumber("34 IST 34");
            car1.setNumberOfSeats(5);
            car1.setBrand("Renault");
            car1.setModel("Clio");
            car1.setMileage(12000.5);
            car1.setDailyPrice(1500.0);
            car1.setCategory("Compact");
            car1.setStatus(CarStatus.AVAILABLE); // Enum
            car1.setTransmissionType(TransmissionType.AUTOMATIC); // Enum
            car1.setLocation(loc1);
            car1.setImageUrl("https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?auto=format&fit=crop&q=80&w=800");
            carRepository.save(car1);

            Car car2 = new Car();
            car2.setBarcode("BAR-002");
            car2.setLicensePlateNumber("06 ANK 06");
            car2.setNumberOfSeats(7);
            car2.setBrand("Ford");
            car2.setModel("Transit");
            car2.setMileage(5000.0);
            car2.setDailyPrice(2500.0);
            car2.setCategory("Minivan");
            car2.setStatus(CarStatus.AVAILABLE);
            car2.setTransmissionType(TransmissionType.MANUAL);
            car2.setLocation(loc2);
            car2.setImageUrl("https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?auto=format&fit=crop&q=80&w=800");
            carRepository.save(car2);

            Reservation res = new Reservation();
            res.setReservationNumber("12345678");
            res.setCreationDate(LocalDateTime.now());
            res.setPickupDateTime(LocalDateTime.now().plusDays(1));
            res.setDropoffDateTime(LocalDateTime.now().plusDays(3));
            res.setStatus(ReservationStatus.ACTIVE);

            res.setMember(member);
            res.setCar(car1);
            res.setPickupLocation(loc1);
            res.setDropoffLocation(loc1);

            res.setExtras(List.of(gps));

            reservationRepository.save(res);

            System.out.println("--- ALL DATA UPLOADED ---");
        }
    }
}