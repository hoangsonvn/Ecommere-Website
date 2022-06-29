package com.demo6.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="voucher")
public class Voucher implements Serializable {

    @Id
    @Column(length = 50, nullable = false)
    private String voucherId;

    @Column(nullable = false)
    private int voucherPercent;
    @Column(nullable = false,unique = true)
    private String code;
    @Column(name = "shortDescription", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(nullable = false)
    private Date effectiveDate;

    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(name = "title")
    private String title;
    @Column(name = "max_voucher_price")
    private long maxVoucher;
    @ManyToMany(mappedBy = "vouchers")
    private List<User> users = new ArrayList<>();
}
