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

    //엔티티 저장
    public Member save(Member member) {
        entityManager.persist(member);

        entityManager.close();
        return member;
    }

    //엔티티 전체 조회
    public List<Member> getAllMembers() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    //엔티티 id 이용하여 조회
    public Member getMemberById(Long id) {
        return entityManager.find(Member.class, id);
    }

    //엔티티 수정
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

    //엔티티 삭제
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }
        entityManager.close();
    }

    // 추가: 트랜잭션 내에서 동일한 엔티티 여러 번 조회 및 준영속 엔티티 병합
    @Transactional
    public void checkCacheAndMerge(Long id) {
        // 첫 번째 조회
        Member member1 = entityManager.find(Member.class, id);
        System.out.println("첫 번째 조회: " + member1.getName());

        // 두 번째 조회
        Member member2 = entityManager.find(Member.class, id);
        System.out.println("두 번째 조회: " + member2.getName());

        // 동일성 확인 (true여야 함)
        System.out.println("result: " + (member1 == member2));

        // 준영속 상태로 만들기
        entityManager.detach(member1);

        // 병합 (merge)
        Member mergedMember = entityManager.merge(member1);
        System.out.println("병합 후 이름: " + mergedMember.getName());
    }
}
