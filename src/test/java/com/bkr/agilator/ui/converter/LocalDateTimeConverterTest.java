/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui.converter;

import com.bkr.agilator.ui.converter.LocalDateTimeConverter;
import com.bkr.agilator.ui.converter.LocalDateTimeConverter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.ejb.Local;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class LocalDateTimeConverterTest {
    
    public LocalDateTimeConverterTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of getAsObject method, of class LocalDateTimeConverter.
     */
    @Test
    public void testGetAsObject() {
        FacesContext fc = null;
        UIComponent uic = null;
        String string = "1999-12-30T23:59:59";
        LocalDateTimeConverter instance = new LocalDateTimeConverter();
        Object expResult = LocalDateTime.of(1999, Month.DECEMBER, 30, 23, 59, 59);
        Object result = instance.getAsObject(fc, uic, string);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAsString method, of class LocalDateTimeConverter.
     */
    @Test
    public void testGetAsString() {
        FacesContext fc = null;
        UIComponent uic = null;
        LocalDateTime o = LocalDateTime.of(1999, Month.DECEMBER, 30, 23, 59, 59);
        LocalDateTimeConverter instance = new LocalDateTimeConverter(Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault());
        String month = formatter.format(o);
        String expResult = "30 "+month+" 1999, 23:59:59";
        String result = instance.getAsString(fc, uic, o);
        assertEquals(expResult, result);
    }
    
}
