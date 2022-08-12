
package servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import models.Items;
import models.Users;
import services.AccountService;
import services.InventoryService;


public class InventoryServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("validAcc");
        
        AccountService accountService = new AccountService();
        InventoryService categoryList = new InventoryService();
        Users user = accountService.getUser(currentUser);
        
        String fullname = user.getFirstName() + " " + user.getLastName();
        
        if(user.getLastName().contains("Admin")){
            fullname = user.getFirstName();
        }
        session.setAttribute("fullname", fullname);
        
        List<Items> inventory = user.getItemsList();
        session.setAttribute("inventory", inventory);
        
        if(user.getIsAdmin()){
            session.setAttribute("isAdmin", true);
        }
        
        // Design
        String hiddenval = "display: none;";
        session.setAttribute("hiddenval", hiddenval);
        
        try {
            List<Categories> category = categoryList.getAllCategories();
            session.setAttribute("category", category);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        InventoryService inventoryservice = new InventoryService();
        
        String currentUser = (String) session.getAttribute("validAcc");
        String action = request.getParameter("action");
        
        if(action.equals("Add")){
            
            String category = request.getParameter("category");
            String itemName = request.getParameter("itemname");
            String value = request.getParameter("price");
        
            double price = 0;
            try{
                price = Integer.parseInt(value);
            }catch(Exception e){
                request.setAttribute("error", true);
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                return;
            }
        
            if(itemName == null){
                request.setAttribute("error", true);
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                return;
            }
            
            Users user = accountService.getUser(currentUser);
        
            int iD = user.getItemsList().size() + 1;
            String username = user.getUsername();
            inventoryservice.insert(iD, itemName, price, username, Integer.parseInt(category));
       
            user = accountService.getUser(currentUser);
            List<Items> inventory = user.getItemsList();
            session.setAttribute("inventory", inventory);
        }
        else if(action.equals("Delete")){
            Users user = accountService.getUser(currentUser);
            List<Items> inventory = user.getItemsList();
            
            int deleteItem = Integer.parseInt(request.getParameter("itemID"));
            Items removeItem = null;
            for(Items i: inventory){
                if(i.getItemID() == deleteItem){
                    removeItem = i;
                }
            }
            inventoryservice.delete(removeItem, user.getUsername());
            user = accountService.getUser(currentUser);
            inventory = user.getItemsList();
            session.setAttribute("inventory", inventory);
        }
        else if(action.equals("Edit")){
            Users user = accountService.getUser(currentUser);
            List<Items> inventory = user.getItemsList();
            
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Items editItem = null;
            for(Items i: inventory){
                if(i.getItemID() == itemID){
                    editItem = i;
                }
            }
            String hiddenval = "";
            request.setAttribute("editItem", editItem);
            request.setAttribute("hiddenval", hiddenval);
        }
        else if(action.equals("Save")){
            
            String editCategory = request.getParameter("editCategory");
            String editItemName = request.getParameter("editItemName");
            String value = request.getParameter("editPrice");
            
            double editPrice = 0;
            try{
                editPrice = Double.parseDouble(value);
            }catch(Exception e){
                request.setAttribute("errorEdit", true);
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                return;
            }
            
            if(editItemName == null){
                request.setAttribute("errorEdit", true);
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                return;
            }
            
            Users user = accountService.getUser(currentUser);
            List<Items> inventory = user.getItemsList();
            
            int itemID = Integer.parseInt(request.getParameter("editItemID"));
            Items editItem = null;
            for(Items i: inventory){
                if(i.getItemID() == itemID){
                    editItem = i;
                }
            }
            List<Categories> category = null;
            try {
                category = inventoryservice.getAllCategories();
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Categories editC = null;
            for(Categories c: category){
                if(c.getCategoryID() == Integer.parseInt(editCategory)){
                    editC = c;
                }
            }
            
            editItem.setCategory(editC);
            editItem.setItemName(editItemName);
            editItem.setPrice(editPrice);
            
            inventoryservice.edit(editItem);
            
            user = accountService.getUser(currentUser);
            inventory = user.getItemsList();
            session.setAttribute("inventory", inventory);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }
}
