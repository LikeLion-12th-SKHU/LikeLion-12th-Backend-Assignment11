package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class  MemberCustomRepository implements MemberRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public MemberCustomRepository() {
    }
    //저장
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }
    //전체 조회
    public List<Member> getAllMembers() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }
    //id 조회
    public Member getMemberById(Long id) {
        return entityManager.find(Member.class, id);
    }
    //업데이트
    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.updateInfo(name);
            entityManager.merge(member);
        } else {
            throw new RuntimeException("맴버가 존재하지 않습니다.");
        }
        return member;
    }

    //삭제
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