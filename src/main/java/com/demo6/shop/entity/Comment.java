package com.demo6.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "comment")
@Entity
@Setter
@Getter
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String createBy;
    @Column(nullable = false)
    private LocalDateTime createDate;
    @Column
    private String shortComment;
    @Column
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id_user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id_product")
    private Product product;
}
