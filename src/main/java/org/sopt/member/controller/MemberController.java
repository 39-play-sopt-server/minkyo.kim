package org.sopt.member.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.dto.response.CreateMemberResponse;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

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
