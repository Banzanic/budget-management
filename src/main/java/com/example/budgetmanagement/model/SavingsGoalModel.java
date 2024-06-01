package com.example.budgetmanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SavingsGoalModel {

    private String goalName;
    private Integer goalAmount;
    private Integer savedAmount;

}
