package com.example.services;

import com.example.rentcar.RentCar;
import com.example.models.Extra;
import com.example.repositories.ExtraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RentCar.class)
@Transactional
public class ExtraServiceTest {

    @Autowired
    private ExtraService extraService;

    @Autowired
    private ExtraRepository extraRepository;

    @Test
    void testSaveExtra() {
        Extra extra = new Extra();
        extra.setName("Save Extra");
        extra.setPrice(BigDecimal.valueOf(100.0));

        Extra saved = extraService.saveExtra(extra);

        assertNotNull(saved.getId());
        assertEquals("Save Extra", saved.getName());
    }

    @Test
    void testGetExtraByName() {
        Extra extra = new Extra();
        extra.setName("Name Extra");
        extra.setPrice(BigDecimal.valueOf(200.0));
        extraRepository.save(extra);

        Optional<Extra> found = extraService.getExtraByName("Name Extra");

        assertTrue(found.isPresent());
        assertEquals(0, BigDecimal.valueOf(200.0).compareTo(found.get().getPrice()));
    }

    @Test
    void testGetExtraById() {
        Extra extra = new Extra();
        extra.setName("ID Extra");
        extra.setPrice(BigDecimal.valueOf(50.0));
        Extra saved = extraRepository.save(extra);

        Optional<Extra> found = extraService.getExtraById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("ID Extra", found.get().getName());
    }

    @Test
    void testGetAllExtras() {
        Extra e = new Extra(); e.setName("E1"); extraRepository.save(e);

        List<Extra> all = extraService.getAllExtras();
        assertFalse(all.isEmpty());
    }

    @Test
    void testDeleteExtra() {
        Extra e = new Extra(); e.setName("DEL");
        Extra saved = extraRepository.save(e);

        extraService.deleteExtra(saved.getId());

        assertTrue(extraRepository.findById(saved.getId()).isEmpty());
    }
}