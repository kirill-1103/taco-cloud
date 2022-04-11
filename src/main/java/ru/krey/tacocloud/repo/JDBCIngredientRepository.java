package ru.krey.tacocloud.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.krey.tacocloud.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * This class has been replaced by another!
 *
 */

//@Repository
public class JDBCIngredientRepository /*implements IngredientRepository*/{

    private final JdbcTemplate jdbc;

    public JDBCIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

//    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient",this::mapRowToIngredient);
    }

//    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("select id,name,type from Ingredient where id = ? ",this::mapRowToIngredient,id);
    }

//    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient (id,name,type) values(?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

}
