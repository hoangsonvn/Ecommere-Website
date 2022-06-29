package com.demo6.shop.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private long productId;
	@Column(name = "product_name",nullable = false,unique = true)
	private String productName;
	@Column(name = "price", nullable = false)
	@NotNull
	private float price;
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "description",columnDefinition = "TEXT")
	private String description;
	@Column(name = "image")
	private String image;
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	@Column(name = "expired_date", nullable = false)
	private Date expiredDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id")
	private Sale sale;
	@OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
	private List<Comment> comments;
}
