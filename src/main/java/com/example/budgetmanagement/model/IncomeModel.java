package com.example.budgetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class IncomeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;
    private Integer salary;
    private Integer investment;
    private Integer gift;
    private Integer interest;
    private Integer rental;
    private Integer sales;
    @Column(name = "y")
    private Integer year;
    @Column(name = "m")
    private String month;
}
