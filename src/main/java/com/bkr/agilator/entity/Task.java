/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Entity
public class Task extends Details implements Serializable {
    
    private Project project;
    
    public Task(String title, String description, int duration){
        this();
        this.title = title;
        this.duration = duration;
        this.description = description;
    }
    
    public Task(){
        super();
    }
    
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @ManyToOne
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
//</editor-fold>

    @Override
    public String toString() {
        return "com.bkr.agilator.entity.Task[ id=" + id +"]";
    }
    
}
