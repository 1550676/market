package ru.zakharova.elena.market.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@NotNull(message = "price is required")
    @Column(name = "title")
    private String title;

    //@NotNull(message = "price is required")
    @Column(name = "price")
    private BigDecimal price;

    //@NotNull(message = "price is required")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn (name = "products_id"),
            inverseJoinColumns = @JoinColumn (name = "categories_id")
    )
    private List<Category> categories;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
