package com.tacticlogistics.crm.view.security;

import com.tacticlogistics.crm.model.bo.SeguridadBO;
import com.tacticlogistics.crm.model.entities.app.RolesMenus;
import com.tacticlogistics.crm.model.entities.app.UsuariosRoles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AbstractUserDetailsAuthenticationProviderCustom extends
        AbstractUserDetailsAuthenticationProvider {

    @Resource
    private SeguridadBO seguridadBO;
    // Este se utiliza cuando no es encriptado private StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
    private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder(); // Se utliza cuando esta encriptado
    private SaltSource saltSource; // Se utliza cuando esta encriptado
    private UserDetailsServiceCustom userDetailsService;

    public AbstractUserDetailsAuthenticationProviderCustom() {
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {

        Object salt = null;

        if (saltSource != null) {
            salt = saltSource.getSalt(userDetails);
        }

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
                token.getCredentials().toString(), salt)) {
            throw new BadCredentialsException("Clave Inválida");
        } else {
            int error = 0;
            try {
                UserData userData = (UserData) userDetails;
                List<UsuariosRoles> listUsuariosRoles = seguridadBO.getListUsuariosRolesPorUsuarios(userData.getUsuarios());
                if (listUsuariosRoles == null || listUsuariosRoles.isEmpty()) {
                    error = 1;
                } else {
                    userData.setListUsuariosRoles(listUsuariosRoles);
                    List<RolesMenus> listRolesMenus = seguridadBO.getListMenusPorUsuarios(listUsuariosRoles);
                    if (listRolesMenus == null || listRolesMenus.isEmpty()) {
                        error = 2;
                    } else {
                        userData.setListRolesMenus(listRolesMenus);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(AbstractUserDetailsAuthenticationProviderCustom.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (error == 1) {
                throw new BadCredentialsException("El usuario no tiene roles asignados!");
            } else if (error == 2) {
                throw new BadCredentialsException("Los roles no tienen menus asignados!");
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username,
            UsernamePasswordAuthenticationToken arg1)
            throws AuthenticationException {

        UserDetails loadedUser;

        try {
            loadedUser = userDetailsService.loadUserByUsername(username);
        } catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "AuthenticationDao returned null, which is an interface contract violation");
        }

        return loadedUser;
    }

    public UserDetailsServiceCustom getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(
            UserDetailsServiceCustom userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public Md5PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
