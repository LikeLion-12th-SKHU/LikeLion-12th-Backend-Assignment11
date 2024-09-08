package org.example.persistencecontext.Api;

import org.example.persistencecontext.Api.requset.MemberSaveReqDto;
import org.example.persistencecontext.Api.requset.MemberUpdateReqDto;
import org.example.persistencecontext.Api.response.MemberInfoResDto;
import org.example.persistencecontext.Api.response.MemberListResDto;
import org.example.persistencecontext.MemberService;
import org.example.persistencecontext.domain.MemberCustomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberCustomRepository memberCustomRepository;

    public MemberController(MemberService memberService, MemberCustomRepository memberCustomRepository) {
        this.memberService = memberService;
        this.memberCustomRepository = memberCustomRepository;
    }
    @PostMapping
    public ResponseEntity<String> studentSave(@RequestBody MemberSaveReqDto memberSaveReqDto) {
        memberCustomRepository.save(memberSaveReqDto);
        return new ResponseEntity<>("사용자 정보 저장(신규)!", HttpStatus.CREATED);
    }

    // 한명 불러오기
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResDto> memberFindOne(@PathVariable("memberId") Long memberId) {
        MemberInfoResDto memberInfoResDto = memberService.memberFindOne(memberId);
        return new ResponseEntity<>(memberInfoResDto, HttpStatus.OK);
    }

    // 전부 조회
    @GetMapping
    public ResponseEntity<MemberListResDto> studentFindAll() {
        MemberListResDto memberListResDto = memberService.memberFindAll();
        return new ResponseEntity<>(memberListResDto, HttpStatus.OK);
    }

    // 수정하기
    @PatchMapping("/{memberId}")
    public ResponseEntity<String> memberUpdate(@PathVariable("memberId")Long memberId,
                                                @RequestBody MemberUpdateReqDto memberUpdateReqDto) {
        memberCustomRepository.updateMember(memberId, memberUpdateReqDto);
        return new ResponseEntity<>("사용자 정보 수정!", HttpStatus.OK);
    }

    // 삭제하기
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> memberDelete(@PathVariable("memberId")Long memberId) {
        memberCustomRepository.deleteMember(memberId);
        return new ResponseEntity<>("사용자 정보 삭제!", HttpStatus.OK);
    }
}
