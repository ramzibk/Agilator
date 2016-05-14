/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui;

import com.bkr.agilator.dao.TaskDAOLocal;
import com.bkr.agilator.entity.Project;
import com.bkr.agilator.entity.Task;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Named(value = "taskBean")
@javax.enterprise.context.SessionScoped
public class TaskBean implements Serializable{

    private Project selectedProject;
    private List<Task> taskList;  // tasks list associated to the selected Project
    private Task newTask;
    private Task selectedTask;
    private List<Task> selectedTasksList;
    
    @Inject
    ProjectBean projectBean;
    private TaskDAOLocal dao;
    FacesContext facesCtx;
    
    public TaskBean() {
    }
    
    @PostConstruct
    public void init(){
        facesCtx = FacesContext.getCurrentInstance();
        newTask = new Task();
    }
    
    public String submitCreate(){
        newTask.setCreationTime(LocalDateTime.now());
        projectBean.addTask(newTask);
        try {
            dao.merge(newTask);
        } catch (Exception e) {
            facesCtx.addMessage("",
                                new FacesMessage("Task could not be created",e.getMessage()));
            System.err.println(e.getMessage());
        }
        facesCtx.addMessage("", 
                            new FacesMessage("Task created successfully"));
        newTask = new Task();
        return "";
    }
    
    public float getProgress(Task task){
        float progress = 0;
        if(task.getStartTime() == null)
            return 0f;
        if(task.getEndTime() != null)
            return 1f;
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(task.getStartTime(), currentTime);
        System.out.println("duration: "+duration.getSeconds());
        progress = duration.getSeconds()/(float)(task.getDuration()*24*3600);
        return progress;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public TaskDAOLocal getDao() {
        return dao;
    }

    @EJB(beanName = "taskDAO")
    public void setDao(TaskDAOLocal taskDAO) {
        this.dao = taskDAO;
    }
    
    public Task getNewTask() {
        return newTask;
    }
    
    public void setNewTask(Task newTask) {
        this.newTask = newTask;
    }
    
    public Task getSelectedTask() {
        return selectedTask;
    }
    
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
    
    public List<Task> getSelectedTasksList() {
        return selectedTasksList;
    }
    
    public void setSelectedTasksList(List<Task> selectedTasksList) {
        this.selectedTasksList = selectedTasksList;
    }
    
    public List<Task> getTaskList() {
        if(selectedProject == null)
            return null;
        taskList = selectedProject.getTasks();
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }
    
    /**
     * For testing purposes only
     * @param facesCtx 
     */
    public void setFacesContext(FacesContext facesCtx){
        this.facesCtx = facesCtx;
    }
    //</editor-fold>
}
