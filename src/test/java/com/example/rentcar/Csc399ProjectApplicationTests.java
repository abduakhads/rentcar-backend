package com.example.rentcar;

import com.example.controllers.LocationControllerTest;
import com.example.services.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CarServiceTest.class,
        ReservationServiceTest.class,
        LocationServiceTest.class,
        ExtraServiceTest.class,
        MemberServiceTest.class,
        LocationControllerTest.class
})
public class Csc399ProjectApplicationTests {
}
