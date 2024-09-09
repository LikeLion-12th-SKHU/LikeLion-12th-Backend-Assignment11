package com.example.persistencecontext;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    Member save(Member member);
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member updateMember(Long id, String name);
    void deleteMember(Long id);
}
