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
        entityManager.persist(member);  // 엔티티가 영속성 컨텍스트에 저장 - 1차 캐시에 저장
        entityManager.clear();  // 영속성 컨텍스트 초기화 - 준영속 상태로 - 1차 캐시 비우기
        return member;  // 준영속 상태 member 리턴
    }

    // 엔티티 전체 조회
    public List<Member> getAllMembers() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    // 엔티티 id 이용하여 조회
    public Member getMemberById(Long id) { // 1차 캐시에서 조회 -> 없다면 db에서 조회 -> 1차 캐시에 저장 후 리턴
        return entityManager.find(Member.class, id);
    }

    // 엔티티 수정
    @Transactional
    public Member updateMember(Long id, String name) {
        Member member = entityManager.find(Member.class, id);   // 1차 캐시 조회
        if (member != null) {
            member.updateInfo(name);    // null이 아니라면 수정 - 멤버가 존재한다면
            entityManager.merge(member);    // 준영속 상태 member -> 영속 상태로 병합
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }

        entityManager.close();  // 영속성 컨텍스트 종료
        return member; // 준영속 상태 member 리턴
    }

    // 엔티티 삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);   // 1차 캐시 조회
        if (member != null) {
            entityManager.remove(member);   // 해당 멤버를 db에서도 삭제
        } else {
            throw new RuntimeException("멤버가 존재하지 않습니다.");
        }
        entityManager.close();
    }

    // 같은 트랙잭션 내에서 같은 엔티티를 여러 번 조회 - 1차 캐시를 통해 동일성 보장하는지, 준영속 엔티티를 다시 병합 및 재생성
    @Transactional
    public Member findEntity(Long id) {
        Member member = entityManager.find(Member.class, id);
        Member member2 = entityManager.find(Member.class, id);

        System.out.println("result: " + (member == member2));  // 1차 캐시를 통해 동일성 보장하는지

        entityManager.clear();  // 준영속 상태로 - 1차 캐시 비우기

        // 준영속 member 병합 및 재생성 = 영속 상태로 관리한다는 뜻
        entityManager.merge(member);
        return member;

    }
}
