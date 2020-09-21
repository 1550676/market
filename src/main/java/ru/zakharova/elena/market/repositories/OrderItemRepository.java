package ru.zakharova.elena.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.zakharova.elena.market.entities.OrderItem;
import ru.zakharova.elena.market.entities.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
    OrderItem findOneByProduct(Product product);

}