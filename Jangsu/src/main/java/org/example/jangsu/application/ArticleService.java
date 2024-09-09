package org.example.jangsu.application;

import jakarta.validation.Valid;
import org.example.jangsu.api.dto.request.ArticleSaveReqDto;
import org.example.jangsu.api.dto.request.ArticleUpdateReqDto;
import org.example.jangsu.api.dto.response.ArticleInfoResDto;
import org.example.jangsu.api.dto.response.ArticleListResDto;
import org.example.jangsu.domain.Article;
import org.example.jangsu.domain.Member;
import org.example.jangsu.domain.respository.ArticleRepository;
import org.example.jangsu.domain.respository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }
    // 게시글 작성하기
    @Transactional
    public ArticleInfoResDto articleSave(@Valid ArticleSaveReqDto articleSaveReqDto) {
        Article article = Article.builder()
                .articleName(articleSaveReqDto.articleName())
                .articleWriteTime(articleSaveReqDto.articleWriteTime())
                .content(articleSaveReqDto.content())
                .member(articleSaveReqDto.member())
                .build();

        articleRepository.save(article);
        return ArticleInfoResDto.from(article);
    }
    // 게시글 전부 찾기
    public ArticleListResDto articleFindAll(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException());

        List<Article> articles = articleRepository.findByMember(member);
        List<ArticleInfoResDto> articleInfoResDtoList = articles.stream()
                .map(ArticleInfoResDto::from).toList();

        return ArticleListResDto.from(articleInfoResDtoList);
    }

    // 게시글 업데이트
    public void articleUpdate(@Valid Long articleId, ArticleUpdateReqDto articleUpdateReqDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException());

        article.update(articleUpdateReqDto);
    }

    // 게시글 삭제
    public void articleDelete(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException());

        articleRepository.delete(article);
    }
}
