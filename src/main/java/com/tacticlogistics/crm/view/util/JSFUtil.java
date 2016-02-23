package com.tacticlogistics.crm.view.util;

import com.tacticlogistics.crm.view.security.UserData;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;

public class JSFUtil {

    public static String role;
    public static Integer idCliente = 8;

    public static void addInfo(String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, title, message));
    }

    public static void addInfo(String text) {
        String title = JSFUtil.getMessage("msg_info");
        addInfo(title, text);
    }

    public static void addWarn(String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, title, message));
    }

    public static void addWarn(String message) {
        String title = JSFUtil.getMessage("msg_warn");
        addWarn(title, message);
    }

    public static void addError(String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }

    public static void addError(String message) {
        String title = JSFUtil.getMessage("msg_error");
        addError(title, message);
    }

    public static void addFatal(String title, String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, title, message));
    }

    public static String getMessage(String resourceBundleKey) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("resources",
                locale);
        return bundle.getString(resourceBundleKey);
    }

    public static String getMessage(String resourceBundleKey, String paramValue) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("resources",
                locale);
        String msgValue = bundle.getString(resourceBundleKey);
        MessageFormat messageFormat = new MessageFormat(msgValue);
        Object[] args = {paramValue};
        return messageFormat.format(args);
    }

    public static String getMessage(String resourceBundleKey, String paramValue1, String paramValue2) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("com.tacticlogistics.clients.view.resources.resources",
                locale);
        String msgValue = bundle.getString(resourceBundleKey);
        MessageFormat messageFormat = new MessageFormat(msgValue);
        Object[] args = {paramValue1, paramValue2};
        return messageFormat.format(args);
    }

    public static boolean isEmptyOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String formatDate(Date date, String format) {
        String stringDate;
        DateFormat formatter;
        formatter = new SimpleDateFormat(format);
        stringDate = formatter.format(date);
        return stringDate;
    }

    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context,
                "#{" + beanName + "}", Object.class);
    }

    public static SelectItem[] getSelectItems(List<?> entities,
            boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem(null, "Seleccione");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFromat) {
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static String dateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String reportDate = df.format(date);
        return reportDate;
    }

    public static Date stringToDate(String dateInString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(dateInString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static String toUpperCaseFirst(String value) {
        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    public static UserData getCurrentUser() {
        UserData userdata;
        try {
            userdata = (UserData) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        } catch (Exception ex) {
            userdata = new UserData();
        }
        return userdata;
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
        ExternalContext ectx = FacesContext.getCurrentInstance()
                .getExternalContext();
        HttpSession session = (HttpSession) ectx.getSession(false);
        session.invalidate();
    }

    public static String getMD5(String text) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes("UTF-8"), 0, text.length());
            byte[] bt = md.digest();
            BigInteger bi = new BigInteger(1, bt);
            md5 = bi.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(JSFUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5;
    }

}
