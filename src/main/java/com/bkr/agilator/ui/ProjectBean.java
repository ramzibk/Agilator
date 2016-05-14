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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@javax.enterprise.context.SessionScoped
@Named(value = "projectBean")
public class ProjectBean implements Serializable {

    private List<Project> projectsList;
    private Project selectedProject;
    private Project newProject;
    private ProjectDAOLocal dao;

    FacesContext facesCtx;

    public ProjectBean() {
    }

    @PostConstruct
    public void init() {
        facesCtx = FacesContext.getCurrentInstance();
    }

    public String projectsListAction() {
        return "projectsList";
    }

    public String newProjectAction() {
        return "newProject";
    }

    public String tasksListAction() {
        return "projectTasks";
    }

    public String submitEdit() {
        try {
            dao.merge(selectedProject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Long id = selectedProject.getId();
        FacesContext.getCurrentInstance().addMessage("",
                new FacesMessage("Project "+id+" was edited"));
        return "index.xhtml";
    }

    /**
     * create button method
     *
     * @return response page link
     */
    public String submitCreate() {
        try {
            LocalDateTime now = LocalDateTime.now();
            newProject.setCreationTime(now);
            dao.insert(newProject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "index.xhtml";
        } finally {
            newProject = null;
        }
        
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("A new Project was created"));
        return "index.xhtml";
    }

    /**
     * delete button method
     *
     * @return URL of response page
     */
    public String submitDelete() {
        if (selectedProject == null) {
            FacesContext.getCurrentInstance().addMessage("", 
                                                         new FacesMessage("Pleae select a Project"));
            return "";
        }
        Long id = selectedProject.getId();
        try {
            dao.delete(selectedProject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "index.xhtml";
        } finally {
            selectedProject = null;
        }
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Project "+id+" was deleted"));
        return "index.xhtml";
    }

    public String cancelDelete() {
        selectedProject = null;
        return "";
    }

    /**
     * sets the start time for the selected project
     *
     * @return URL of response page
     */
    public String submitStart() {
        if (selectedProject == null) {
            FacesContext.getCurrentInstance().getCurrentInstance().addMessage("",
                                                         new FacesMessage("Please select a Project"));
            return "";
        }
        Long id = selectedProject.getId();
        try {
            selectedProject.setStartTime(LocalDateTime.now());
            dao.merge(selectedProject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "index.xhtml";
        } finally {
            selectedProject = null;
        }
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Project "+id+" started"));
        return "";
    }

    /**
     * sets the end time for the selected project
     *
     * @param URL of response page
     */
    public String submitEnd() {
        if(selectedProject == null){
            FacesContext.getCurrentInstance().addMessage("",
                                                         new FacesMessage("Please selected a Project"));
            return "";
        }
        Long id = selectedProject.getId();
        try {
            selectedProject.setEndTime(LocalDateTime.now());
            dao.merge(selectedProject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "index.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Project "+id+" was ended"));
        return "";
    }

    /**
     * returns the time elapsed time since the start of the project
     *
     * @param project
     * @return a number between 0 and 100
     */
    public float getProgress(Project project) {
        LocalDateTime startTime = project.getStartTime();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime = project.getEndTime();
        int duration_sec;
        float progress;

        if (startTime == null) {
            return 0.0f;
        } else {
            long amount_sec;

            if (endTime == null) {
                // duration in seconds beteween the start time until the current time
                amount_sec = Duration.between(startTime, currentTime).getSeconds();
            } else {
                // duration in seconds between the start and the end time
                amount_sec = Duration.between(startTime, endTime).getSeconds();;
            }

            duration_sec = project.getDuration() * 24 * 3600; // days*hours*(minutes*seconds)
            if (amount_sec < duration_sec) {
                progress = amount_sec / (float) duration_sec;
            } else // 100% is maximum value
            {
                progress = 1;
            }

            return progress * 100;
        }
    }

    /**
     * adds a task to the tasks List of the selected Project
     *
     * @param task a task to add
     *
     */
    public void addTask(Task task) {
        if (selectedProject != null) {
            selectedProject.addTask(task);
        } else {
            facesCtx.addMessage("", new FacesMessage("Please select a Project"));
            return;
        }
    }

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @EJB(beanName = "projectDAO")
    public void setDao(ProjectDAOLocal dao) {
        this.dao = dao;
    }

    public ProjectDAOLocal getDAO() {
        return dao;
    }

    public Project getNewProject() {
        if (newProject == null) {
            newProject = new Project();
        }
        return newProject;
    }

    public void setNewProject(Project newProject) {
        this.newProject = newProject;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    /**
     * get project's list
     *
     * @return a list of projects
     */
    public List<Project> getProjectsList() {
        projectsList = dao.findAll();
        return projectsList;
    }

    public void setProjectsList(List<Project> projectsList) {
        this.projectsList = projectsList;
    }

    public String getState(Project project) {
        if (project == null) {
            return "";
        }
        if (project.getStartTime() == null) {
            return "NEW";
        } else if (project.getEndTime() == null) {
            return "STARTED";
        } else {
            return "DONE";
        }
    }

    /**
     * For testing purposes only
     *
     * @param facesCtx
     */
    public void setFacesContext(FacesContext facesCtx) {
        this.facesCtx = facesCtx;
    }
    //</editor-fold>
}
