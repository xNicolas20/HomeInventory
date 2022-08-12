
package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;


public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String logoutParam = request.getParameter("logout");
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("validAcc");
        Users currentUser = null;
        
        if(user != null){
            AccountService accountservice = new AccountService();
            currentUser = accountservice.getUser(user);
            if(currentUser.getActive() == false){
                session.invalidate();
                request.setAttribute("deactivate", true);
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
        }
        else{
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        if(logoutParam == null){
            if(currentUser.getIsAdmin() == true){
                response.sendRedirect("admin");
                return;
            }
            else{
                response.sendRedirect("admin");
                return;
            }
        }
        else if(logoutParam.isEmpty()){
            String logoutMessage = "You have successfully logged out.";
            request.setAttribute("logoutMessage", logoutMessage);
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Users isAccount = loginProccess(username, password);
        
        if(isAccount == null){
            request.setAttribute("isError", true);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        else if(!isAccount.getActive()){
            request.setAttribute("deactivate", true);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        else{
            String currentUser = isAccount.getUsername();
            session.setAttribute("validAcc", currentUser);
            if(isAccount.getIsAdmin() == true){
                response.sendRedirect("admin");
                return;
            }
            else{
                response.sendRedirect("inventory");
                return;
            }
        }
    }

    private Users loginProccess(String username, String password) {
        AccountService accounts = new AccountService();
        
        
        if(username != null){
            Users user = accounts.getUser(username);
            if(user != null && user.getPassword().equals(password)){
                return user;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }
}
