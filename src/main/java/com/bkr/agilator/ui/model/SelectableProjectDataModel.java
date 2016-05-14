/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui.model;

import com.bkr.agilator.dao.ProjectDAO;
import com.bkr.agilator.dao.ProjectDAOLocal;
import com.bkr.agilator.entity.Project;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Named(value = "selectableProjectDataModel")
@RequestScoped
public class SelectableProjectDataModel implements SelectableDataModel<Project>{

    ProjectDAOLocal dao;
    
    public  SelectableProjectDataModel(){
    }
    
    @Override
    public Object getRowKey(Project project) {
        return project.getId();
    }

    @Override
    public Project getRowData(String id) {
        try{
            return (Project) dao.find(Long.valueOf(id));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Could not find Data with Id "+id));
            return null;
        }
    }
    
}
