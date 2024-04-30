package xyz.silencelurker.project.deploy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import xyz.silencelurker.project.deploy.entity.account.AdminAccount;
import xyz.silencelurker.project.deploy.entity.account.WorkAccount;
import xyz.silencelurker.project.deploy.service.IAccountInfoService;

import static xyz.silencelurker.project.deploy.service.tool.TokenUtil.*;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Silence_Lurker
 */
@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final String ADMIN_VALUE = "admin";
    private static final String WORK_VALUE = "work";

    private static final String ROLE_KEY_VALUE = "role";

    protected boolean checkAdminAccount(String token) {
        var accountInfo = decodeToken(token);
        if (accountInfo.get(ROLE_KEY_VALUE).equals(ADMIN_VALUE)) {
            return true;
        }
        return false;
    }

    protected boolean checkAccount(String token) {
        var accountInfo = decodeToken(token);
        if (accountInfo.get(ROLE_KEY_VALUE).equals(WORK_VALUE) || accountInfo.get(ROLE_KEY_VALUE).equals(ADMIN_VALUE)) {
            return true;
        }
        return false;
    }

    protected Cookie createCookie(String token) {
        var cokie = new Cookie("token", URLEncoder.encode(token, Charset.availableCharsets().get("utf-8")));

        cokie.setMaxAge(24 * 60 * 60);
        cokie.setPath("/");
        cokie.setSecure(true);
        cokie.setAttribute("SameSite", "None");

        return cokie;
    }

    @Resource
    private IAccountInfoService accountInfoService;

    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password, HttpServletResponse resp) {
        var account = accountInfoService.login(username, password);

        Map<String, String> tokenInfo = new HashMap<>(5);

        tokenInfo.put("id", account.getId());
        tokenInfo.put("username", username);
        tokenInfo.put(ROLE_KEY_VALUE, "work");
        tokenInfo.put("createTime", System.currentTimeMillis() + "");
        tokenInfo.put("score", account.getScore() + "");

        var token = encodeToken(tokenInfo);

        resp.addCookie(createCookie(token));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(String username, String password, HttpServletResponse resp) {
        var account = accountInfoService.adminLogin(username, password);

        Map<String, String> tokenInfo = new HashMap<>(5);

        tokenInfo.put("id", account.getId());
        tokenInfo.put("username", username);
        tokenInfo.put(ROLE_KEY_VALUE, "admin");
        tokenInfo.put("createTime", System.currentTimeMillis() + "");
        tokenInfo.put("score", account.getScore() + "");

        var token = encodeToken(tokenInfo);

        resp.addCookie(createCookie(token));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(WorkAccount newWorkAccount, @CookieValue String token) {
        if (checkAdminAccount(token)
                && accountInfoService.findByWorkAccountUsername(newWorkAccount.getUsername()) == null) {
            accountInfoService.register(newWorkAccount);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/adminRegister")
    public ResponseEntity<?> adminRegister(AdminAccount newAdminAccount, @CookieValue String token) {
        if (checkAdminAccount(token)
                && accountInfoService.findByAdminAccountUsername(newAdminAccount.getUsername()) == null) {
            accountInfoService.adminRegister(newAdminAccount);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/initAdminAccount")
    public ResponseEntity<?> initAdminAccount(AdminAccount newAdminAccount) {
        if (accountInfoService.findAll().size() == 0) {
            accountInfoService.adminRegister(newAdminAccount);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody WorkAccount newWorkAccount, @CookieValue String token) {
        boolean sameAccount = (checkAccount(token)
                && decodeToken(token).get("username").equals(newWorkAccount.getUsername()));
        if (sameAccount || checkAdminAccount(token)) {
            accountInfoService.update(newWorkAccount);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String id, @CookieValue String token) {
        if (checkAdminAccount(token)) {
            accountInfoService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@CookieValue String token) {
        if (checkAdminAccount(token)) {
            return ResponseEntity.ok(accountInfoService.findAll());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
