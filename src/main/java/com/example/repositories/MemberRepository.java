package com.example.repositories;

import com.example.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    java.util.Optional<Member> findByEmail(String email);
}