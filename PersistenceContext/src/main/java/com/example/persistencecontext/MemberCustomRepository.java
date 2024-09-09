package com.example.persistencecontext;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MemberCustomRepository implements MemberRepository{
    @PersistenceContext                         // EntityManager 를 빈으로 주입할 때 사용하는 어노테이션
    private EntityManager entityManager;

    public MemberCustomRepository() {
    }

    public Member save(Member member) {
        entityManager.persist(member);          // 비영속을 영속 상태로 만드는 함수

        entityManager.close();                  // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수
        return member;
    }

    public List<Member> getAllMembers() {
        // 쿼리를 생성하는 함수
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member getMemberById(Long id) {
        // 쿼리를 찾는 함수
        return entityManager.find(Member.class, id);
    }

    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.updateInfo(name);
            entityManager.merge(member);            // 준영속 상태의 엔티티를 영속 상태로 변경 시 사용하는 함수
        } else {
            throw new RuntimeException("맴버가 존재하지 않습니다.");
        }
    entityManager.close();                          // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수
        return member;
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);           // 영속성 컨텍스트에 있는 엔티티를 삭제한다. DB 에서도 삭제된다.
        } else {
            throw new RuntimeException("맴버가 존재하지 않습니다.");
        }
        entityManager.close();                      // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수
    }

    @Transactional
    public void checkIdentityAndDetach(Long memberId) {
        Member member1 = entityManager.find(Member.class, memberId);
        Member member2 = entityManager.find(Member.class, memberId);

        System.out.println("is same member1, member2? :" + (member1 == member2));


        entityManager.detach(member1);              // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수

        // 준영속 상태인 엔티티 병합, 재생성되어 영속 상태로 전환
        Member mergedMember = entityManager.merge(member1);

        // 영속 상태로 만든 변수를 원래 변수와 서로 비교하는 출력문
        System.out.println("is same member1, mergedMember? :" + (member1 == mergedMember));
    }
}
