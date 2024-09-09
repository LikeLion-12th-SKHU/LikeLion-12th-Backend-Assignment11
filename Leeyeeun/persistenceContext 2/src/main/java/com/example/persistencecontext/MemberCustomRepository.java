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
}
