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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

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
        if (model.getAttribute("incomeModel") == null) {
            model.addAttribute("incomeModel", new IncomeModel());
        }
        if (model.getAttribute("savingsGoalModel") == null) {
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
        if (model.getAttribute("expensesModel") == null) {
            model.addAttribute("expensesModel", new ExpensesModel());
        }
        if (model.getAttribute("savingsGoalModel") == null) {
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
        double monthlySavings = 1;
        String estimatedGoalDate = "";
        double requiredMonthlySavings = 1;
        if (savingsGoalModel.getGoalAmount() != null && savingsGoalModel.getGoalAmount() != 0) {
            progressPercentage = (savingsService.getSummedSavings() * 100) / savingsGoalModel.getGoalAmount();
            monthlySavings = savingsService.getAverageMonthlySavings();
            estimatedGoalDate = getEstimatedGoalDate(savingsGoalModel, monthlySavings);
            requiredMonthlySavings = (savingsGoalModel.getGoalAmount() - savingsService.getSummedSavings()) / 12;

            model.addAttribute("progressPercentage", progressPercentage);
            model.addAttribute("averageMonthlySavings", monthlySavings);
            model.addAttribute("estimatedGoalDate", estimatedGoalDate);
            model.addAttribute("requiredMonthlySavings", requiredMonthlySavings);
        } else {
            model.addAttribute("progressPercentage", progressPercentage);
            model.addAttribute("averageMonthlySavings", monthlySavings);
            model.addAttribute("estimatedGoalDate", estimatedGoalDate);
            model.addAttribute("requiredMonthlySavings", requiredMonthlySavings);
        }
        System.out.println("monthly savings: " + monthlySavings);

        savingsService.putSavingsGoal(savingsGoalModel);
        session.setAttribute("savingsGoalModel", savingsGoalModel);

        return "savings";
    }

    private String getEstimatedGoalDate(SavingsGoalModel savingsGoalModel, double monthlySavings) {
        int remainingAmount = savingsGoalModel.getGoalAmount() - savingsService.getSummedSavings();

        double monthsToGoal = Math.ceil(remainingAmount / monthlySavings);

        LocalDate currentDate = LocalDate.now();
        LocalDate goalDate = currentDate.plus((long) monthsToGoal, ChronoUnit.MONTHS);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

        return goalDate.format(formatter);
    }
}
