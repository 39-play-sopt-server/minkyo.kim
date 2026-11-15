package org.sopt.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.response.CommonResponse;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.springframework.http.ResponseEntity;

/**
 * 회원 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "회원 API")
@SecurityRequirement(name = "bearerAuth")
public interface MemberApiDocs {
    @Operation(summary = "회원 닉네임 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 닉네임 수정 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임이 비어 있는 경우"),
            @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> updateMember(
            @Parameter(hidden = true) Long memberId,
            UpdateMemberRequest request
    );

    @Operation(summary = "회원 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> deleteMember(
            @Parameter(hidden = true) Long memberId
    );

    @Operation(summary = "내 회원 ID 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 회원 ID 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않은 경우")
    })
    ResponseEntity<CommonResponse<Long>> me(
            @Parameter(hidden = true) Long memberId
    );
}
