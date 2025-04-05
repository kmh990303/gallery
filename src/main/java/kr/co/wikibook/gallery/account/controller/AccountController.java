package kr.co.wikibook.gallery.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import kr.co.wikibook.gallery.common.util.TokenUtils;
import kr.co.wikibook.gallery.block.service.BlockService;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import kr.co.wikibook.gallery.account.etc.AccountConstants;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {
    private final AccountHelper accountHelper;
    private final BlockService blockService;

    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequest joinReq) {

        if (!StringUtils.hasLength(joinReq.getName()) ||
        !StringUtils.hasLength(joinReq.getLoginId()) || !StringUtils.hasLength(joinReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequest loginReq) {

        if (!StringUtils.hasLength(loginReq.getLoginId()) ||
        !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String output = accountHelper.login(loginReq, req, res);

        if (output == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest req) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(req), HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        accountHelper.logout(req, res);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);

        if (StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);

            Integer memberId = (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);

            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);
        }

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
