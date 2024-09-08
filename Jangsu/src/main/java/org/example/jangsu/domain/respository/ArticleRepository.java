package org.example.jangsu.domain.respository;

import org.example.jangsu.domain.Article;
import org.example.jangsu.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByMember(Member member);
}
