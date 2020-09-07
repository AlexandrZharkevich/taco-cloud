package by.mrbregovich.tacos.data;

import by.mrbregovich.tacos.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByZip(String zip);

    List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
}
