/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.dao;

import com.bkr.agilator.entity.Project;
import javax.ejb.Stateless;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
@Stateless(name = "projectDAO")
public class ProjectDAO extends AbstractDAO<Project> implements ProjectDAOLocal<Project> {

    public ProjectDAO() {
        super(new Project());
    }
    
}
