package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductsModel {
    int product_id;
    String product_name;
    String price;
    int supplier_id;
    int quantity;
    Date expire_date;
    Date add_date;
    String supplier_name;
    String supplier_email;

    public ProductsModel(ResultSet resultSet) throws SQLException {


        product_id = resultSet.getInt("product_id");
        product_name = resultSet.getString("product_name");
        price = resultSet.getString("price");
        supplier_id = resultSet.getInt("supplier_id");
        quantity = resultSet.getInt("quantity");
        expire_date = resultSet.getDate("expire_date");
        add_date = resultSet.getDate("add_date");
        supplier_name = resultSet.getString("name");
        supplier_email = resultSet.getString("email");


    }
    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_email() {
        return supplier_email;
    }

    public void setSupplier_email(String supplier_email) {
        this.supplier_email = supplier_email;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
}
