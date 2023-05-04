package com.market.list.security.oauth;


import com.market.list.entities.Account;
import com.market.list.enums.Provider;
import com.market.list.exceptions.MarketException;
import com.market.list.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AccountService accountService;

    @Autowired
    public OAuth2LoginSuccessHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        System.out.println(email);

        try {
            Account account = accountService.findAccount(null, email);
            account.setProvider(Provider.GOOGLE);
            accountService.update(account);
        } catch (MarketException e) {
            accountService.createViaOAuth2(email, name, Provider.GOOGLE);
        } finally {
            response.sendRedirect("/api/v1/account/?email=" + email);
        }
    }
}
