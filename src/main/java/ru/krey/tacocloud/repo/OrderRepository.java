package ru.krey.tacocloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krey.tacocloud.model.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrdersByZip(String zip);//find order by zip
    List<Order> findOrdersByZipAndPlacedAtBetween(String zip, Date start, Date end);

}
