package org.example.persistencecontext;

import org.example.persistencecontext.Api.requset.MemberSaveReqDto;
import org.example.persistencecontext.Api.requset.MemberUpdateReqDto;
import org.example.persistencecontext.Api.response.MemberInfoResDto;
import org.example.persistencecontext.Api.response.MemberListResDto;
import org.example.persistencecontext.domain.Member;
import org.example.persistencecontext.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void memberSave(MemberSaveReqDto memberSaveReqDto) {
        Member member = Member.builder()
                .name(memberSaveReqDto.name())
                .build();

        memberRepository.save(member);
    }

    public MemberListResDto memberFindAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberInfoResDto> memberInfoResDtoList = members.stream()
                .map(MemberInfoResDto::from).toList();

        return MemberListResDto.from(memberInfoResDtoList);
    }

    public MemberInfoResDto memberFindOne(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        return MemberInfoResDto.from(member);
    }

    public void memberUpdate(Long memberId, MemberUpdateReqDto memberUpdateReqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다. ID : " + memberId));

        member.update(memberUpdateReqDto);
    }

    public void memberDelete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다. ID : " + memberId));

        memberRepository.delete(member);
    }
}
