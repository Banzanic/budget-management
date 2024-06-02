package com.example.budgetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class IncomeModel implements Comparable<IncomeModel> {

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

    public int getTotalIncome(){
        int totalIncome = 0;
        totalIncome+= Objects.requireNonNullElse(salary, 0);
        totalIncome+= Objects.requireNonNullElse(investment, 0);
        totalIncome+= Objects.requireNonNullElse(gift, 0);
        totalIncome+= Objects.requireNonNullElse(interest, 0);
        totalIncome+= Objects.requireNonNullElse(rental, 0);
        totalIncome+= Objects.requireNonNullElse(sales, 0);
        return totalIncome;
    }

    @Override
    public int compareTo(IncomeModel other) {
        int yearComparison = this.year - other.getYear();
        if (yearComparison!= 0) {
            return yearComparison;
        }
        return Integer.compare(Integer.parseInt(this.month), Integer.parseInt(other.getMonth()));
    }
}
