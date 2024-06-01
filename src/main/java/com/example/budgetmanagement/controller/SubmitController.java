package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.charts.ExpensesChart;
import com.example.budgetmanagement.charts.IncomeChart;
import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.model.SavingsGoalModel;
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

    @Autowired
    private IncomeChart incomeChart;

    @Autowired
    private ExpensesChart expensesChart;

    @Autowired
    private SavingsGoalModel savingsGoal;

    @PostMapping("/submitExpenses")
    public String submitExpenses(@ModelAttribute("expensesModel") ExpensesModel expensesModel, Model model) {
        if(model.getAttribute("incomeModel")==null) {
            model.addAttribute("incomeModel", new IncomeModel());
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
        System.out.println("Groceries: " + incomeModel.getSalary() + ", Investment: " + incomeModel.getInvestment() + ", Gift: " + incomeModel.getGift() + ", Interest: " + incomeModel.getInterest() + ", Rental: " + incomeModel.getRental() + ", Sales: " + incomeModel.getSales() + ", Year: " + incomeModel.getYear() + ", Month: " + incomeModel.getMonth());
        incomeService.putIncome(incomeModel);
        incomeChart.generateBarChartYear();
        incomeChart.generateBarChartMonth();
        incomeChart.generateChart();
        return "functionality";
    }

    @PostMapping("/setSavingsGoal")
    public String setSavingsGoal(@ModelAttribute("savingsGoalModel") SavingsGoalModel savingsGoalModel, Model model) {
        System.out.println("GoalName: " + savingsGoalModel.getGoalName() + ", GoalAmount: " + savingsGoalModel.getGoalAmount());
        savingsGoal.setGoalName(savingsGoalModel.getGoalName());
        savingsGoal.setGoalAmount(savingsGoalModel.getGoalAmount());

        int savedAmount = 10;  // zmienić to
        double progressPercentage = (savedAmount  * 100) / savingsGoalModel.getGoalAmount();
        System.out.println("ProgressPercentage: " + progressPercentage);

        model.addAttribute("savingsGoalModel", savingsGoalModel);
        model.addAttribute("progressPercentage", progressPercentage);

        return "savings";
    }
}
