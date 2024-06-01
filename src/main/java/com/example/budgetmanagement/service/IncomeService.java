package com.example.budgetmanagement.service;


import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public IncomeModel putIncome(IncomeModel incomeModel) {
        return incomeRepository.save(incomeModel);
    }


    public List<IncomeModel> getIncome() {
        return (List<IncomeModel>) incomeRepository.findAll();
    }

    public IncomeModel getLatestIncome(){
        List<IncomeModel> incomes = getIncome();
        IncomeModel incomeModel = null;
        for(IncomeModel income : incomes){
            if(incomeModel == null || income.getYear()>incomeModel.getYear() || (income.getYear().equals(incomeModel.getYear()) && Integer.parseInt(income.getMonth())>Integer.parseInt(incomeModel.getMonth()))){
                incomeModel = income;
            }
        }
        return incomeModel;
    }

    public Integer getLatestYear(){
        List<IncomeModel> incomes = getIncome();
        Integer year = 2010;
        for(IncomeModel income : incomes){
            if(income.getYear()>year){
                year=income.getYear();
            }
        }
        return year;
    }

    public Map<Integer, Integer> getYearlyIncomeSums(){
        List<IncomeModel> incomes = getIncome();
        Map<Integer, Integer> yearlyIncomes = new LinkedHashMap<>();
        for(int i = 2010; i<=2024;i++){
            yearlyIncomes.put(i, 0);
        }
        for(IncomeModel income : incomes){
            Integer year = income.getYear();
            int existingIncome = yearlyIncomes.get(year);
            int newIncome = existingIncome + income.getTotalIncome();
            yearlyIncomes.put(year, newIncome);
        }
        return yearlyIncomes;
    }

    public Map<String, Integer> getMonthlyIncomeSumsByYear(Integer year){
        List<IncomeModel> incomes = getIncome();
        Map<String, Integer> monthlyIncomes = new LinkedHashMap<>();
        for(int i =1;i <=12;i++){
            monthlyIncomes.put(String.valueOf(i), 0);
        }
        for(IncomeModel income : incomes){
            if(income.getYear().equals(year)){
                String month = income.getMonth();
                if(!monthlyIncomes.containsKey(month)){
                    monthlyIncomes.put(month, 0);
                }
                int existingIncome = monthlyIncomes.get(month);
                int newIncome = existingIncome + income.getTotalIncome();
                monthlyIncomes.put(month, newIncome);
            }
        }
        return monthlyIncomes;

    }

    public IncomeModel updateIncome(IncomeModel incomeModel, Long incomeId) {
        IncomeModel depDB = incomeRepository.findById(incomeId).get();

//        if (Objects.nonNull(expensesModel.getExpensesName()) && !"".equalsIgnoreCase(expensesModel.getExpensesName())) {
//            depDB.setExpensesName(expensesModel.getExpensesName());
//        }
//
//        if (Objects.nonNull(expensesModel.getExpensesAddress()) && !"".equalsIgnoreCase(expensesModel.getExpensesAddress())) {
//            depDB.setExpensesAddress(expensesModel.getExpensesAddress());
//        }
//
//        if (Objects.nonNull(expensesModel.getExpensesCode()) && !"".equalsIgnoreCase(expensesModeles.getExpensesCode())) {
//            depDB.setExpensesCode(expensesModel.getExpensesCode());
//        }

        return incomeRepository.save(depDB);
    }

    public void deleteIncome(Long incomeId) {
        incomeRepository.deleteById(incomeId);
    }
}
