/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bkr.agilator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ramzi Ben Khedhiri <bk.ramzi@gmail.com>
 * @param <T>
 */
public abstract class AbstractDAO <T> implements I_DAO<T>{
    
    T entity;
    Class<T> type;
    
    @PersistenceContext(unitName = "Agilator_PU")
    EntityManager em;

    protected AbstractDAO(T entity){
        this.entity = entity;
        System.err.println("T entity: "+entity);
        type = (Class<T>) entity.getClass();
        System.err.println("Class<T>: "+type);
    }
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void insert(T o){
        em.persist(o);
    }

    @Override
    public  T find(Long id){
        return (T)em.find(type, id);
    }

    @Override
    public T merge(T o) {
        return em.merge(o);
    }

    @Override
    public void delete(T o) {
        em.remove(o);
    }

    @Override
    public List<T> findAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);
        TypedQuery<T> query = em.createQuery(cq);
        return query.getResultList();
    }
    
    public void flush(){
        em.flush();
    }
}
