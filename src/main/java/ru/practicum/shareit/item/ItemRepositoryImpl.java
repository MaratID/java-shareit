package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeptions.InternalServerException;
import ru.practicum.shareit.item.model.Item;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<Item> mapper;

    private static final String INSERT_QUERY = """
            INSERT INTO items (user_id, name, description, available)
            VALUES (?, ?, ?, ?)""";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM items WHERE id = ?";

    private static final String FIND_BY_USERID_AND_ITEMID_QUERY = "SELECT * FROM items WHERE user_id = ? AND id = ?";

    private static final String FIND_BY_USERID_QUERY = "SELECT * FROM items WHERE user_id = ?";

    private static final String FIND_BY_NAMEANDUSERID_QUERY =
            """
                    SELECT * FROM items WHERE user_id = ?
                    AND LOWER(TRIM(name)) = LOWER(TRIM(?))
                    """;

    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM items WHERE name = ?";

    private static final String UPDATE_QUERY =
            """
                    UPDATE items SET name = ?, description = ?, available = ?
                    WHERE user_id = ?
                    """;

    @Override
    public List<Item> findByUserId(long userId) {
        return jdbc.query(FIND_BY_USERID_QUERY, mapper, userId);
    }

    @Override
    public Optional<Item> findByName(String name) {
        try {
            Item result = jdbc.queryForObject(FIND_BY_NAME_QUERY, mapper, name);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findByNameAndUserId(Long userID, String itemName) {
        String normalizedName = itemName.trim().toLowerCase();
        return jdbc.query(FIND_BY_NAMEANDUSERID_QUERY, mapper, userID, normalizedName);
    }

    @Override
    public Optional<Item> findByUserIdAndItemId(Long userId, Long itemId) {
        try {
            Item result = jdbc.queryForObject(FIND_BY_USERID_AND_ITEMID_QUERY, mapper, userId, itemId);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public Item save(Long userId, Item item) {
        Long id = insert(
                INSERT_QUERY,
                userId,
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
        item.setId(id);
        return item;
    }

    @Override
    @Transactional
    public Item update(Long userId, Item newItem) {
        update(
                UPDATE_QUERY,
                newItem.getName(),
                newItem.getDescription(),
                newItem.getAvailable(),
                userId
        );
        newItem.setId(newItem.getId());

        return newItem;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        try {
            Item result = jdbc.queryForObject(FIND_BY_ID_QUERY, mapper, itemId);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
    }

    protected Long insert(String query, Object... params) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                ps.setObject(idx + 1, params[idx]);
            }
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);

        if (id != null) {
            return id;
        } else {
            throw new InternalServerException("Не удалось сохранить данные");
        }
    }

    protected void update(String query, Object... params) {
        int rowsUpdated = jdbc.update(query, params);
        if (rowsUpdated == 0) {
            throw new InternalServerException("Не удалось обновить данные");
        }
    }
}