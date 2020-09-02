package com.homework.nix.hibernate.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "operations")
@DiscriminatorFormula("CASE WHEN amount < 0 THEN 'expense' WHEN amount > 0 THEN 'income' END")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    @Column(nullable = false)
    private long amount;

    private String description;

    @Column(nullable = false)
    private Instant timestamp;

}
