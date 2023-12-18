package com.cryptovaultdoo.cryptovault.data.entities;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_cryptocurrencies")
@Getter
@Setter
@NoArgsConstructor
public class UserCryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cryptocurrency_id")
    private Cryptocurrency cryptocurrency;

    @Column(precision = 16, scale = 2)
    private BigDecimal amount;

    @Embedded
    private BaseAuditEntity audit;

    public UserCryptocurrency(User user, Cryptocurrency cryptocurrency) {
        this.user = user;
        this.cryptocurrency = cryptocurrency;
    }

    public UserCryptocurrency(String name, String code, BigDecimal amount) {
        this.cryptocurrency = new Cryptocurrency(name, code);
        this.amount = amount;
    }

}
