package com.revature.yolp.services;

import com.revature.yolp.daos.RestaurantDAO;
import com.revature.yolp.models.Restaurant;
import com.revature.yolp.util.custom_exceptions.InvalidSQLException;

import java.util.List;

public class RestaurantService {
    private final RestaurantDAO restaurantDAO;

    public RestaurantService(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    public void register(Restaurant resto) {
        restaurantDAO.save(resto);
    }

    public List<Restaurant> getAllResto() {
        return restaurantDAO.getAll();
    }

    public boolean updateName(String name, String id) {
        try {
            restaurantDAO.updateRestoName(name, id);
            return true;
        } catch (InvalidSQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteResto(String id) {
        try {
            restaurantDAO.delete(id);
            return true;
        } catch (InvalidSQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
