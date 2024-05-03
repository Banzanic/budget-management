package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.service.ExpensesService;
import com.example.budgetmanagement.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SubmitController {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/submitExpenses")
    public String submitExpenses(@ModelAttribute("expensesModel") ExpensesModel expensesModel, Model model) {
        model.addAttribute("incomeModel", new IncomeModel());
        System.out.println("Groceries: " + expensesModel.getGroceries() + ", Rent: " + expensesModel.getRent() + ", Transportation: " + expensesModel.getTransportation() + ", Subscriptions: " + expensesModel.getSubscriptions() + ", Health care: " + expensesModel.getHealthCare() + ", Entertainment: " + expensesModel.getEntertainment() + ", Debt: " + expensesModel.getDebt() + ", Year: " + expensesModel.getYear() + ", Month: " + expensesModel.getMonth());
        expensesService.putExpenses(expensesModel);

        return "functionality";

    }

    @PostMapping("/submitIncome")
    public String submitIncome(@ModelAttribute("incomeModel") IncomeModel incomeModel, Model model) {
        model.addAttribute("expensesModel", new ExpensesModel());
        System.out.println("Groceries: " + incomeModel.getSalary() + ", Investment: " + incomeModel.getInvestment() + ", Gift: " + incomeModel.getGift() + ", Interest: " + incomeModel.getInterest() + ", Rental: " + incomeModel.getRental() + ", Sales: " + incomeModel.getSales() + ", Year: " + incomeModel.getYear() + ", Month: " + incomeModel.getMonth());
        incomeService.putIncome(incomeModel);
        return "functionality";
    }
}
