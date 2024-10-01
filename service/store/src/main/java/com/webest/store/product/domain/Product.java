package com.webest.store.product.domain;


import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_product SET is_deleted = true WHERE id = ?")
@SQLRestriction(BaseEntity.DEFAULT_CONDITION)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private String name;

    private Double price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public static Product of(Long storeId, String name, Double price, String description) {
        Product product = new Product();
        product.storeId = storeId;
        product.name = name;
        product.price = price;
        product.description = description;
        return product;
    }

    // 상품 상태 변경
    public void updateStatus(ProductStatus status) {
        this.status = status;
    }
}
