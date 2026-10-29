package org.sopt.member.domain;

import jakarta.persistence.*;
import org.sopt.common.BaseEntity;
import org.sopt.common.exception.CustomException;
import org.sopt.member.exception.MemberErrorCode;

/**
 * 회원의 상태를 나타내는 도메인 객체입니다.
 */
@Entity
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private boolean isDeleted;

    protected Member() {}

    /**
     * 저장 전 회원을 생성합니다.
     *
     * @param nickname 회원 닉네임
     */
    public Member(String nickname) {
        validate(nickname);
        this.nickname = nickname;
        isDeleted = false;
    }

    /**
     * 회원의 닉네임을 수정합니다.
     *
     * @param nickname 변경할 닉네임
     */
    public void update(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    /**
     * 회원을 탈퇴 상태로 변경하고 기존 닉네임을 비활성화합니다.
     */
    public void delete() {
        isDeleted = true;
        nickname = "d_" + nickname;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayNickname() {
        return isDeleted
                ? "탈퇴한 회원"
                : nickname;
    }

    private static void validate(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(MemberErrorCode.NICKNAME_REQUIRED);
        }
    }
}
