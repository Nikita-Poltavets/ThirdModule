package com.homework.nix.hibernate.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long balance;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations;

    @OneToMany(mappedBy = "account")
    @Where(clause = "amount > 0")
    private List<Income> incomes;

    @OneToMany(mappedBy = "account")
    @Where(clause = "amount < 0")
    private List<Expense> expenses;
}
