package org.sopt.member.domain;

import jakarta.persistence.*;
import org.sopt.common.BaseEntity;
import org.sopt.common.exception.CustomException;
import org.sopt.member.exception.MemberErrorCode;

@Entity
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    protected Member() {}

    public Member(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    public void update(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    private static void validate(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(MemberErrorCode.NICKNAME_REQUIRED);
        }
    }
}
