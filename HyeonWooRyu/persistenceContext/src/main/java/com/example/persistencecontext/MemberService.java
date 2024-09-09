package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void checkAndMerge(Long id) {
        memberRepository.checkAndMerge(id); // ID로 동일성 확인 및 병합
    }
}