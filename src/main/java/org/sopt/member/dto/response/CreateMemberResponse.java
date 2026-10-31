package org.sopt.member.dto.response;

/**
 * 회원 생성 후 클라이언트에 전달할 정보를 나타냅니다.
 *
 * @param memberId 생성된 회원 ID
 */
public record CreateMemberResponse(
        Long memberId
) {
}
