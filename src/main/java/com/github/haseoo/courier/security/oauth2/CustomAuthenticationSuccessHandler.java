package com.github.haseoo.courier.security.oauth2;

import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import com.github.haseoo.courier.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.github.haseoo.courier.security.Constants.HOME_URL;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ClientIndividualRepository clientIndividualRepository;

    private final JwtTokenProvider jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map<String, Object> attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        ClientIndividualModel clientIndividualModel = clientIndividualRepository.getByEmailAddress(email);
        String token = jwtTokenUtil.generateTokenToSocialLogin(clientIndividualModel);
        String redirectionUrl = UriComponentsBuilder.fromUriString(HOME_URL)
                .queryParam("auth_token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }

}
