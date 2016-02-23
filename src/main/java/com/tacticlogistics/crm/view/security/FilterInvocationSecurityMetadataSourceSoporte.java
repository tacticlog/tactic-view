package com.tacticlogistics.crm.view.security;

import com.tacticlogistics.crm.model.entities.app.Menus;
import com.tacticlogistics.crm.view.util.JSFUtil;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class FilterInvocationSecurityMetadataSourceSoporte implements
        FilterInvocationSecurityMetadataSource {
    
    public FilterInvocationSecurityMetadataSourceSoporte() {
    }
    
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        StringBuilder acceso = new StringBuilder();
        FilterInvocation fi = (FilterInvocation) object;
        String pagina = fi.getRequestUrl();
        try {
            if (pagina.contains("jquery") || pagina.contains("primefaces")
                    || pagina.contains("jpg") || pagina.contains("png")
                    || pagina.contains("gif")
                    || pagina.contains("sounds")
                    || pagina.contains("css")
                    || pagina.contains("public")
                    || pagina.contains("javax.faces.resource")) {
                return null;
            }
            
            List<Menus> paginas = JSFUtil.getCurrentUser()
                    .getListPaginas();
            for (Menus pag : paginas) {
                String p = pag.getPagina();
                if (p != null && pagina.contains(p) || pagina.contains("seguridad")) {
                    return null;
                }
            }
            
            acceso.append(pagina);
        } catch (Exception ex) {
            acceso.append(pagina);
        }
        return SecurityConfig.createListFromCommaDelimitedString(acceso
                .toString());
    }
    
    @Override
    public boolean supports(Class<?> cl) {
        return FilterInvocation.class.isAssignableFrom(cl);
    }
    
}
