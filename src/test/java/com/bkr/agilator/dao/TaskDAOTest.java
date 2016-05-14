/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.dao;

import Utils.HibernateUtil;
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
public class TaskDAOTest {
    
    static EntityManager em;
    static TaskDAO dao;
    Task task;
    
    public TaskDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        dao = new TaskDAO();
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
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
    }
    
    @Test
    public void testInsert(){
        Project project = new Project(1l);
        task = new Task(project);
        dao.insert(task);
        assertNotNull(task.getId());
    }
    
    @Test
    public void testUpdate(){
        task = new Task();
        dao.insert(task);
        Long id = task.getId();
       
        // modify the task
        String desc = "new description";
        task.setDescription(desc);
        int duration = 1;
        task.setDuration(duration);
        dao.merge(task);        
        
        // test update
        Task t = (Task)dao.find(id);
        assertEquals(desc, t.getDescription());
        assertEquals(duration, t.getDuration());
    }

    @Test
    public void testDelete(){
        task = new Task();
        dao.insert(task);
        Long id = task.getId();
        // verify task was created
        assertNotNull(id); 
        // delete the task
        dao.delete(task); 
        // try to find it by id
        Task t = dao.find(id);
        // verify task was not found
        assertNull(t); 
    }
    
    @Test 
    public void testFind(){
        task = new Task();
        dao.insert(task);
        // verify task was created
        assertNotNull(task.getId());
        // find the added task
        Task found = (Task) dao.find(task.getId());
        assertEquals(task, found);
    }
    
    @Test
    public void testFindAll(){
        Task a = new Task();
        Task b = new Task();
        dao.insert(a);
        dao.insert(b);
        List<Task> list;
        list = dao.findAll();
        assertTrue(list.size()>= 2);
        assertTrue(list.contains(a));
        assertTrue(list.contains(b));
    }
}
