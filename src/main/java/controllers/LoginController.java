package controllers;

import com.google.gson.Gson;
import database_connection.DatabaseConnection;
import models.User;
import utils.Hash;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Gson gson = new Gson();
        User login = gson.fromJson(req.getReader(), User.class);

        try {
            con = DatabaseConnection.initializeDatabase();
            String query = "SELECT * FROM users WHERE email= ?;";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, login.getEmail());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                int userlevelID = resultSet.getInt("user_role");

                System.out.println("results: " + storedPassword);
                boolean passwordMatch = Hash.checkPassword(login.getPassword(), storedPassword);
                if (passwordMatch) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"userlevel\":"+userlevelID+"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Incorrect password");
                }

            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("User not found");

            }
        } catch(SQLException  e) {
            e.printStackTrace();
        }


    }





}
