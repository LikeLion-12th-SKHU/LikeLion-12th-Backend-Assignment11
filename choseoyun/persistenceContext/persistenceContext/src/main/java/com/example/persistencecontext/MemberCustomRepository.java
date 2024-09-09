package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MemberCustomRepository implements MemberRepository {
    @PersistenceContext //EntityManager를 빈으로 주입할 때 사용하는 어노테이션, 스프링 컨테이너가 시작될 때 EntityManager를 만들어서 빈으로 등록해둔다.
    private EntityManager entityManager; //스프링에서는 영속성 관리를 위해 EntityManager가 존재한다.

    // 저장
    @Transactional
    public Member save(Member member) {
        entityManager.persist(member); //persist() 시점에는 영속성 컨텍스트에 저장한다.
        entityManager.clear(); //clear 메서드는 영속성 컨텍스트의 모든 엔티티를 초기화하는 역할한다.

        return member;
    }

    // 전체 조회
    public List<Member> getAllMembers() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    // id 이용하여 조회
    public Member getMemberById(Long id) {
        return entityManager.find(Member.class, id);
    }

    //수정
    @Transactional
    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.updateInfo(name);
            entityManager.merge(member);    //merge(병합)은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용한다.
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        entityManager.close();  //close 메서드는 영속성 컨텍스트를 종료하고 관련 리소스를 해제하여 엔티티를 더 이상 사용할 수 없는 상태로 만든다.
        return member;
    }

    //삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);   //remove가 호출되는 순간 바로 member는 영속성 컨텍스트에서 제거된다.
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }
        entityManager.close();
    }

    @Transactional
    public void checkEntity(Long id) {
        Member m1 = entityManager.find(Member.class, id);
        Member m2 = entityManager.find(Member.class, id);

        System.out.println("1차 캐시를 통해 동일성을 보장하는지?" + (m1==m2));

        entityManager.detach(m1); //member 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
        Member mergedMember = entityManager.merge(m1);    //재병합

        System.out.println("result: " + mergedMember.getName());

    }


}
