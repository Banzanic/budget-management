package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.charts.ExpensesChart;
import com.example.budgetmanagement.charts.IncomeChart;
import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.model.SavingsGoalModel;
import com.example.budgetmanagement.service.ExpensesService;
import com.example.budgetmanagement.service.IncomeService;
import com.example.budgetmanagement.service.SavingsService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private IncomeChart incomeChart;

    @Autowired
    private ExpensesChart expensesChart;

    @Autowired
    private SavingsService savingsService;

    @PostMapping("/submitExpenses")
    public String submitExpenses(@ModelAttribute("expensesModel") ExpensesModel expensesModel, Model model) {
        if(model.getAttribute("incomeModel")==null) {
            model.addAttribute("incomeModel", new IncomeModel());
        }
        if(model.getAttribute("savingsGoalModel")==null){
            model.addAttribute("savingsGoalModel", new SavingsGoalModel());
        }
        System.out.println("Groceries: " + expensesModel.getGroceries() + ", Rent: " + expensesModel.getRent() + ", Transportation: " + expensesModel.getTransportation() + ", Subscriptions: " + expensesModel.getSubscriptions() + ", Health care: " + expensesModel.getHealthCare() + ", Entertainment: " + expensesModel.getEntertainment() + ", Debt: " + expensesModel.getDebt() + ", Year: " + expensesModel.getYear() + ", Month: " + expensesModel.getMonth());
        expensesService.putExpenses(expensesModel);
        expensesChart.generateBarChartYear();
        expensesChart.generateBarChartMonth();
        expensesChart.generateChart();

        return "functionality";

    }

    @PostMapping("/submitIncome")
    public String submitIncome(@ModelAttribute("incomeModel") IncomeModel incomeModel, Model model) {
        if(model.getAttribute("expensesModel")==null) {
            model.addAttribute("expensesModel", new ExpensesModel());
        }
        if(model.getAttribute("savingsGoalModel")==null){
            model.addAttribute("savingsGoalModel", new SavingsGoalModel());
        }
        System.out.println("Groceries: " + incomeModel.getSalary() + ", Investment: " + incomeModel.getInvestment() + ", Gift: " + incomeModel.getGift() + ", Interest: " + incomeModel.getInterest() + ", Rental: " + incomeModel.getRental() + ", Sales: " + incomeModel.getSales() + ", Year: " + incomeModel.getYear() + ", Month: " + incomeModel.getMonth());
        incomeService.putIncome(incomeModel);
        incomeChart.generateBarChartYear();
        incomeChart.generateBarChartMonth();
        incomeChart.generateChart();
        return "functionality";
    }

    @PostMapping("/setSavingsGoal")
    public String setSavingsGoal(@ModelAttribute("savingsGoalModel") SavingsGoalModel savingsGoalModel, Model model, HttpSession session) {
        System.out.println(savingsService);
        System.out.println("GoalName: " + savingsGoalModel.getGoalName() + ", GoalAmount: " + savingsGoalModel.getGoalAmount());
        savingsGoalModel.setSavedAmount(savingsService.getSummedSavings());
        double progressPercentage = 0;
        if(savingsGoalModel.getGoalAmount()!=null && savingsGoalModel.getGoalAmount()!=0) {
            progressPercentage = (savingsService.getSummedSavings() * 100) / savingsGoalModel.getGoalAmount();
            model.addAttribute("progressPercentage", progressPercentage);
        } else {
            model.addAttribute("progressPercentage", progressPercentage);
        }
        savingsService.putSavingsGoal(savingsGoalModel);
        session.setAttribute("savingsGoalModel", savingsGoalModel);


        return "savings";
    }
}
