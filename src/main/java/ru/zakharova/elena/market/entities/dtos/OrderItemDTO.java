package ru.zakharova.elena.market.entities.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zakharova.elena.market.entities.OrderItem;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private BigDecimal price;
    private int quantity;
    private String productTitle;
    private Long productId;

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
        this.productTitle = orderItem.getProduct().getTitle();
        this.productId = orderItem.getProduct().getId();
    }
}
