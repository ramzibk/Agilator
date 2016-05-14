/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui.converter;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@FacesConverter("ui.LocalDateTimeConverter")
public class LocalDateTimeConverter implements Converter{

    Locale locale; 
    
    public LocalDateTimeConverter(Locale locale){
        this.locale = locale;
    }
    
    public LocalDateTimeConverter(){
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        LocalDateTime ldt = null;
        try{
            ldt = LocalDateTime.parse(string);
        }catch(DateTimeParseException e){
            FacesContext.getCurrentInstance().getMessageList().add(new FacesMessage(string+" is not a valid Date and Time Format"));
        }
        return ldt;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        DateTimeFormatter formatter;
        if(locale == null)
            formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss");
        else
            formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss",locale);
        LocalDateTime ldt = (LocalDateTime)o;
        String format = null;
        try{
            format = ldt.format(formatter);
            return format;
        }catch(DateTimeException e){
            FacesContext.getCurrentInstance().getMessageList().add(new FacesMessage(ldt+" could not be formatted"));
        }
        return format;
    }
    
}
