/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.agilator.ui;

import com.bkr.agilator.dao.ProjectDAO;
import com.bkr.agilator.dao.ProjectDAOLocal;
import com.bkr.agilator.entity.Project;
import com.bkr.agilator.ui.ProjectBean;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.mockito.Mockito.*;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class ProjectBeanTest {
    
    static ProjectBean bean;
    
    public ProjectBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass(){
        bean = new ProjectBean();
        ProjectDAOLocal dao = mock(ProjectDAO.class);
        bean.setDao(dao);
        
        // mocking ProjectDAO EJB
        when(dao.merge(any(Project.class))).thenReturn(new Project());
        //when(dao.insert(any(Project.class)));
        doNothing().when(dao).insert(any());
    }
    
    @Before
    public void setUp() {
        bean.setNewProject(new Project());
        bean.setSelectedProject(new Project());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testSubmitAdd(){
        bean.submitAdd(null);
        assertNotNull(bean.getNewProject());
    }
    
    @Test
    public void testStart(){
        bean.start();
        assertNotNull(bean.getSelectedProject().getStartTime());    
    }
    
    @Test
    public void testEnd(){
        bean.end();
        assertNotNull(bean.getSelectedProject().getEndTime());    
    }
    
    @Test
    public void testGetProgression(){
        float progress;
        // initialize test project
        bean.getSelectedProject()
            .setDuration(2);                                  // duration is 2 day
        
        // testing before the project starts        
        progress = bean.getProgress(bean.getSelectedProject());
        assertEquals(0.0f, progress,0.0);
        
        // testing in the middle the project time
        bean.getSelectedProject()
            .setStartTime(LocalDateTime.now().minusDays(1));  // start the project 1 day in advance
        // get the progress of the project
        progress = bean.getProgress(bean.getSelectedProject());
        // assert progress is 50&
        assertEquals(50, progress, 1);
        
        // test progress when duration exceeded
        bean.getSelectedProject()
            .setStartTime(LocalDateTime.now().minusDays(2));  // 2 days forewards
        progress = bean.getProgress(bean.getSelectedProject());
        assertEquals(100.0,progress,0.0);
    }
    
}
