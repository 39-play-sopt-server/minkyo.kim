package org.sopt.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.response.CommonResponse;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.dto.response.CreateMemberResponse;
import org.springframework.http.ResponseEntity;

/**
 * 회원 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "회원 API")
public interface MemberApiDocs {
    @Operation(summary = "회원 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임이 비어 있는 경우"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 닉네임인 경우")
    })
    ResponseEntity<CommonResponse<CreateMemberResponse>> createMember(
            CreateMemberRequest request
    );

    @Operation(summary = "회원 닉네임 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 닉네임 수정 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임이 비어 있는 경우"),
            @ApiResponse(responseCode = "404", description = "회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> updateMember(
            @Parameter(description = "수정할 회원 ID") Long id,
            UpdateMemberRequest request
    );

    @Operation(summary = "회원 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> deleteMember(
            @Parameter(description = "삭제할 회원 ID") Long id
    );
}
