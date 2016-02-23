/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * 
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Entity
public class Project extends Details implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Task> tasks;
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Project(String title, String description, int duration){
        this();
        this.title = title;
        this.description = description;
        this.duration = duration;
    }
    public Project(Long id){
        this();
        this.id = id;
    }
    public Project(){
        tasks = new ArrayList();
    }
//</editor-fold>

    public void addTask(Task task){
        task.setProject(this);
        tasks.add(task);
    }
    
    public void removeTask(Task task){
        task.setProject(null);
        tasks.remove(task);
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">    
    
    @OneToMany( mappedBy = "project",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    public List<Task> getTasks() {
        return this.tasks;
    }
    
    public void setTasks(List tasks) {    
        this.tasks = tasks;
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
