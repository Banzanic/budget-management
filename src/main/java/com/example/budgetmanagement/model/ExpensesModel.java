package com.example.budgetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class ExpensesModel implements Comparable<ExpensesModel>{

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
    public int getTotalExpense(){
        int totalExpenses = 0;
        totalExpenses+= Objects.requireNonNullElse(groceries, 0);
        totalExpenses+= Objects.requireNonNullElse(rent, 0);
        totalExpenses+= Objects.requireNonNullElse(transportation, 0);
        totalExpenses+= Objects.requireNonNullElse(subscriptions, 0);
        totalExpenses+= Objects.requireNonNullElse(healthCare, 0);
        totalExpenses+= Objects.requireNonNullElse(entertainment, 0);
        totalExpenses+= Objects.requireNonNullElse(debt, 0);
        return totalExpenses;
    }

    @Override
    public int compareTo(ExpensesModel other) {
        int yearComparison = this.year - other.getYear();
        if (yearComparison!= 0) {
            return yearComparison;
        }
        return Integer.compare(Integer.parseInt(this.month), Integer.parseInt(other.getMonth()));
    }
}
