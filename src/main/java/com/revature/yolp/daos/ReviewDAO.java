package com.revature.yolp.daos;

import com.revature.yolp.models.Review;
import com.revature.yolp.util.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO implements CrudDAO<Review> {
    Connection con = DatabaseConnection.getCon();

    @Override
    public void save(Review obj) {

    }

    @Override
    public void update(Review obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Review getById(String id) {
        return null;
    }

    @Override
    public List<Review> getAll() {
        return null;
    }

    public List<Review> getReviewsByRestoId(String resto_id) {
        List<Review> reviews = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reviews WHERE restaurant_id = (?)");
            ps.setString(1, resto_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                reviews.add(new Review(rs.getString("id"), rs.getInt("rating"), rs.getString("msg"), rs.getString("user_id"), rs.getString("restaurant_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return reviews;
    }
}
