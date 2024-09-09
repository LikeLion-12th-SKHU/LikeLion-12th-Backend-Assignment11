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

    //과제 스타뚜~~

    //엔티티 동일성 확인
    @GetMapping("/check-identity/{id}")
    public ResponseEntity<String> checkEntityIdentity(@PathVariable Long id) {
        memberService.checkEntityIdentity(id);
        //엔티티 동일성을 확인하는 메서드이고용 이제 영속성 컨텍스트에서 동일성을 검사합니당
        return ResponseEntity.ok("엔티티 동일성 확인 완료!");
    }

    //준영속 엔티티 병합
    @PostMapping("/merge-detached/{id}")
    public ResponseEntity<String> mergeDetachedEntity(@PathVariable Long id) {
        memberService.mergeDetachedEntity(id);
        //준영속 상태의 엔티티를 병합한 다음에 영속성 컨텍스트에 다시 연결합니당
        // (영속성 컨텍스트는 데이터베이스와의 연결을 관리하고, 객체의 상태를 추적하는 메모리상의 공간/준영속 상태는 객체가 영속성 컨텍스트에서 분리된 상태)
        return ResponseEntity.ok("준영속 엔티티 병합 완료!");
    }
}
