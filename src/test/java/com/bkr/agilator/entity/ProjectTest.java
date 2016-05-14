/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.entity;

import com.bkr.agilator.entity.Project;
import com.bkr.agilator.entity.Task;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class ProjectTest {
    
    private Project project;
    
    public ProjectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        project = new Project();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testHashCode(){
        // if x=y then x.hashCode()=y.hashCode()
        Project x = new Project(1l);
        Project y = new Project(1l);
        assertEquals(x.hashCode(),y.hashCode());
        
        // x#z then x.hashCode()#z.hashCode()
        Project z = new Project(2l);
        assertNotEquals(x.hashCode(), z.hashCode());
    }
    
    @Test
    public void testEquals(){
        // x.equals(x)
        Project x = new Project(1l);
        assertTrue(x.equals(x));
        
        // if x=y then x.equals(y)=true and y.equals(x)=true
        Project y = new Project(1l);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
        
        // if x=y and y=z then x=z
        Project z = new Project(1l);
        assertTrue(x.equals(y) & y.equals(z) & x.equals(z));
        
        // if x#y then x.equals(z)=false and y.equals(x)=false
        Project notx = new Project(2l);
        assertFalse(x.equals(notx));
        assertFalse(notx.equals(x));
        
        // x.equals(null)=false
        assertFalse(x.equals(null));
    }
    
    @Test
    public void testAddTask(){
        Task task = new Task();
        task.setId(new Long(1));
        project.addTask(task);
        assertNotNull(task.getProject());
        assertTrue(project.getTasks().contains(task));
    }
    
    @Test
    public void testRemoveTask(){
        Task task = new Task();
        task.setId(new Long(1));
        project.addTask(task);
        project.removeTask(task);
        assertFalse(project.getTasks().contains(task));
        assertNull(task.getProject());
    }
}
