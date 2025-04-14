package kr.co.wikibook.gallery.member.service;

import kr.co.wikibook.gallery.member.entity.Member;
import kr.co.wikibook.gallery.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import kr.co.wikibook.gallery.common.util.HashUtils;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String loginId, String loginPw) {
        String loginPwSalt = HashUtils.generateSalt(16);

        String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

        memberRepository.save(new Member(name, loginId, loginPwSalted, loginPwSalt));
    }

    public Member find(String loginId, String loginPw) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            String loginPwSalt = memberOptional.get().getLoginPwSalt();

            String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

            if (member.getLoginPw().equals(loginPwSalted)) {
                return member;
            }
        }

        return null;
    }

    @Override
    public Member find(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }
}
