package org.sopt.member.service;

import org.sopt.common.exception.CustomException;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 생성, 수정, 삭제와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    /**
     * 생성 요청을 검증하고 새 회원을 저장합니다.
     *
     * @param request 회원 생성 요청
     * @return 생성된 회원 ID
     */
    public Long createMember(CreateMemberRequest request) {
        if (repository.existsByNickname(request.nickname())) {
            throw new CustomException(MemberErrorCode.NICKNAME_DUPLICATED);
        }
        Member member = new Member(request.nickname());

        Member savedMember = repository.save(member);
        return savedMember.getId();
    }

    /**
     * 지정한 ID의 회원 닉네임을 수정합니다.
     *
     * @param id 수정할 회원 ID
     * @param request 회원 수정 요청
     */
    @Transactional
    public void updateMember(Long id, UpdateMemberRequest request) {
        Member member = getMember(id);
        member.update(request.nickname());
    }

    /**
     * 지정한 ID의 회원을 탈퇴 처리합니다.
     *
     * @param id 삭제할 회원 ID
     */
    @Transactional
    public void deleteMember(Long id) {
        Member member = getMember(id);
        member.delete();
    }

    private Member getMember(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}
