package org.sopt.auth.service;

import org.sopt.common.jwt.JwtProvider;
import io.jsonwebtoken.JwtException;
import org.sopt.auth.dto.TokenPair;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.common.exception.CustomException;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public Long signup(String email, String password, String nickname) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(AuthErrorCode.EMAIL_DUPLICATED);
        }

        Member member = new Member(email, passwordEncoder.encode(password), nickname);
        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public TokenPair login(String email, String password) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        return issueTokenPair(member.getId());
    }

    @Transactional(readOnly = true)
    public TokenPair reissue(String refreshToken) {
        Long memberId;

        try {
            memberId = jwtProvider.getMemberIdFromRefreshToken(refreshToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        memberRepository.findByIdAndIsDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN));

        return issueTokenPair(memberId);
    }

    private TokenPair issueTokenPair(Long memberId) {
        return new TokenPair(
                jwtProvider.createAccessToken(memberId),
                jwtProvider.createRefreshToken(memberId)
        );
    }
}
