package com.example.onboardung.global.util;

import com.example.onboardung.domain.member.dto.MemberResponse;
import com.example.onboardung.domain.member.entity.Member;

public class ConverterDto {

    public static MemberResponse convertMemberResponse(Member member) {
        return new MemberResponse(member.getEmail());
    }
}
