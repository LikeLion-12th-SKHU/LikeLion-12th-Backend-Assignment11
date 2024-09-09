package com.example.persistencecontext;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberCustomRepository memberCustomRepository;

    public MemberController(MemberService memberService, MemberCustomRepository memberCustomRepository) {
        this.memberService = memberService;
        this.memberCustomRepository = memberCustomRepository;
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


    @GetMapping("/merge/{id}")
    public ResponseEntity<String> checkIdentityAndDetach(@PathVariable Long id) {
        memberService.checkIdentityAndDetach(id);
        return ResponseEntity.ok("병합 및 재생성 완료!");
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
}
