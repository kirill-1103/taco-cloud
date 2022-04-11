package ru.krey.tacocloud.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.krey.tacocloud.model.Order;
import ru.krey.tacocloud.model.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * This class has been replaced by another!
 *
 */

@Slf4j
//@Repository
public class JDBCOrderRepository /*implements OrderRepository*/{

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

//    @Autowired
    public JDBCOrderRepository(JdbcTemplate jdbc){
        orderInserter = new SimpleJdbcInsert(jdbc).
                withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbc).
                withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();

    }


//    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for(Taco t : tacos){
            saveTacoToOrder(t,orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String,Object> values = objectMapper.convertValue(order,Map.class);
        values.put("placedAt",order.getPlacedAt());

        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long id ){
        Map<String,Object> values = new HashMap<>();
        values.put("tacoOrder",id);
        values.put("taco",taco.getId());
        orderTacoInserter.execute(values);
    }

}
