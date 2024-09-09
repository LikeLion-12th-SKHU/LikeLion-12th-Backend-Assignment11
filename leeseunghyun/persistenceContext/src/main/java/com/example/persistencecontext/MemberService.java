package com.example.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;  // EntityManager 주입

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save() {
        memberRepository.save(new Member("헤헿"));
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    public Member getMemberById(Long id) {
        return memberRepository.getMemberById(id);
    }

    public Member updateMember(Long id) {
        return memberRepository.updateMember(id, "변경!!");
    }

    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }

    //과제 스따뚜~~


    @Transactional
    public void checkEntityIdentity(Long id) {
        //데이터베이스에서 같은 ID를 가진 엔티티를 두 번 조회한당
        Member member1 = em.find(Member.class, id);
        Member member2 = em.find(Member.class, id);

        // 동일성 확인
        System.out.println("같은 엔티티인가요?: " + (member1 == member2));
    }


    @Transactional
    public void mergeDetachedEntity(Long id) {
        // 데이터베이스에서 엔티티를 조회함 조회
        Member member = em.find(Member.class, id);

        //분리
        em.detach(member); //em은 entitymanager의 약어!!
        //수정
        member.updateInfo("변경된 이름");
        //병합
        Member mergedMember = em.merge(member);
        //동일성확인
        System.out.println("병합 후 같은 엔티티인가요?: " + (member == mergedMember));
        //변경된 이름 출력
        System.out.println("변경된 이름: " + mergedMember.getName());
    }
}