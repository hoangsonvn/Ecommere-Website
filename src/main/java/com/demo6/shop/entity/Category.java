package com.demo6.shop.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;
    @Column(name = "category_name",nullable = false,unique = true)
    private String categoryName;
    @Column(name = "title")
    private String title;
    @Column(name = "content",columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
    private List<Product> products;
}
