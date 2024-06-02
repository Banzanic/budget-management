package com.example.budgetmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String desiredDate;

}
