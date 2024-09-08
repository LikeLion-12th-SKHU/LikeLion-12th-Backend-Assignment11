package org.example.jangsu.domain.respository;

import org.example.jangsu.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
