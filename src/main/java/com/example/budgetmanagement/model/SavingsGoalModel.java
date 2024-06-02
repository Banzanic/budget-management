package com.example.budgetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SavingsGoalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier;
    private String goalName;
    private Integer goalAmount;
    private Integer savedAmount;
    @Column(name = "y")
    private Integer yearDesiredDate;
    @Column(name = "m")
    private String monthDesiredDate;

}
