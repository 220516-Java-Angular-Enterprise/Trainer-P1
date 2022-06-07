package com.revature.yolp.daos;

import com.revature.yolp.models.Restaurant;
import com.revature.yolp.util.custom_exceptions.InvalidSQLException;
import com.revature.yolp.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO implements CrudDAO<Restaurant> {
    Connection con = DatabaseConnection.getCon();

    @Override
    public void save(Restaurant obj) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO restaurants (id, name, city, state) VALUES (?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getName());
            ps.setString(3, obj.getCity());
            ps.setString(4, obj.getState());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(Restaurant obj) {

    }

    @Override
    public void delete(String id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM restaurants WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to update data from to the database.");
        }
    }

    @Override
    public Restaurant getById(String id) {
        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM restaurants");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                restaurants.add(new Restaurant(rs.getString("id"), rs.getString("name"), rs.getString("city"), rs.getString("state")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return restaurants;
    }

    public void updateRestoName(String name, String id) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE restaurants SET name = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to update data from to the database.");
        }
    }
}
