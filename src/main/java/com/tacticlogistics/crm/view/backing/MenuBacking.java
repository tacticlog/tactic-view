/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tacticlogistics.crm.view.backing;

import com.tacticlogistics.crm.model.entities.app.Menus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author csarmiento
 */
@Named
@Scope("session")
public class MenuBacking extends BaseBacking {

    private MenuModel model;
    private List<Menus> listMenu;
    private String title;

    public MenuBacking() {

    }

    @PostConstruct
    public void init() {
        if (model == null) {
            title = "Dashboard";
            model = new DefaultMenuModel();
            listMenu = getMenus();
            for (Menus menus : listMenu) {
                if (menus.getMenus() == null) {
                    DefaultSubMenu submenu = new DefaultSubMenu();
                    submenu.setLabel(menus.getNombre());
                    submenu.setIcon(menus.getIcono());
                    boolean isMenu = true;
                    for (Menus children : listMenu) {
                        if ((children.getMenus() != null)
                                && (Objects.equals(children.getMenus().getId(), menus
                                        .getId()))) {
                            DefaultMenuItem menuItem = new DefaultMenuItem();
                            menuItem.setValue(children.getNombre());
                            menuItem.setIcon(children.getIcono());
                            List<String> params = new ArrayList<>();
                            params.add(children.getPagina());
                            menuItem.setParams(new HashMap<>());
                            menuItem.getParams().put("pagina", params);
                            menuItem.setAjax(true);
                            menuItem.setCommand("#{menuBacking.goPageAction}");
                            submenu.addElement(menuItem);
                            isMenu = false;
                        }
                    }
                    if (isMenu) {
                        DefaultMenuItem menuItem = new DefaultMenuItem();
                        menuItem.setValue(menus.getNombre());
                        menuItem.setIcon(menus.getIcono());
                        List<String> params = new ArrayList<>();
                        params.add(menus.getPagina());
                        menuItem.setParams(new HashMap<>());
                        menuItem.getParams().put("pagina", params);
                        menuItem.setAjax(true);
                        menuItem.setCommand("#{menuBacking.goPageAction}");
                        model.addElement(menuItem);
                    } else {
                        model.addElement(submenu);
                    }

                }
            }
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public List<Menus> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<Menus> listMenu) {
        this.listMenu = listMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void goPageAction(MenuActionEvent event) {
        MenuItem menuItem = event.getMenuItem();
        String page = (String) menuItem.getParams().get("pagina").get(0);
        if (page != null) {
            try {
                this.title = menuItem.getValue().toString();
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("../" + page);
            } catch (IOException ex) {
                Logger.getLogger(BaseBacking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String goCambiarClave() {
        this.title = "Cambiar Clave";
        return "/seguridad/cambiarClave.jsf?faces-redirect=true";
    }
    
    public String goSeguridad() {
        this.title = "Gestionar Seguridad";
        return "/seguridad/seguridad.jsf?faces-redirect=true";
    }

}
