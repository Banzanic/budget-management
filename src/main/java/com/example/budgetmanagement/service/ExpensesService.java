package com.example.budgetmanagement.service;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public ExpensesModel updateExpenses(ExpensesModel expensesModel, Long expenseId) {
        ExpensesModel depDB = expensesRepository.findById(expenseId).get();

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

        return expensesRepository.save(depDB);
    }

    public void deleteExpenses(Long expenseId) {
        expensesRepository.deleteById(expenseId);
    }
}
