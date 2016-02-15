/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@MappedSuperclass
public abstract class Details implements Serializable{

    protected String title;
    protected String description;
    protected Date startTime;
    protected Date endTime;
    protected Long duration;
    
// <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        System.err.println("setTitle: "+title);
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        System.err.println("setDescription: "+description);
        this.description = description;
    }
    
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

// </editor-fold>

}
