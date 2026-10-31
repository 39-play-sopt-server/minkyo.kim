package org.sopt.member.dto.request;

/**
 * 회원 생성에 필요한 클라이언트 요청입니다.
 *
 * @param nickname 회원 닉네임
 */
public record CreateMemberRequest(
        String nickname
) {
}
