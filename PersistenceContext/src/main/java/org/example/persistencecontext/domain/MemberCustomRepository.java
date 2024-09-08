package org.example.persistencecontext.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.persistencecontext.Api.requset.MemberSaveReqDto;
import org.example.persistencecontext.Api.requset.MemberUpdateReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberCustomRepository {
    // EntityManager 를 빈으로 주입할 때 사용하는 어노테이션
    @PersistenceContext
    private EntityManager entityManager;

    public void save(MemberSaveReqDto memberSaveReqDto) {
        entityManager.persist(memberSaveReqDto);    // 비영속을 영속 상태로 만드는 함수

        entityManager.close();                      // 영속성 컨텍스트 종료 함수
    }

    public List<Member> getAllMembers() {
        // 쿼리를 생성하는 함수
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member getMemberById(Long id) {
        // 쿼리를 찾는 함수
        return entityManager.find(Member.class, id);
    }

    public Member updateMember(Long id, MemberUpdateReqDto memberUpdateReqDto) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            member.update(memberUpdateReqDto);
            entityManager.merge(member); // 준영속 상태의 엔티티를 영속 상태로 변경 시 사용하는 함수
        } else {
            throw new RuntimeException("사용자가 존재하지 않습니다.");
        }
        entityManager.close();          // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수
        return member;
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);// 영속성 컨텍스트에 있는 엔티티를 삭제한다. DB 에서도 삭제된다.
        } else {
            throw new RuntimeException("사용자가 존재하지 않습니다.");
        }
        entityManager.close();          // 영속성 컨텍스트에 있는 엔티티를 삭제하는 함수
    }
}
