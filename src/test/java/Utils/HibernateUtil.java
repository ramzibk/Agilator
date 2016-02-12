/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 */
public class HibernateUtil {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    static{
       if(emf == null){
            emf = Persistence.createEntityManagerFactory("Agilator_TEST_PU");
       }
    }
    
    private HibernateUtil(){}
    
    public static EntityManager getEntityManager(){
        if(em == null || !em.isOpen())
            em = emf.createEntityManager();
        return em;
    }
}
