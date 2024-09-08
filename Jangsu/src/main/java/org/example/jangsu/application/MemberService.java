package org.example.jangsu.application;

import jakarta.validation.Valid;
import org.example.jangsu.api.dto.request.MemberSaveReqDto;
import org.example.jangsu.api.dto.request.MemberUpdateReqDto;
import org.example.jangsu.domain.Member;
import org.example.jangsu.domain.respository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public void memberSave(@Valid MemberSaveReqDto memberSaveReqDto) {
        Member member = Member.builder()
                .email(memberSaveReqDto.email())
                .password(memberSaveReqDto.password())
                .nickname(memberSaveReqDto.nickname())
                .status(memberSaveReqDto.status())
                .build();

        memberRepository.save(member);
    }

    // 사용자 업데이트
    @Transactional
    public void memberUpdate(@Valid Long memberId, MemberUpdateReqDto memberUpdateReqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        member.update(memberUpdateReqDto);
    }

    // 회원 탈퇴
    public void memberDelete(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() ->new IllegalArgumentException("해당하는 사용자가 없습니다."));

        memberRepository.deleteById(memberId);
    }
}
