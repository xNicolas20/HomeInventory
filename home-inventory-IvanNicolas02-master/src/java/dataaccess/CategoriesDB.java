/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Categories;
import models.Users;

/**
 *
 * @author ivanc
 */
public class CategoriesDB {
    
    public List<Categories> getAll(){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
            List<Categories> categories = em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
            return categories;
        }
        finally{
            em.close();
        }
    }
    
    public Categories getCategory(int categoryID){
        EntityManager em = DBUtil.getEmFactory().createEntityManager(); 
        try{
            Categories category = em.find(Categories.class, categoryID);
            
            return category;
        } finally{
            em.close();
        }
    }
    
    public void addCategory(Categories category){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(category);
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
    public void editCategory(Categories category){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.merge(category);
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
}
