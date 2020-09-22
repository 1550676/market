package ru.zakharova.elena.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zakharova.elena.market.entities.Order;
import ru.zakharova.elena.market.repositories.OrdersRepository;

@Service
public class OrdersService {
    private OrdersRepository ordersRepository;

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order save(Order order) {
        return ordersRepository.save(order);
    }
}
