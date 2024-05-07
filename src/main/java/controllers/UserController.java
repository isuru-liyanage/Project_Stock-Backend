package controllers;

import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.google.gson.Gson;
import services.SignupService;
import utils.Hash;

public class UserController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        int userID;
        try {

            Gson gson = new Gson();
            BufferedReader reader = req.getReader();
            User user = gson.fromJson(reader, User.class);

            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassword());


            if (user.getPassword().length() < 8) {
                System.out.println("Password length should be at least 8 characters");
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                out.write("Password should be at least 8 characters");
                return;

            }
            if (!isValidEmail(user.getEmail())){
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                out.write("Invalid email address");
//                resp.getWriter().flush();
                return;
            }

            String hashedPassword = Hash.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);


            SignupService newuser = new SignupService();

            int isSuccess = newuser.isInsertionSuccessful(user);
            if (isSuccess==1) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.write("Registration successfully");
//                System.out.println("Registration successful");
            } else if(isSuccess==2){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Email or Username already exists");
            }  else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Registration failed");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }




    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();


}
}


