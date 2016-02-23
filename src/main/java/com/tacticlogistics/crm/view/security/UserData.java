package com.tacticlogistics.crm.view.security;

import com.tacticlogistics.crm.model.entities.app.Menus;
import com.tacticlogistics.crm.model.entities.app.RolesMenus;
import com.tacticlogistics.crm.model.entities.app.Usuarios;
import com.tacticlogistics.crm.model.entities.app.UsuariosRoles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserData implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean enabled;
    private List<Menus> listPaginas;
    private String pagePorOmision;
    private Usuarios usuarios;
    private List<UsuariosRoles> listUsuariosRoles;
    private List<RolesMenus> listRolesMenus;

    public UserData() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        listUsuariosRoles.stream().forEach((rolesMenus) -> {
            roles.add(new SimpleGrantedAuthority(rolesMenus.getRoles().getRol()));
        });
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public List<Menus> getListPaginas() {
        listPaginas = new LinkedList<>();
        if (listRolesMenus != null) {
            listRolesMenus.stream().forEach((rm) -> {
                listPaginas.add(rm.getMenus());
            });
        }
        return listPaginas;
    }

    public void setListPaginas(List<Menus> listPaginas) {
        this.listPaginas = listPaginas;
    }

    public String getPagePorOmision() {
        return pagePorOmision;
    }

    public void setPagePorOmision(String pagePorOmision) {
        this.pagePorOmision = pagePorOmision;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuariosRoles> getListUsuariosRoles() {
        return listUsuariosRoles;
    }

    public void setListUsuariosRoles(List<UsuariosRoles> listUsuariosRoles) {
        this.listUsuariosRoles = listUsuariosRoles;
    }

    public List<RolesMenus> getListRolesMenus() {
        return listRolesMenus;
    }

    public void setListRolesMenus(List<RolesMenus> listRolesMenus) {
        this.listRolesMenus = listRolesMenus;
    }

}
