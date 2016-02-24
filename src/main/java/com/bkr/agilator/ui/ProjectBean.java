/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui;

import com.bkr.agilator.dao.ProjectDAOLocal;
import com.bkr.agilator.entity.Project;
import com.bkr.agilator.entity.Task;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */

@javax.enterprise.context.SessionScoped
@Named(value = "projectBean")
public class ProjectBean implements Serializable {

    List<Project> projectsList;  // Projects list from the database service
    
    List<Project> selectedProjectsList; // references a list of selected projects from a UI
    
    Project selectedProject; // references a selected project from a UI
    
    Project newProject; // reference to a new project
    
    private ProjectDAOLocal dao;  // database service
    
    public ProjectBean() {
    }
    
    @PostConstruct
    public void init(){
    }
    
    public float getProgress(Project project){
        LocalDateTime startTime = project.getStartTime();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime = project.getEndTime();
        int duration_sec;
        float progress;
        
        if(startTime == null){
            return 0.0f;
        }else{
            long amount_sec;
            
            if(endTime == null){
                // duration in seconds beteween the start time until the current time
                amount_sec = Duration.between(startTime, currentTime).getSeconds();
            }else{
                // duration in seconds between the start and the end time
                amount_sec = Duration.between(startTime, endTime).getSeconds();;
            }
            
            duration_sec = project.getDuration()*24*3600; // days*hours*(minutes*seconds)
            if(amount_sec < duration_sec)
                progress = amount_sec / (float)duration_sec;
            else // 100% is maximum value
                progress = 1;
            
            return progress*100;
        }
    }
    
    /**
     * sets the start time for the selected project
     * @param project
     */
    public void start(Project project){
        if(project != null)
            project.setStartTime(LocalDateTime.now());
        dao.merge(project);
    }
    
    /**
     * sets the end time for the selected project
     */
    public void end(){
        if(selectedProject == null)
            return;
        
        // set the endTime of the project
        selectedProject.setEndTime(LocalDateTime.now());
        dao.merge(selectedProject);
    }
    
    /**
     * returns the list of projects from the database service
     * @return a list of projects
     */
    public List<Project> getAllProjects(){ 
        projectsList = dao.findAll();
        return projectsList;
    }
    
    /**
     * handles the submit new project button click event
     * @param event 
     */
    public void submitAdd(ActionEvent event){
        newProject.setCreationTime(LocalDateTime.now());
        dao.insert(newProject);
    }
    
    /**
     * return the tasks associated to the selected project
     * @return a list of tasks
     */
    public List<Task> getProjectTasks(){
        if(selectedProject == null)
            return null;
        return selectedProject.getTasks();
    }
    
    /**
     * adds a task to the selectedProject
     * @param task 
     */
    public void addTask(Task task){
        selectedProject.getTasks().add(task);
        dao.merge(selectedProject);
    }
    
    /**
     * removes a task from the selected project
     * @param task 
     */
    public void removeTask(Task task){
        selectedProject.getTasks().remove(task);
        dao.merge(selectedProject);
        // TODO handle duration change
    }
    
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @EJB(beanName = "projectDAO")
    public void setDao(ProjectDAOLocal dao) { this.dao = dao; }
    public ProjectDAOLocal getDAO() { return dao; }
    
    public Project getNewProject() {
        return newProject; 
    }
    public void setNewProject(Project newProject) { this.newProject = newProject;}
    
    public Project getSelectedProject() { return selectedProject;}
    public void setSelectedProject(Project selectedProject) { this.selectedProject = selectedProject;}
    
//</editor-fold>
    
}
