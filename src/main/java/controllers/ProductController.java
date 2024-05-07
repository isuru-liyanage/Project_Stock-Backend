package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database_connection.DatabaseConnection;
import models.ProductsModel;
import services.notifysupllier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DatabaseConnection.initializeDatabase();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM products join supplier on products.suplier_id=supplier.supplier_id");
            ResultSet collection = preparedStatement.executeQuery();
            JsonArray jsonArray = new JsonArray();
            while (collection.next()) {
                ProductsModel product = new ProductsModel(collection);
                JsonObject jsonObject =new Gson().toJsonTree(product).getAsJsonObject();
                jsonArray.add(jsonObject);

            }

            resp.getWriter().write(jsonArray.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DatabaseConnection.initializeDatabase();
        Gson gson = new Gson();
        ProductsModel product = gson.fromJson(req.getReader(), ProductsModel.class);
        //product_id
        //product_name
        //price
        //suplier_id
        //quantity
        //expire_date
        //add_date


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (product_name, price, suplier_id, quantity, expire_date) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getProduct_name());
            preparedStatement.setString(2, product.getPrice());
            preparedStatement.setInt(3, product.getSupplier_id());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setDate(5, new java.sql.Date(product.getExpire_date().getTime()));

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DatabaseConnection.initializeDatabase();
        Gson gson = new Gson();
        ProductsModel product = gson.fromJson(req.getReader(), ProductsModel.class);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET product_name = ?, price = ?, quantity = ?  WHERE product_id = ?");
            preparedStatement.setString(1, product.getProduct_name());
            preparedStatement.setString(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getProduct_id());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                if(product.getQuantity()<10) {
                    //notifysupllier.notifySuppli(product.getSupplier_email(),product.getProduct_name(),product.getQuantity()
                    notifysupllier notifysupllier = new notifysupllier();
                    Connection connection1 = DatabaseConnection.initializeDatabase();
                    PreparedStatement preparedStatement1 = connection1.prepareStatement("SELECT * FROM products join supplier on products.suplier_id=supplier.supplier_id where product_id=?");
                    preparedStatement1.setInt(1, product.getProduct_id());
                    ResultSet collection = preparedStatement1.executeQuery();
                    if (collection.next()) {
                        ProductsModel product1 = new ProductsModel(collection);
                        if (notifysupllier.notifySuppli(product1.getSupplier_email(), product1.getProduct_name(), product1.getQuantity())) {
                            resp.setStatus(HttpServletResponse.SC_OK);
                        }
                    }
                }else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                }


            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DatabaseConnection.initializeDatabase();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE product_id = ?");
            preparedStatement.setInt(1, Integer.parseInt(req.getParameter("product_id")));

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
