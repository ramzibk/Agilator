/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui;

import com.bkr.agilator.dao.ProjectDAO;
import com.bkr.agilator.dao.ProjectDAOLocal;
import com.bkr.agilator.entity.Project;
import com.bkr.agilator.entity.Task;
import com.bkr.agilator.ui.ProjectBean;
import java.time.LocalDateTime;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class ProjectBeanTest {
    
    static ProjectBean bean;
    static ProjectDAOLocal dao;
    
    public ProjectBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass(){
        bean = new ProjectBean();
        dao = mock(ProjectDAO.class);
        // mock merge method on ProjectDAO
        when(dao.merge(any(Project.class)))
                .then(
                    new Answer<Project>(){
                    @Override
                        public Project answer(InvocationOnMock invocation) throws Throwable {
                            Project project = invocation.getArgumentAt(0, Project.class);
                            if(project.getId() == null )
                                project.setId(1l); // generate and id for the project
                            for(Task task : project.getTasks()){
                                    if(task.getId() == null)
                                        task.setId(1l); // generate an id for the task
                            }
                            return project;
                        }
                    });
        // mock insert method on ProjectDAO
        doAnswer(new Answer<Project>(){
            @Override
            public Project answer(InvocationOnMock invocation) throws Throwable {
                Project project = invocation.getArgumentAt(0, Project.class);
                project.setId(1l); // generate an id for the project
                return project;
            }
            }).when(dao).insert(any(Project.class));
        
        bean.setDao(dao);
        
        // mock FacesContext variable
        FacesContext facesCtx = mock(FacesContext.class);
        doNothing().when(facesCtx)
                   .addMessage(any(String.class), any(FacesMessage.class));
        bean.setFacesContext(facesCtx);
    }
    
    @Before
    public void setUp() {
        bean.setNewProject(new Project());
    }
    
    @After
    public void tearDown() {
    }
    
        
    @Test
    public void testAddTask(){
        Project project = new Project();
        bean.setSelectedProject(project);
        Task task = new Task();
        bean.addTask(task);
        assertTrue(project.getTasks().contains(task));
        assertEquals(project, task.getProject());
    }
    
    @Test
    public void testGetProgress_before_start(){
        Project project = new Project();
        project.setDuration(2);
        bean.setSelectedProject(new Project());
        float progress = bean.getProgress(project);
        assertEquals(0.0f, progress,0.0);
    }
    
    @Test
    public void testGetProgress_after_start(){
        float progress;
        Project project = new Project();
        project.setDuration(2);
        project.setStartTime(LocalDateTime.now().minusDays(1));  // start the project 1 day in advance
        progress = bean.getProgress(project);
        assertEquals(50, progress, 1); // assert progress is 50%
    }
    
    @Test 
    public void testGetProgress_after_end(){
        float progress;
        Project project = new Project();
        project.setDuration(2);
        project.setStartTime(LocalDateTime.now().minusDays(2));  // 2 days forewards
        progress = bean.getProgress(project);
        assertEquals(100.0,progress,0.0);
    }
    
}
