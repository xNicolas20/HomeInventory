/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Categories;
import models.Items;
import models.Users;

/**
 *
 * @author ivanc
 */
public class InventoryService {
    
    public List<Items> getAll(){
        ItemsDB itemsDB = new ItemsDB();
        
        List<Items> itemList = itemsDB.getAll();
        return itemList;
    }
    
    public void insert(int itemID, String name, double price, String username, int categoryID){
        ItemsDB itemsdb = new ItemsDB();
        UserDB  userdb = new UserDB();
        CategoriesDB categoriesdb = new CategoriesDB();
        
        Items newItem = new Items(itemID, name, price);
        Users owner = userdb.getUser(username);
        Categories category = categoriesdb.getCategory(categoryID);
        
        newItem.setOwner(owner);
        newItem.setCategory(category);
        
        itemsdb.insert(newItem);
    }
    
    public void delete(Items item, String username){
        
        if(item.getOwner().getUsername().equals(username)){
            ItemsDB itemsdb = new ItemsDB();
            Items deleteItem = item;
            itemsdb.delete(deleteItem);
        }
        
    }
    
    public void edit(Items edititem){
        ItemsDB itemsdb = new ItemsDB();
        Users user = edititem.getOwner();
        
        List<Items> iList = user.getItemsList();
        
        try {
            itemsdb.edit(edititem);
        } catch (Exception ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Categories> getAllCategories() throws Exception {
        CategoriesDB categoriesDB = new CategoriesDB();
        
        List<Categories> categories = categoriesDB.getAll();
        return categories;
    }
    
    public void addCategories(Categories category){
        CategoriesDB categoriesDB = new CategoriesDB();
        
        categoriesDB.addCategory(category);
    }
    
    public void editCategories(Categories category){
        CategoriesDB categoriesDB = new CategoriesDB();
        
        categoriesDB.editCategory(category);
    }
    
    public Categories getCategories(int categoryID){
        CategoriesDB categoriesDB = new CategoriesDB();
        Categories result = categoriesDB.getCategory(categoryID);
        return result;
    }
}
