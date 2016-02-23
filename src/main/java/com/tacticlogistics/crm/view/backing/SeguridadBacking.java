/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.bo.SeguridadBO;
import com.tacticlogistics.crm.model.entities.app.Menus;
import com.tacticlogistics.crm.model.entities.app.Roles;
import com.tacticlogistics.crm.model.entities.app.RolesMenus;
import com.tacticlogistics.crm.model.entities.app.Usuarios;
import com.tacticlogistics.crm.model.entities.app.UsuariosRoles;
import com.tacticlogistics.crm.view.util.DataModelCustom;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author csarmiento
 */
@Named
@Scope("view")
public class SeguridadBacking implements Serializable {

    @Inject
    private SeguridadBO seguridadBO;

    private List<Roles> rolesList;
    private DataModel<Roles> rolesModel;
    private Roles rolesSelected;
    private List<Menus> listMenusSource;
    private List<Menus> listMenusTarget;
    private DualListModel<Menus> listMenus;

    private List<Usuarios> usuariosList;
    private DataModel<Usuarios> usuariosModel;
    private Usuarios usuariosSelected;
    private List<Roles> listRolesSource;
    private List<Roles> listRolesTarget;
    private DualListModel<Roles> duaListRoles;

    private String clave;

    @PostConstruct
    public void init() {
        try {
            rolesList = seguridadBO.getListRoles();
            rolesModel = new DataModelCustom<>(rolesList);
            rolesSelected = new Roles();

            usuariosList = seguridadBO.getListUsuarios();
            usuariosModel = new DataModelCustom<>(usuariosList);
            usuariosSelected = new Usuarios();

            listMenusSource = seguridadBO.getListMenusActivos();
            listMenus = new DualListModel<>(listMenusSource, new LinkedList<>());

            listRolesSource = seguridadBO.getListRolesActivos();
            duaListRoles = new DualListModel<>(listRolesSource, new LinkedList<>());
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public DataModel<Roles> getRolesModel() {
        return rolesModel;
    }

    public void setRolesModel(DataModel<Roles> rolesModel) {
        this.rolesModel = rolesModel;
    }

    public Roles getRolesSelected() {
        return rolesSelected;
    }

    public void setRolesSelected(Roles rolesSelected) {
        this.rolesSelected = rolesSelected;
    }

    public List<Menus> getListMenusSource() {
        return listMenusSource;
    }

    public void setListMenusSource(List<Menus> listMenusSource) {
        this.listMenusSource = listMenusSource;
    }

    public List<Menus> getListMenusTarget() {
        return listMenusTarget;
    }

    public void setListMenusTarget(List<Menus> listMenusTarget) {
        this.listMenusTarget = listMenusTarget;
    }

    public DualListModel<Menus> getListMenus() {
        return listMenus;
    }

    public void setListMenus(DualListModel<Menus> listMenus) {
        this.listMenus = listMenus;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public DataModel<Usuarios> getUsuariosModel() {
        return usuariosModel;
    }

    public void setUsuariosModel(DataModel<Usuarios> usuariosModel) {
        this.usuariosModel = usuariosModel;
    }

    public Usuarios getUsuariosSelected() {
        return usuariosSelected;
    }

    public void setUsuariosSelected(Usuarios usuariosSelected) {
        this.usuariosSelected = usuariosSelected;
    }

    public List<Roles> getListRolesSource() {
        return listRolesSource;
    }

    public void setListRolesSource(List<Roles> listRolesSource) {
        this.listRolesSource = listRolesSource;
    }

    public List<Roles> getListRolesTarget() {
        return listRolesTarget;
    }

    public void setListRolesTarget(List<Roles> listRolesTarget) {
        this.listRolesTarget = listRolesTarget;
    }

    public DualListModel<Roles> getDuaListRoles() {
        return duaListRoles;
    }

    public void setDuaListRoles(DualListModel<Roles> duaListRoles) {
        this.duaListRoles = duaListRoles;
    }

    public void changeAction(ActionEvent event) {
        try {
            Usuarios usuarios = JSFUtil.getCurrentUser().getUsuarios();
            String md5 = JSFUtil.getMD5(clave);
            usuarios.setClave(md5);
            seguridadBO.save(usuarios);
            JSFUtil.addInfo("Transacción Exitosa!");
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.addInfo(ex.getMessage());
        }
    }

    public void newRolesAction(ActionEvent event) {
        try {
            rolesSelected = new Roles();
            listMenusSource = seguridadBO.getListMenusActivos();
            listMenus = new DualListModel<>(listMenusSource, new LinkedList<>());
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newUsuariosAction(ActionEvent event) {
        try {
            usuariosSelected = new Usuarios();
            listRolesSource = seguridadBO.getListRolesActivos();
            duaListRoles = new DualListModel<>(listRolesSource, new LinkedList<>());
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveRolesAction(ActionEvent event) {
        try {
            if (listMenus.getTarget().isEmpty()) {
                JSFUtil.addWarn("Debe seleccionar al menos un Menú!");
            } else {
                seguridadBO.execute("DELETE RolesMenus o WHERE o.roles.id = " + rolesSelected.getId());

                rolesSelected.setRol(rolesSelected.getRol().toUpperCase());
                rolesSelected.setFechaActualizacion(new Date());
                rolesSelected.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());

                List<RolesMenus> rolesMenuses = new LinkedList<>();
                listMenus.getTarget().stream().forEach((menu) -> {
                    RolesMenus rm = new RolesMenus();
                    rm.setMenus(menu);
                    rm.setRoles(rolesSelected);
                    rolesMenuses.add(rm);
                });

                rolesSelected.setRolesMenuses(rolesMenuses);
                seguridadBO.save(rolesSelected);
                rolesList = seguridadBO.getListRoles();
                rolesModel = new DataModelCustom<>(rolesList);
                JSFUtil.addInfo("Transacción Exitosa!");
            }
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.addInfo(ex.getMessage());
        }
    }

    public void saveUsuariosAction(ActionEvent event) {
        try {
            if (duaListRoles.getTarget().isEmpty()) {
                JSFUtil.addWarn("Debe seleccionar al menos un Rol!");
            } else {
                seguridadBO.execute("DELETE UsuariosRoles o WHERE o.usuarios.id = " + usuariosSelected.getId());

                usuariosSelected.setUsuario(usuariosSelected.getUsuario().toLowerCase());
                usuariosSelected.setEmail(usuariosSelected.getEmail().toLowerCase());
                usuariosSelected.setFechaActualizacion(new Date());
                usuariosSelected.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                if (usuariosSelected.getId() == null) {
                    usuariosSelected.setClave(JSFUtil.getMD5("12345"));
                }

                List<UsuariosRoles> usuariosRoleses = new LinkedList<>();
                duaListRoles.getTarget().stream().forEach((roles) -> {
                    UsuariosRoles rm = new UsuariosRoles();
                    rm.setUsuarios(usuariosSelected);
                    rm.setRoles(roles);
                    rm.setFechaActualizacion(new Date());
                    rm.setUsuarioActualizacion(JSFUtil.getCurrentUser().getUsername());
                    usuariosRoleses.add(rm);
                });

                usuariosSelected.setUsuariosRoleses(usuariosRoleses);
                seguridadBO.save(usuariosSelected);
                usuariosList = seguridadBO.getListUsuarios();
                usuariosModel = new DataModelCustom<>(usuariosList);
                JSFUtil.addInfo("Transacción Exitosa!");
            }
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
            JSFUtil.addInfo(ex.getMessage());
        }
    }

    public void selectDetailAction() {
        try {
            listMenusTarget = seguridadBO.getListMenusPorRoles(rolesSelected);
            List<Menus> result = new LinkedList<>();
            for (Menus row : listMenusSource) {
                boolean exits = false;
                for (Menus avb : listMenusTarget) {
                    if (avb.getId().intValue() == row.getId().intValue()) {
                        exits = true;
                        break;
                    }
                }

                if (!exits) {
                    result.add(row);
                }
            }

            listMenus = new DualListModel<>(result, listMenusTarget);
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectUsuariosDetailAction() {
        try {
            listRolesTarget = seguridadBO.getListRolesPorUsuarios(usuariosSelected);
            List<Roles> result = new LinkedList<>();
            for (Roles row : listRolesSource) {
                boolean exits = false;
                for (Roles avb : listRolesTarget) {
                    if (avb.getId().intValue() == row.getId().intValue()) {
                        exits = true;
                        break;
                    }
                }

                if (!exits) {
                    result.add(row);
                }
            }

            duaListRoles = new DualListModel<>(result, listRolesTarget);
        } catch (Exception ex) {
            Logger.getLogger(SeguridadBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
