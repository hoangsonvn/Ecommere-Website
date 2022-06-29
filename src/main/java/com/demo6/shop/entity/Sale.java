package com.demo6.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sale")
public class Sale implements Serializable {


	@Id
	@Column(name = "sale_id", length = 50, nullable = false)
	private String saleId;
	@Column(name = "sale_percent", nullable = false,unique = true)
	private int salePercent;
	@Column(name="shortDescription",columnDefinition = "TEXT")
	private String shortDescription;
	@Column(name = "create_date" , nullable = false)
	private Date createDate;
	@Column(name = "expired_date" , nullable = false)
	private Date expirationDate;
	@OneToMany(mappedBy = "sale")
	private List<Product> products;
	@Column(name = "title")
	private String title;


}
