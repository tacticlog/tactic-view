package com.tacticlogistics.crm.view.security;

import com.tacticlogistics.crm.model.bo.SeguridadBO;
import com.tacticlogistics.crm.model.entities.app.Usuarios;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceCustom implements UserDetailsService {

    @Resource
    private SeguridadBO seguridadBO;

    public UserDetailsServiceCustom() {

    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserData userData = null;
        Usuarios usuarios = null;
        try {
            usuarios = seguridadBO.getUsuariosPorEmail(email);
        } catch (Exception ex) {
            Logger.getLogger(UserDetailsServiceCustom.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuarios != null) {
            if (!usuarios.getActivo()) {
                throw new DisabledException("Usuario Inactivo");
            }
            userData = new UserData();
            userData.setUsername(email);
            userData.setPassword(usuarios.getClave());
            userData.setEnabled(usuarios.getActivo());
            userData.setUsuarios(usuarios);
            userData.setPagePorOmision("/dashboard/dashboard.jsf");
        } else {
            throw new BadCredentialsException("Usuario o Clave Inválida");
        }
        return userData;
    }

}
