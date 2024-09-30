package com.webest.store.category.domain;

import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "p_store_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_store_category SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class StoreCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryKey;

    private String value;

    private StoreCategory(String categoryKey, String value) {
        this.categoryKey = categoryKey;
        this.value = value;
    }

    public static StoreCategory of(String categoryKey, String value) {
        return new StoreCategory(categoryKey, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreCategory storeCategory = (StoreCategory) o;

        return id != null && id.equals(storeCategory.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void updateValue(String value) {
        this.value = value;
    }

}
