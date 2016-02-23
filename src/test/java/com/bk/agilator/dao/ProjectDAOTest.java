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
    static ProjectDAO dao;
    Project project;
    
    public ProjectDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        dao = new ProjectDAO();
        em = HibernateUtil.getEntityManager();
        dao.setEntityManager(em);
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
        dao.insert(project);
        assertNotNull(project.getId());
        assertNotNull(task.getId());
    }
    
    @Test
    public void testUpdate(){
        // insert a project
        dao.insert(project);
        Long id = project.getId();
       
        // modify project.title
        project.setTitle("TestProject");
        // modify project.tasks
        Task task = new Task();
        project.getTasks().add(task);
        // update project
        dao.merge(project);
        
        // test update
        Project entity = (Project)dao.find(id);
        assertEquals("TestProject", entity.getTitle());
        assertTrue(entity.getTasks().size() >= 1);
    }
    
    @Test
    public void testDelete(){
        Task task = new Task();
        project.getTasks().add(task);
        
        // insert project with task list
        dao.insert(project);
        Long id = project.getId();
        Long task_id = task.getId();
        
        // deleted project
        dao.delete(project);
        
        // find deleted project
        Project p = dao.find(id);
        assertNull(p);
        
        // Initialize taskDAO
        TaskDAO taskDAO = new TaskDAO();
        taskDAO.setEntityManager(em);
        // find deleted project.task
        Task t = taskDAO.find(task_id);
        // assert not found
        assertNull(t);
    }
    
    @Test 
    public void testFind(){
        dao.insert(project);
        Project p = (Project) dao.find(project.getId());
        assertEquals(project, p);
        
    }
    
    @Test
    public void testFindAll(){
        Project a = new Project();
        Project b = new Project();
        dao.insert(a);
        dao.insert(b);
        List<Project> list;
        list = dao.findAll();
        assertTrue(list.size()>= 2);
    }
}
