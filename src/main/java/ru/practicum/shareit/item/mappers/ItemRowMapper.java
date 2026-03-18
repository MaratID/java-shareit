package ru.practicum.shareit.item.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Item item = new Item();

        item.setId(resultSet.getLong("id"));
        item.setUserId(resultSet.getLong("user_id"));
        item.setName(resultSet.getString("name"));
        item.setDescription(resultSet.getString("description"));
        item.setAvailable(resultSet.getString("available"));

        return item;
    }
}