package org.sopt.member.dto.request;

/**
 * 회원 수정에 필요한 클라이언트 요청입니다.
 *
 * @param nickname 변경할 닉네임
 */
public record UpdateMemberRequest(
        String nickname
) {
}
