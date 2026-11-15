package org.sopt.member.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 클라이언트의 회원 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
@RestController
@RequestMapping("/api/v1/members")
public class MemberController implements MemberApiDocs {
    private final MemberService memberService;

    public MemberController(
            MemberService memberService
    ) {
        this.memberService = memberService;
    }

    /**
     * 지정한 ID의 회원 닉네임을 수정합니다.
     *
     * @param memberId 수정할 회원 ID
     * @param request 회원 수정 요청
     * @return 회원 수정 처리 결과
     */
    @Override
    @PatchMapping(path = "/me")
    public ResponseEntity<CommonResponse<Void>> updateMember(
            @AuthenticationPrincipal Long memberId,
            @RequestBody(required = true) UpdateMemberRequest request
    ) {
        memberService.updateMember(memberId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "닉네임이 수정되었습니다.",
                        null
                ));
    }

    /**
     * 지정한 ID의 회원을 탈퇴 처리합니다.
     *
     * @param memberId 삭제할 회원 ID
     * @return 회원 삭제 처리 결과
     */
    @Override
    @DeleteMapping(path = "/me")
    public ResponseEntity<CommonResponse<Void>> deleteMember(
            @AuthenticationPrincipal Long memberId
    ) {
        memberService.deleteMember(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "회원이 삭제되었습니다.",
                        null
                ));
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<Long>> me(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "회원 조회에 성공했습니다",
                        null
                ));
    }
}
