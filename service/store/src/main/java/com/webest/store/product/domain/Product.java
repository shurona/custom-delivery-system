package com.webest.store.product.domain;


import com.webest.app.jpa.BaseEntity;
import com.webest.store.store.domain.model.Store;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private String name;

    private Double price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;


    public static Product of(Store store, String name, Double price, String description) {
        Product product = new Product();
        product.store = store;
        product.name = name;
        product.price = price;
        product.description = description;
        return product;
    }

    // 상품 상태 변경
    public void updateStatus(ProductStatus status) {
        this.status = status;
    }

    // 상품 디테일 변경
    public void updateDetails(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
