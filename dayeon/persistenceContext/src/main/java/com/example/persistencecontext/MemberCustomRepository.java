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

    // Entity Save
    @Transactional
    public Member save(Member member) {
        entityManager.persist(member);

        entityManager.close();
        return member;
    }

    // Entity Search All
    public List<Member> getAllMembers() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    // Entity Search By Id
    public Member getMemberById(Long id) {
        return entityManager.find(Member.class, id);
    }

    // Entity Update(과제 시 참고)
    @Transactional
    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.updateInfo(name);
            entityManager.merge(member);
        } else {
            throw new RuntimeException("맴버가 존재하지 않습니다.");
        }

        entityManager.close();
        return member;
    }

    // Entity Delete
    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);
        } else {
            throw new RuntimeException("맴버가 존재하지 않습니다.");
        }

        entityManager.close();
    }

    @Transactional
    public void checkAndMerge(Long id) {
        // 같은 트랜잭션 내 같은 엔티티 여러번 조회
        Member member1 = entityManager.find(Member.class, id);
        System.out.println("맴버 1 : " + member1.getName());
        Member member2 = entityManager.find(Member.class, id);
        System.out.println("맴버 2 : " + member2.getName());

        // 1차 캐시를 통한 동일성 보장 확인
        System.out.println("동일한 맴버인가요? : " + (member1 == member2));

        // 엔티티 준영속 상태로 전환
        entityManager.detach(member1);

        // 엔티티 병합
        Member mergedMember = entityManager.merge(member1);

        // 엔티티 병합 확인
        System.out.println(member1.getName());
    }
}
