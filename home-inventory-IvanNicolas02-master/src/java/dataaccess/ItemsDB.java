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
public class ItemsDB {
    
    public List<Items> getAll(){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            List<Items> items = em.createNamedQuery("Items.findAll", Items.class).getResultList();
            return items;
        }
        finally{
            em.close();
        }
    }
    
    public void insert(Items item){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            Users user = item.getOwner();
            transaction.begin();
            user.getItemsList().add(item);
            em.persist(item);
            em.merge(user);
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
    public void delete(Items item){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            Users user = item.getOwner();
            user.getItemsList().remove(item);        
            transaction.begin();
            em.remove(em.merge(item));
            em.merge(user);
            transaction.commit();
        } catch(Exception ex) {
            transaction.rollback();
        }finally {
            em.close();
        }
    }
    
    public void edit(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Users user = item.getOwner();
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
