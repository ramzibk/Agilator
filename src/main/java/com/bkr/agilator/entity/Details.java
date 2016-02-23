/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@MappedSuperclass
public abstract class Details implements Serializable{

    protected Long id;

    /**
     * title is a text value with maximum 60 characters
     */
    protected String title;
    
    /**
     * description is a long text value with maximum 400 characters
     */
    protected String description;
    
    /**
     * startTime is a local date-time value set automatically on user request
     */
    protected LocalDateTime startTime;
    
    /**
     * endTime is a local date-time value set automatically on user request
     */
    protected LocalDateTime endTime;
    
    /**
     * A user estimated duration 
     * This is a positive value representing an amount of time in days
     * The real duration can be calculated using startTime and endTime values
     */
    protected int duration; // estimated duration in number days

// <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    @Column
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Column
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

// </editor-fold>

}
