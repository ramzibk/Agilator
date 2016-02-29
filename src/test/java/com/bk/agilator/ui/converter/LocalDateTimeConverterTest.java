/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.agilator.ui.converter;

import com.bkr.agilator.ui.converter.LocalDateTimeConverter;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class LocalDateTimeConverterTest {
    
    LocalDateTimeConverter converter;
    DateFormatSymbols symbols;
    String month;
    
    public LocalDateTimeConverterTest() {
    }
    
    @Before
    public void setUp() {
        converter = new LocalDateTimeConverter();
        symbols = new DateFormatSymbols(Locale.getDefault(Locale.Category.FORMAT));
        month = symbols.getMonths()[Month.MARCH.getValue()-1];
    }
    
    @Test
    public void testGetAsString(){
        LocalDateTime ldt= LocalDateTime.of(1999, Month.MARCH, 30, 23, 59, 59);
        String actual = converter.getAsString(null, null, ldt);
        String expected = "30 "+month+" 1999, 23:59:59";
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetAsObject(){
        String asString = "1999-03-30T23:59:59";
        LocalDateTime actual = (LocalDateTime)converter.getAsObject(null, null, asString);
        LocalDateTime expected = LocalDateTime.of(1999, Month.MARCH, 30, 23, 59, 59);
        assertEquals(expected, actual);
    }
}