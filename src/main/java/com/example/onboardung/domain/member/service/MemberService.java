package com.example.onboardung.domain.member.service;

import com.example.onboardung.domain.member.dto.MemberResponse;
import com.example.onboardung.domain.member.repository.MemberRepository;
import com.example.onboardung.global.util.ConverterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse findMember(Long userId){
        return ConverterDto.convertMemberResponse(memberRepository.findById(userId).orElseThrow());
    }
}
