package org.example.jangsu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.jangsu.api.dto.request.MemberUpdateReqDto;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @Builder
    private Member(String email, String password, String nickname,Status status) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.nickname = nickname;
    }

    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        this.email = memberUpdateReqDto.email();
        this.password = memberUpdateReqDto.password();
        this.nickname = memberUpdateReqDto.nickname();
    }
}
