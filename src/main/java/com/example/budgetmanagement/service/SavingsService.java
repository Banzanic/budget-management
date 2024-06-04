package com.example.budgetmanagement.service;

import com.example.budgetmanagement.model.SavingsGoalModel;
import com.example.budgetmanagement.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SavingsService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private IncomeService incomeService;

    public Integer getSummedSavings() {
        Map<Integer, Integer> savings = getYearlySavings();
        Integer summedSavings = 0;
        for(Map.Entry<Integer, Integer> entry : savings.entrySet()){
            summedSavings+=entry.getValue();
        }
        return summedSavings;
    }

    public void putSavingsGoal(SavingsGoalModel savingsGoalModel) {
        savingsGoalRepository.save(savingsGoalModel);
    }

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

        for (int i = 1; i <= 12; i++) {
            int savings = monthlyIncomeSums.get(String.valueOf(i)) - monthlyExpenseSums.get(String.valueOf(i));
            if (savings < 0) {
                savings = 0;
            }
            monthlySavings.put(String.valueOf(i), savings);
        }

        return monthlySavings;
    }

    public double getAverageMonthlySavings() {
        Map<String, Integer> monthlySavings = getMonthlySavingsByYear();
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();

        double totalSavings = 0;
        int count = 0;

        for (int i = 0; i < 12; i++) {
            int month = currentMonth - i;
            if (month <= 0) {
                month += 12;
            }
            String monthKey = String.valueOf(month);

            Integer savings = monthlySavings.get(monthKey);
            if (savings != null) {
                totalSavings += savings;
                count++;
            }
        }

        return count == 0 ? 0 : totalSavings / count;
    }
}
