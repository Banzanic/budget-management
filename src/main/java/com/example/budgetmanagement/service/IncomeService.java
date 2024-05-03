package com.example.budgetmanagement.service;


import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
