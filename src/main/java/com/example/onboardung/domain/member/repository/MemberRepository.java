package com.example.onboardung.domain.member.repository;

import com.example.onboardung.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
