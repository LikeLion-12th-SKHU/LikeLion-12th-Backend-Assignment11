package com.example.persistencecontext;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save() {
        memberRepository.save(new Member("헤헿"));
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    public Member getMemberById(Long id) {
        return memberRepository.getMemberById(id);
    }

    public Member updateMember(Long id) {
        return memberRepository.updateMember(id, "변경!!");
    }
    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }

    // 추가: 1차 캐시 테스트 및 준영속 엔티티 병합 로직 호출
    public void checkCacheAndMerge(Long id) {
        memberRepository.checkCacheAndMerge(id);
    }
}
