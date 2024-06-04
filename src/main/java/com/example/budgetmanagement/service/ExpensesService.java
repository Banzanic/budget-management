package com.example.budgetmanagement.service;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    public ExpensesModel putExpenses(ExpensesModel expensesModel) {
        return expensesRepository.save(expensesModel);
    }


    public List<ExpensesModel> getExpenses() {
        return (List<ExpensesModel>) expensesRepository.findAll();
    }

    public ExpensesModel getLatestExpense() {
        List<ExpensesModel> expenses = getExpenses();
        ExpensesModel expensesModel = null;
        for (ExpensesModel expense : expenses) {
            if (expensesModel == null || expense.getYear() > expensesModel.getYear() || (expense.getYear().equals(expensesModel.getYear()) && Integer.parseInt(expense.getMonth()) > Integer.parseInt(expensesModel.getMonth()))) {
                expensesModel = expense;
            }
        }
        return expensesModel;
    }

    public Integer getLatestYear() {
        List<ExpensesModel> expenses = getExpenses();
        Integer year = 2010;
        for (ExpensesModel expense : expenses) {
            if (expense.getYear() > year) {
                year = expense.getYear();
            }
        }
        return year;
    }

    public Map<Integer, Integer> getYearlyExpenseSums() {
        List<ExpensesModel> expenses = getExpenses();
        Map<Integer, Integer> yearlyExpenses = new LinkedHashMap<>();
        for (int i = 2010; i <= 2024; i++) {
            yearlyExpenses.put(i, 0);
        }
        for (ExpensesModel expense : expenses) {
            Integer year = expense.getYear();
            int existingExpense = yearlyExpenses.get(year);
            int newExpense = existingExpense + expense.getTotalExpense();
            yearlyExpenses.put(year, newExpense);
        }
        return yearlyExpenses;
    }

    public Map<String, Integer> getMonthlyExpenseSumsByYear(Integer year) {
        List<ExpensesModel> expenses = getExpenses();
        Map<String, Integer> monthlyExpenses = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyExpenses.put(String.valueOf(i), 0);
        }
        for (ExpensesModel expense : expenses) {
            if (expense.getYear().equals(year)) {
                String month = expense.getMonth();
                if (!monthlyExpenses.containsKey(month)) {
                    monthlyExpenses.put(month, 0);
                }
                int existingExpense = monthlyExpenses.get(month);
                int newExpense = existingExpense + expense.getTotalExpense();
                monthlyExpenses.put(month, newExpense);
            }
        }
        return monthlyExpenses;

    }
}
