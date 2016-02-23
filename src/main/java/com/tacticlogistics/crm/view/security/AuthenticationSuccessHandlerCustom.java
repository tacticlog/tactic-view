package com.tacticlogistics.crm.view.security;

import com.tacticlogistics.crm.view.util.JSFUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessHandlerCustom implements
        AuthenticationSuccessHandler {

    public AuthenticationSuccessHandlerCustom() {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication arg2)
            throws IOException, ServletException {
        String paginaPorDefecto = JSFUtil.getCurrentUser()
                .getPagePorOmision();
        response.sendRedirect(response.encodeRedirectURL(paginaPorDefecto));
    }

}
