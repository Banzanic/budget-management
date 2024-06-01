package com.example.budgetmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SavingsService {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private IncomeService incomeService;

    public Map<Integer, Integer> getYearlySavings() {
        Map<Integer, Integer> yearlyExpenseSums = expensesService.getYearlyExpenseSums();
        Map<Integer, Integer> yearlyIncomeSums = incomeService.getYearlyIncomeSums();
        Map<Integer, Integer> yearlySavings = new LinkedHashMap<>();
        for (int i = 2010; i <= 2024; i++) {
            int savings = yearlyIncomeSums.get(i) - yearlyExpenseSums.get(i);
            if (savings < 0) {
                savings = 0;
            }
            yearlySavings.put(i, savings);
        }
        return yearlySavings;
    }

    public Map<String, Integer> getMonthlySavingsByYear() {
        Map<String, Integer> monthlyExpenseSums = expensesService.getMonthlyExpenseSumsByYear(expensesService.getLatestYear());
        Map<String, Integer> monthlyIncomeSums = incomeService.getMonthlyIncomeSumsByYear(incomeService.getLatestYear());
        Map<String, Integer> monthlySavings = new LinkedHashMap<>();

        if (!Objects.equals(expensesService.getLatestYear(), incomeService.getLatestYear())) {
            System.out.println("Expense last year=" + expensesService.getLatestYear() + "is different than income last year=" + incomeService.getLatestYear());
        } else {
            for (int i = 1; i <= 12; i++) {
                int savings = monthlyIncomeSums.get(String.valueOf(i)) - monthlyExpenseSums.get(String.valueOf(i));
                if (savings < 0) {
                    savings = 0;
                }
                monthlySavings.put(String.valueOf(i), savings);
            }
        }

        return monthlySavings;

    }
}
