package org.sopt.member.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.dto.response.CreateMemberResponse;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 클라이언트의 회원 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
@RestController
@RequestMapping("/api/v1/members")
public class MemberController implements MemberApiDocs {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    /**
     * 회원 생성 요청을 처리합니다.
     *
     * @param request 회원 생성 요청
     * @return 생성된 회원 ID를 포함한 처리 결과
     */
    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<CreateMemberResponse>> createMember(
            @RequestBody(required = true) CreateMemberRequest request
    ) {
        Long memberId = service.createMember(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        "회원이 생성되었습니다.",
                        new CreateMemberResponse(memberId)
                ));
    }

    /**
     * 지정한 ID의 회원 닉네임을 수정합니다.
     *
     * @param id 수정할 회원 ID
     * @param request 회원 수정 요청
     * @return 회원 수정 처리 결과
     */
    @Override
    @PatchMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Void>> updateMember(
            @PathVariable Long id,
            @RequestBody(required = true) UpdateMemberRequest request
            ) {
        service.updateMember(id, request);

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
     * @param id 삭제할 회원 ID
     * @return 회원 삭제 처리 결과
     */
    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteMember(
            @PathVariable Long id
    ) {
        service.deleteMember(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "회원이 삭제되었습니다.",
                        null
                ));
    }
}
