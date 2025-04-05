package kr.co.wikibook.gallery.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.block.service.BlockService;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import kr.co.wikibook.gallery.common.util.TokenUtils;
import kr.co.wikibook.gallery.member.entity.Member;
import kr.co.wikibook.gallery.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class TokenAccountHelper implements AccountHelper {

    private final MemberService memberService;
    private final BlockService blockService;

    private String getAccessToken(HttpServletRequest req) {
        return HttpUtils.getBearerToken(req);
    }

    private String getRefreshToken(HttpServletRequest req) {
        return HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);
    }

    private Integer getMemberId(String token) {
        if (TokenUtils.isValid(token)) {
            Map<String, Object> tokenBody = TokenUtils.getBody(token);
            return (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);
        }

        return null;
    }

    @Override
    public void join(AccountJoinRequest joinReq) {
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());
    }

    @Override
    public String login(AccountLoginRequest loginReq, HttpServletRequest req, HttpServletResponse res) {
        Member member = memberService.find(loginReq.getLoginId(), loginReq.getLoginPw());

        if (member == null) {
            return null;
        }

        Integer memberId = member.getId();

        String accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);

        String refreshToken = TokenUtils.generate(AccountConstants.REFRESH_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.REFRESH_TOKEN_EXP_MINUTES);

        HttpUtils.setCookie(res, AccountConstants.REFRESH_TOKEN_NAME, refreshToken, 0);

        return accessToken;
    }

    @Override
    public Integer getMemberId(HttpServletRequest req) {
        return this.getMemberId(getAccessToken(req));
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest req) {
        if (TokenUtils.isValid(getAccessToken(req))) {
            return true;
        }

        String refreshToken = getRefreshToken(req);

        return TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken);
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {

        String refreshToken = getRefreshToken(req);

        if (refreshToken != null) {
            HttpUtils.removeCookie(res, AccountConstants.REFRESH_TOKEN_NAME);

            if (!blockService.has(refreshToken)) {
                blockService.add(refreshToken);
            }
        }
    }
}
