package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.entity.Dish;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Utils {

    public static void mockResultSetForDish(Dish dish, ResultSet resultSet) throws SQLException {
        Mockito.when(resultSet.getString("description")).thenReturn(dish.getDescription());
        Mockito.when(resultSet.getLong("weight")).thenReturn(dish.getWeight());
        Mockito.when(resultSet.getLong("price")).thenReturn(dish.getPrice());
        Mockito.when(resultSet.getLong("category_id")).thenReturn(dish.getCategoryId());
        Mockito.when(resultSet.getString("name")).thenReturn(dish.getName());
        Mockito.when(resultSet.getLong("id")).thenReturn(dish.getId());
    }

    public static void assertFullyEqualsDishes(Dish expected, Dish actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getWeight(), actual.getWeight());
        assertEquals(expected.getDescription(), actual.getDescription());
    }
}
