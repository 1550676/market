package ru.zakharova.elena.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zakharova.elena.market.entities.OrderItem;
import ru.zakharova.elena.market.entities.Product;
import ru.zakharova.elena.market.entities.dtos.OrderItemDTO;
import ru.zakharova.elena.market.repositories.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {
    private OrderItemRepository orderItemRepository;

   @Autowired
    public void setCategoryRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemDTO> mapEntityListToDtoList(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }

    public void delete(Product product) {
            OrderItem item = orderItemRepository.findOneByProduct(product);
        orderItemRepository.deleteById(item.getId());
    }


}
