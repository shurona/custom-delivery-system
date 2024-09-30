package com.webest.order.domain.model;

import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    private Double price;

    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public static OrderProduct create(Long productId,
                                      Integer quantity,
                                      Double price)
    {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.productId = productId;
        orderProduct.quantity = quantity;
        orderProduct.price = price;
        orderProduct.totalPrice = quantity * price; // (총금액 = 수량 * 상품금액)
        return orderProduct;
    }

    public void update(Long productId,
                       Integer quantity,
                       Double price,
                       Double totalPrice)
    {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
