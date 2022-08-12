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
import models.Users;

/**
 *
 * @author ivanc
 */
public class AccountService {
    
    public List<Users> getAll(){
        UserDB userdb = new UserDB();
        
        List<Users> resultUser = userdb.getAll();
        return resultUser;
    }
    
    public Users getUser(String username){
        UserDB userdb = new UserDB();
        
        Users resultUser = userdb.getUser(username);
        return resultUser;
    }
    
    public void insert(String username, String password, String email, String firstName, String lastName, boolean isActive, boolean isAdmin){
        UserDB userdb = new UserDB();
        
        Users newUser = new Users(username, password, email, firstName, lastName, isActive, isAdmin);
        userdb.insert(newUser);
    }
    
    public void delete(String username){
        UserDB userdb = new UserDB();
        
        Users user = userdb.getUser(username);
        userdb.delete(user);
    }
    
    public void edit(Users user, String username, String password, String email, String firstName, String lastName, boolean isActive, boolean isAdmin) throws Exception{
        UserDB userdb = new UserDB();
        
        Users prevUser = userdb.getUser(user.getUsername());
        Users editUser = user;
        editUser.setUsername(username);
        editUser.setPassword(password);
        editUser.setEmail(email);
        editUser.setFirstName(firstName);
        editUser.setLastName(lastName);
        editUser.setActive(isActive);
        editUser.setIsAdmin(isAdmin);
        
        userdb.update(editUser);
        
        if(!prevUser.getUsername().equals(editUser.getUsername())){
            userdb.delete(prevUser);
        }
    }
    
    public void Deactivate(Users user) throws Exception{
        UserDB userdb = new UserDB();
        
        Users deactivateUser = user;
        
        deactivateUser.setActive(false);
        userdb.update(deactivateUser);
    }
    
    
}
