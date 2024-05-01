package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitController {

    @PostMapping("/submitExpenses")
    public void submitExpenses(@ModelAttribute("expensesModel") ExpensesModel expensesModel) {
        System.out.println(expensesModel);
        System.out.println("Groceries: " + expensesModel.getGroceries() + ", Rent: " + expensesModel.getRent() + ", Transportation: " + expensesModel.getTransportation() + ", Subscriptions: " + expensesModel.getSubscriptions() + ", Health care: " + expensesModel.getHealthCare() + ", Entertainment: " + expensesModel.getEntertainment() + ", Debt: " + expensesModel.getDebt() + ", Year: " + expensesModel.getYear() + ", Month: " + expensesModel.getMonth());

    }

    @PostMapping("/submitIncome")
    public void submitIncome(@ModelAttribute("incomeModel") IncomeModel incomeModel) {
        System.out.println(incomeModel);
        System.out.println("Groceries: " + incomeModel.getSalary() + ", Investment: " + incomeModel.getInvestment() + ", Gift: " + incomeModel.getGift() + ", Interest: " + incomeModel.getInterest() + ", Rental: " + incomeModel.getRental() + ", Sales: " + incomeModel.getSales() + ", Year: " + incomeModel.getYear() + ", Month: " + incomeModel.getMonth());
    }
}
