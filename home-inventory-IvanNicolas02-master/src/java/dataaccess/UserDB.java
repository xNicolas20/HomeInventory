/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Items;
import models.Users;

/**
 *
 * @author ivanc
 */
public class UserDB {
    
    public List<Users> getAll(){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        }
        finally{
            em.close();
        }
    }
    
    public Users getUser(String username){
        EntityManager em = DBUtil.getEmFactory().createEntityManager(); 
        
        try{
            Users resultUser = em.find(Users.class, username);
            
            return resultUser;
        } finally{
            em.close();
        }
    }
    
    public void insert(Users user){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
    public void delete(Users user){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {        
            transaction.begin();
            em.remove(em.merge(user));
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
    public void update(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
}
