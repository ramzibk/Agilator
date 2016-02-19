/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity.converter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Handle the convertion of LocalDateTime to and from SQL Timestamp
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Converter( autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp>{

    /**
     * converts a java.time.LocalDateTime to java.sql.Timestamp
     * @param ldTime of type LocalDateTime
     * @return Timestamp
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime ldTime) {
        return (ldTime==null) ? null : Timestamp.valueOf(ldTime);
    }

    /**
     * converts a java.sql.Timestamp to java.time.LocalDateTime
     * @param sqlTime of type Timestamp
     * @return LocaDateTime
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTime) {
        return (sqlTime==null) ? null : sqlTime.toLocalDateTime();
    }
    
}
