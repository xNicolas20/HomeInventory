/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;

/**
 *
 * @author ivanc
 */
public class EditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("validAcc");

        AccountService sv = new AccountService();

        Users editUser = sv.getUser(currentUser);

        session.setAttribute("editUser", editUser);

        getServletContext().getRequestDispatcher("/WEB-INF/editUser.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("validAcc");
        AccountService sv = new AccountService();
        Users editUser = sv.getUser(currentUser);

        String action = request.getParameter("action");

        if (action.equals("Save")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String active = request.getParameter("isActive");

            try {
                sv.edit(editUser, username, password, email, firstName, lastName, true, editUser.getIsAdmin());
            } catch (Exception ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!username.equals(editUser.getUsername())) {
                Users updatedUser = sv.getUser(username);
                String newUser = updatedUser.getUsername();
                session.setAttribute("validAcc", newUser);
            }
            
            request.setAttribute("succcess", true);
        } else if(action.equals("Deactivate")) {
            
            try {
                sv.Deactivate(editUser);
            } catch (Exception ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Users updatedUser = sv.getUser(editUser.getUsername());
            String deactivatedUser = updatedUser.getUsername();
            session.setAttribute("validAcc", deactivatedUser);
            response.sendRedirect("login");
            return;
        }

    }
}
