/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Categories;
import models.Users;
import services.AccountService;
import services.InventoryService;

/**
 *
 * @author ivanc
 */
public class CategoryServlet extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        HttpSession session = request.getSession();
        InventoryService is = new InventoryService();
        
        String currentUser = (String) session.getAttribute("validAcc");
        AccountService accountService = new AccountService();
        
        Users user = accountService.getUser(currentUser);
        
        if(user.getIsAdmin()){
            session.setAttribute("isAdmin", true);
        }
        
        List<Categories> categoriesList = null;
        try {
            categoriesList = is.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.setAttribute("categoriesList", categoriesList);
        
        // Design
        String hiddenval = "display: none;";
        session.setAttribute("hiddenval", hiddenval);
        
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
        return;
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        
        String currentUser = (String) session.getAttribute("validAcc");
        String action = request.getParameter("action");
        
        if(action.equals("Edit")){
            String categoryID = request.getParameter("categoryID");
            Categories category = is.getCategories(Integer.parseInt(categoryID));
            request.setAttribute("editCategory", category);
            
            String hiddenval = "";
            request.setAttribute("hiddenval", hiddenval);
        }
        else if(action.equals("Add")){
            String categoryName = request.getParameter("categoryName");
            
            List<Categories> listCategory = null;
            try {
                listCategory = is.getAllCategories();
            } catch (Exception ex) {
                Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int categoryID = listCategory.size() + 1;
            
            Categories category = new Categories(categoryID, categoryName);
            is.addCategories(category);
            
        }
        else if(action.equals("Save")){
            String categoryName = request.getParameter("editCategoryName");
            String categoryID = request.getParameter("editCategoryID");
            
            Categories category = new Categories(Integer.parseInt(categoryID), categoryName);
            
            is.editCategories(category);
        }
        List<Categories> categoriesList = null;
        try {
            categoriesList = is.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.setAttribute("categoriesList", categoriesList);
        
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
        return;
    }


}
