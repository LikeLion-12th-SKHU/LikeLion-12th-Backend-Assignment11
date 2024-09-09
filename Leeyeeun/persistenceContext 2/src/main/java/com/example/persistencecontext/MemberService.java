package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public void checkEntityIdentity(Long id) {
        Member member1 = memberRepository.getMemberById(id);
        Member member2 = memberRepository.getMemberById(id);

        // 동일성 확인 (1차 캐시를 통한 동일성 보장)
        if (member1 == member2) {
            System.out.println("같은 트랜잭션 내에서는 동일한 엔티티를 반환합니다.");
        } else {
            System.out.println("동일하지 않은 엔티티가 반환되었습니다.");
        }
    }

    public Member updateMember(Long id) {
        return memberRepository.updateMember(id, "변경!!");
    }

    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }

    @Transactional
    public Member detachAndMerge(Long id) {
        // 엔티티 조회
        Member member = memberRepository.getMemberById(id);

        // 준영속 상태로 만들기
        entityManager.detach(member);

        // 준영속 상태이므로 업데이트하면 예외가 발생 X (DB에 반영되지 않음)
        member.updateInfo("준영속 상태에서 변경!");

        // 병합하여 다시 영속 상태로 만들기
        Member mergedMember = entityManager.merge(member);

        return mergedMember;
    }
}
