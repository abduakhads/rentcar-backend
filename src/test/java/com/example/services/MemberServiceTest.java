package com.example.services;

import com.example.rentcar.RentCar;
import com.example.models.Member;
import com.example.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RentCar.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testSaveMember() {
        Member member = new Member();
        member.setName("Save Test User");
        member.setDrivingLicenseNumber("DL-SAVE-01");
        member.setEmail("save@test.com");
        member.setPhone("555-0001");

        Member saved = memberService.saveMember(member);

        assertNotNull(saved.getId());
        assertEquals("DL-SAVE-01", saved.getDrivingLicenseNumber());
    }

    @Test
    void testGetMemberById() {
        Member member = new Member();
        member.setName("Get Test User");
        member.setDrivingLicenseNumber("DL-GET-01");
        member.setEmail("get@test.com");
        Member saved = memberRepository.save(member);

        Optional<Member> found = memberService.getMemberById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("DL-GET-01", found.get().getDrivingLicenseNumber());
    }

    @Test
    void testGetAllMembers() {
        Member m1 = new Member(); m1.setDrivingLicenseNumber("DL-ALL-1"); memberRepository.save(m1);
        Member m2 = new Member(); m2.setDrivingLicenseNumber("DL-ALL-2"); memberRepository.save(m2);

        List<Member> all = memberService.getAllMembers();
        assertTrue(all.size() >= 2);
    }

    @Test
    void testDeleteMember() {
        Member m1 = new Member();
        m1.setDrivingLicenseNumber("DL-DEL");
        Member saved = memberRepository.save(m1);

        memberService.deleteMember(saved.getId());

        Optional<Member> found = memberRepository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }
}