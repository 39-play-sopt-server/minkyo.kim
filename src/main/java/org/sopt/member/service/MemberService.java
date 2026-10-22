package org.sopt.member.service;

import org.sopt.common.exception.CustomException;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.CreateMemberRequest;
import org.sopt.member.dto.request.UpdateMemberRequest;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Long createMember(CreateMemberRequest request) {
        Member member = new Member(request.nickname());

        Member savedMember = repository.save(member);
        return savedMember.getId();
    }

    @Transactional
    public void updateMember(Long id, UpdateMemberRequest request) {
        Member member = getMember(id);
        member.update(request.nickname());
    }

    public void deleteMember(Long id) {
        Member member = getMember(id);
        repository.delete(member);
    }

    private Member getMember(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}
