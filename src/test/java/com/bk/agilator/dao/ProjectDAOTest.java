/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.agilator.dao;

import Utils.HibernateUtil;
import com.bkr.agilator.dao.ProjectDAO;
import com.bkr.agilator.dao.TaskDAO;
import com.bkr.agilator.entity.Project;
import com.bkr.agilator.entity.Task;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class ProjectDAOTest {
    
    static EntityManager em;
    static ProjectDAO projectDAO;
    static TaskDAO taskDAO;
    Project project;
;    
    public ProjectDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        projectDAO = new ProjectDAO();
        taskDAO = new TaskDAO();
        em = HibernateUtil.getEntityManager();
        projectDAO.setEntityManager(em);
        taskDAO.setEntityManager(em);
    }
    
    @AfterClass
    public static void tearDownClass() {
        em.close();
    }
    
    @Before
    public void setUp() {
        em.getTransaction().begin();
        project = new Project("title", "description", 20);
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
    }
    
    @Test 
    public void testInsert(){
        Task task = new Task();
        project.getTasks().add(task);
        projectDAO.insert(project);
        assertNotNull(project.getId());
        assertNotNull(task.getId());
    }
    
    @Test
    public void testUpdate(){
        // insert a project
        projectDAO.insert(project);
        Long id = project.getId();
       
        // modify project.title
        project.setTitle("TestProject");
        // modify project.tasks
        Task task = new Task();
        project.getTasks().add(task);
        // update project
        projectDAO.merge(project);
        
        // test update
        Project entity = (Project)projectDAO.find(id);
        assertEquals("TestProject", entity.getTitle());
        assertTrue(entity.getTasks().size() >= 1);
    }
    
    @Test
    public void testDelete(){
        Task task = new Task();
        project.getTasks().add(task);
        
        // insert project with task list
        projectDAO.insert(project);
        Long id = project.getId();
        Long task_id = task.getId();
        
        // deleted project
        projectDAO.delete(project);
        
        // find deleted project
        Project p = projectDAO.find(id);
        assertNull(p);
        
        // find deleted project.task
        Task t = taskDAO.find(task_id);
        // test task not found
        assertNull(t);
    }
    
    @Test 
    public void testFind(){
        projectDAO.insert(project);
        Project p = (Project) projectDAO.find(project.getId());
        assertEquals(project, p);
    }
    
    @Test
    public void testFindAll(){
        Project a = new Project();
        Project b = new Project();
        projectDAO.insert(a);
        projectDAO.insert(b);
        List<Project> list;
        list = projectDAO.findAll();
        assertTrue(list.size()>= 2);
    }
    
}
