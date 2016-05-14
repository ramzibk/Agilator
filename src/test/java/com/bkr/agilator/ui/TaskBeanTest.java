/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.ui;

import com.bkr.agilator.entity.Task;
import java.time.LocalDateTime;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class TaskBeanTest {
    
    static TaskBean bean;
    Task task;
   
    public TaskBeanTest() {
    }
    
    @BeforeClass
    public static void init(){
        bean = new TaskBean();
        // mock FacesContext variable
        FacesContext facesCtx = mock(FacesContext.class);
        doNothing().when(facesCtx)
                   .addMessage(any(String.class), any(FacesMessage.class));
        bean.setFacesContext(facesCtx);
    }
       
    @Test
    public void TestGetProgressNotStarted(){
        task = new Task();
        task.setStartTime(null);
        float result = bean.getProgress(task);
        float expected = 0.0f;
        assertEquals(expected, result, 0.0); // if startTime == null, return 0
    }
    
    @Test
    public void TestGetProgressAfterStart(){
        task = new Task();
        task.setDuration(2); // set a duration of 2 days
        task.setStartTime(LocalDateTime.now().minusDays(1)); // set start time one day in advance
        float result = bean.getProgress(task);
        float expected = 0.5f;
        assertEquals(expected, result, 0.1);
    }
    
    @Test
    public void TestGetProgressAfterEnd(){
        task = new Task();
        task.setStartTime(LocalDateTime.now());
        task.setEndTime(LocalDateTime.now());
        // if endTime != null, return 1
        float result = bean.getProgress(task);
        float expected = 1f;
        assertEquals(expected, result, 0);
    }
    
}
