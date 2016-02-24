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
import javax.persistence.Transient;

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
    protected String title = "";
    
    /**
     * description is a long text value with maximum 400 characters
     */
    protected String description = "";
    
    /**
     * startTime is a local date-time value set automatically on user request
     */
    protected LocalDateTime startTime = null;
    
    /**
     * endTime is a local date-time value set automatically on user request
     */
    protected LocalDateTime endTime = null;
    
    /**
     * the creation time for history purposes
     */
    protected LocalDateTime creationTime = null;
    
    /**
     * A user estimated duration 
     * This is a positive value representing an amount of time in days
     * The real duration can be calculated using startTime and endTime fields
     */
    protected int duration = 0; // estimated duration in number days

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
    
    @Transient
    public boolean isStarted(){
        return this.startTime != null;
    }

    @Column
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Column
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

// </editor-fold>

}
