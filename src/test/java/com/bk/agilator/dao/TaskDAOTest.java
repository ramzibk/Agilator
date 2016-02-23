/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.agilator.dao;

import Utils.HibernateUtil;
import com.bkr.agilator.dao.TaskDAO;
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
        task = new Task(null, "description", 20);
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
    }
    
    @Test
    public void testInsert(){
        dao.insert(task);
        assertNotNull(task.getId());
    }
    
    @Test
    public void testUpdate(){
        dao.insert(task);
        Long id = task.getId();
       
        // modify task
        task.setDescription("TestTask");
        dao.merge(task);        
        
        // test update
        Task t = (Task)dao.find(id);
        assertEquals("TestTask", t.getDescription());
    }

    @Test
    public void testDelete(){
        dao.insert(task);
        Long id = task.getId();
        dao.delete(task);
        Task t = dao.find(id);
        // assert not found
        assertNull(t);
    }
    
    @Test 
    public void testFind(){
        dao.insert(task);
        Task t = (Task) dao.find(task.getId());
        assertEquals(task, t);
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
    }
}
