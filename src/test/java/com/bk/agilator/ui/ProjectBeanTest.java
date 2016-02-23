/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.agilator.ui;

import com.bkr.agilator.entity.Project;
import com.bkr.agilator.ui.ProjectBean;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class ProjectBeanTest {
    
    Project project;
    ProjectBean bean;
    
    public ProjectBeanTest() {
    }
    
    @Before
    public void setUp() {
        project = new Project();
        bean = new ProjectBean();
        bean.setSelectedProject(project);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testStart(){
        bean.setSelectedProject(project);
        bean.start();
        assertNotNull(project.getStartTime());    
    }
    
    @Test
    public void testEnd(){
        bean.setSelectedProject(project);
        bean.end();
        assertNotNull(project.getEndTime());    
    }
    
    @Test
    public void testGetProgression(){
        float progress;
        bean.setSelectedProject(project);
        
        // test progress before the project starts        
        progress = bean.getProgress(project);
        assertEquals(0.0f, progress,0.0);
        
        // test progress after the project starts
        project.setDuration(2); // 1 day
        // start the project 1 day in advance
        project.setStartTime(LocalDateTime.now().minusDays(1)); 
        progress = bean.getProgress(project);
        assertEquals(50, progress, 1);
        
        // test progress after the project ends
        bean.end();
        progress = bean.getProgress(project);
        assertEquals(100.0,progress,0.0);
    }
    
}
