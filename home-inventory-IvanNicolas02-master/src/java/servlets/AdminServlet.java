
package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import models.Users;
import services.AccountService;
import services.InventoryService;


public class AdminServlet extends HttpServlet {


   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("validAcc");
        
        AccountService accountService = new AccountService();
        Users user = accountService.getUser(currentUser);
        
        // style
        String hiddenval = "display: none;";
        session.setAttribute("hiddenval", hiddenval);
        
        if(currentUser == null){
            response.sendRedirect("login");
            return;
        }
        
        List<Users> users = accountService.getAll();
        session.setAttribute("users", users);
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("validAcc");
        AccountService accountService = new AccountService();
        Users user = accountService.getUser(currentUser);
        
        String action = request.getParameter("action");
        
        if(action.equals("Add")){
            
            List<Users> usernames = accountService.getAll();
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            
            if(username.isEmpty() || password.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()){
                request.setAttribute("nullValue", true);
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                return;
            }
            
            for(Users u: usernames){
                if(u.getUsername().equals(username)){
                    request.setAttribute("addError", true);
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    return;
                }
            }
            accountService.insert(username, password, email, firstName, lastName, true, false);
        }
        else if(action.equals("Delete")){
            String usernameValue = request.getParameter("usernameValue");
            
            if(usernameValue.equals(user.getUsername())){
                request.setAttribute("deleteError", true);
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                return;
            }
            
            accountService.delete(usernameValue);
        }   
        else if(action.equals("Edit")){
            String usernameValue = request.getParameter("usernameValue");
            Users editUser = accountService.getUser(usernameValue);
            
            String hiddenval = "";
            
            session.setAttribute("editUser", editUser);
            request.setAttribute("hiddenval", hiddenval);
        }
        if(action.equals("Save")){
            String usernameValue = request.getParameter("usernameValue");
            Users editUser = accountService.getUser(usernameValue);
            
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String active = request.getParameter("isActive");
            String admin = request.getParameter("isAdmin");
            
            boolean isActive = false;
            boolean isAdmin = false;
            
            if(active != null && active.equals("isActive")){
                isActive = true;
            }
            if(admin != null && admin.equals("isAdmin")){
                isAdmin = true;
            }
            
            try {
                accountService.edit(editUser, username, password, email, firstName, lastName, isActive, isAdmin);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        List<Users> users = accountService.getAll();
        session.setAttribute("users", users);
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }

  
}
