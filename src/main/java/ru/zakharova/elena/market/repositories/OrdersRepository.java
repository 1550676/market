package ru.zakharova.elena.market.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zakharova.elena.market.entities.Order;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Long> {
}
