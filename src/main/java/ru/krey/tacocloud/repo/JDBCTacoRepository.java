package ru.krey.tacocloud.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.krey.tacocloud.model.Ingredient;
import ru.krey.tacocloud.model.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * This class has been replaced by another!
 *
 */

//@Repository
public class JDBCTacoRepository /*implements TacoRepository*/{
    private JdbcTemplate jdbc;

    public JDBCTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

//    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for(Ingredient ingredient:taco.getIngredients()){
            saveIngredientToTaco(ingredient,tacoId);
        }
        return taco;
    }

    public long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "insert into Taco (name,createdAt) values(?,?)", Types.VARCHAR,Types.TIMESTAMP
        );
        factory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = factory.newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime()))
        );


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc,keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        jdbc.update("insert into Taco_Ingredients (taco,ingredient)"+
                "values ( ?,? )",
                tacoId, ingredient.getId());
    }

}
