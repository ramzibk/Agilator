/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Entity
public class Project extends Details implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private List<Task> tasks;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Project(String title, String description, Long duration){
        this();
        this.title = title;
        this.description = description;
        this.duration = duration;
    }
    
    public Project(){
        tasks = new ArrayList();
    }
//</editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">    

    /**
     * returns associated tasks
     * @return the associated list of tasks
     */
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    public List<Task> getTasks() {
        return this.tasks;
    }
    
    public void setTasks(List tasks) {    
        this.tasks = tasks;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

// </editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bkr.agilator.jpa.Project[ id=" + id +"]";
    }
}
