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
    private EntityManager entityManager;

    // 엔티티 저장
    @Transactional
    public Member save(Member member) {
        entityManager.persist(member);

        entityManager.close();
        return member;
    }

    // 엔티티 전체 조회
    public List<Member> getAllMembers() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    // 엔티티 id 이용하여 조회
    public Member getMemberById(Long id) {
        return entityManager.find(Member.class, id);
    }

    // 수정
    @Transactional
    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.updateInfo(name);
            entityManager.merge(member);
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        entityManager.close();
        return member;
    }

    // 삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }
        entityManager.close();
    }

    // 동일성 확인 및 병합
    @Transactional
    public void checkAndMerge(Long id) {
        Member member1 = entityManager.find(Member.class, id); // ID로 Member조회
        Member member2 = entityManager.find(Member.class, id); // 동일 ID로 다시 조회

        System.out.println("result: " + (member1 == member2));  // 1차 캐시를 통해 동일성 확인

        entityManager.detach(member1); // 엔티티를 1차 캐시에서 분리
        Member resultMember = entityManager.merge(member1); // 병합

        System.out.println("merge 후 이름 확인: " + resultMember.getName());
    }
}
