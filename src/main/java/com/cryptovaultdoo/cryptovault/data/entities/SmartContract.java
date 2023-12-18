package com.cryptovaultdoo.cryptovault.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "smart_contracts")
@Getter
@Setter
@NoArgsConstructor
public class SmartContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    private String contractAddress;

    @Embedded
    private BaseAuditEntity audit;

    public SmartContract(String name, String contractAddress) {
        this.name = name;
        this.contractAddress = contractAddress;
    }

}
