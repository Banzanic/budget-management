package com.example.budgetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExpensesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;
    private Integer groceries;
    private Integer rent;
    private Integer transportation;
    private Integer subscriptions;
    private Integer healthCare;
    private Integer entertainment;
    private Integer debt;
    @Column(name = "y")
    private Integer year;
    @Column(name = "m")
    private String month;
}
