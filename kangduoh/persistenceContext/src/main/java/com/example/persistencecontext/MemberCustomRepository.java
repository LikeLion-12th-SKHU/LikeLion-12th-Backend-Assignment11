package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MemberCustomRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 엔티티 저장
    @Transactional
    public Member save(Member member) {
        em.persist(member);
        em.clear();

        return member;
    }

    // 엔티티 전체 조회
    public List<Member> getAllMembers() {
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    // 엔티티 id로 조회
    public Member getMemberById(Long id) {
        return em.find(Member.class, id);
    }

    // 수정
    @Transactional
    public Member updateMember(Long id, String name) {
        Member member = em.find(Member.class, id);

        if (member != null) {
            member.updateInfo(name);
            em.merge(member);
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        em.close();
        return member;
    }

    // 삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = em.find(Member.class, id);

        if (member != null) {
            em.remove(member);
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        em.close();
    }

    // 동일성 확인 및 준영속 엔티티 병합
    @Transactional
    public void checkAndMerge(Long id) {
        // 같은 트랜잭션 내에서 동일한 엔티티를 여러 번 조회
        Member member1 = em.find(Member.class, id);
        Member member2 = em.find(Member.class, id);

        // 1차 캐시를 통해 동일성 보장 확인
        System.out.println(member1 == member2 ? "동일 엔티티" : "다른 엔티티");

        // 준영속 상태로 만듦
        em.detach(member1);

        // 준영속 상태에서 수정
        member1.updateInfo("변경!");

        // 다시 병합하여 영속 상태로 변경
        Member mergedMember = em.merge(member1);

        // 병합된 엔티티의 수정된 값 출력
        System.out.println(mergedMember.getName()); // "변경!"
    }
}
