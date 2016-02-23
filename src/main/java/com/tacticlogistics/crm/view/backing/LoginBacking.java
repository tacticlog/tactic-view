package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.view.security.UserData;
import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.tacticlogistics.crm.view.util.JSFUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Named
@Scope("session")
public class LoginBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String loginAction() {
        String page = null;

        AuthenticationManager authenticationManager = (AuthenticationManager) JSFUtil
                .findBean("authenticationManager");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                this.username, this.password);

        try {
            Authentication authenticationResponseToken = authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(
                    authenticationResponseToken);

            if (authenticationResponseToken.isAuthenticated()) {
                this.password = null;
                page = ((UserData) authenticationResponseToken.getPrincipal())
                        .getPagePorOmision() + "?faces-redirect=true";
            }

        } catch (BadCredentialsException | LockedException | DisabledException | AuthenticationServiceException | NullPointerException ex) {
            Logger.getLogger(LoginBacking.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.addWarn(ex.getMessage());
        }

        return page;
    }

    public String getUserNamePrincipal() {
        return JSFUtil.getCurrentUser().getUsuarios().getUsuario();
    }

    public String logout() {
        this.username = null;
        JSFUtil.logout();
        return "/public/login.jsf?faces-redirect=true";
    }

}
