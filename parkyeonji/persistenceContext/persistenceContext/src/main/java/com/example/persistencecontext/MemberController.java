package com.example.persistencecontext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping()
    public ResponseEntity<String> save() {
        memberService.save();
        return ResponseEntity.ok("사용자 저장!");
    }

    @GetMapping()
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.updateMember(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("사용자 삭제!");
    }

    // 추가: 동일 엔티티 조회 및 준영속 엔티티 병합
    @GetMapping("/equalEntity/{id}")
    public ResponseEntity<String> checkCacheAndMerge(@PathVariable Long id) {
        memberService.checkCacheAndMerge(id);
        return ResponseEntity.ok("캐시 테스트 및 병합 완료!");
    }
}
