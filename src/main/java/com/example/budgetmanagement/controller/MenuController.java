package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.charts.SavingsChart;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private IncomeChart incomeChart;

    @Autowired
    private ExpensesChart expensesChart;

    @Autowired
    private SavingsChart savingsChart;

    @GetMapping("/")
    public String startApplication(Model model) {
        model.addAttribute("expensesModel", new ExpensesModel());
        model.addAttribute("incomeModel", new IncomeModel());
        model.addAttribute("currentYear", Integer.toString(LocalDate.now().getYear()));
        model.addAttribute("currentMonth", LocalDate.now().getMonth());
        model.addAttribute("savingsGoalModel",new SavingsGoalModel());
        incomeChart.generateBarChartYear();
        incomeChart.generateBarChartMonth();
        incomeChart.generateChart();
        expensesChart.generateBarChartYear();
        expensesChart.generateBarChartMonth();
        expensesChart.generateChart();
        savingsChart.generateBarChartYear();
        savingsChart.generateBarChartMonth();
        return "home";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("/functionality")
    public String getFunctionality(Model model) {
        model.addAttribute("expensesModel", new ExpensesModel());
        model.addAttribute("incomeModel", new IncomeModel());

        model.addAttribute("currentYear", Integer.toString(LocalDate.now().getYear()));
        model.addAttribute("currentMonth", LocalDate.now().getMonth());

        return "functionality";
    }

    @GetMapping("/expensesArchive")
    public String getExpensesArchive(Model model){
        List<ExpensesModel> archivedExpenses = expensesService.getExpenses();
        model.addAttribute("expenses", archivedExpenses);
        return "expensesArchive";
    }

    @GetMapping("/incomeArchive")
    public String getIncomeArchive(Model model){
        List<IncomeModel> archivedIncomes = incomeService.getIncome();
        model.addAttribute("incomes", archivedIncomes);
        return "incomeArchive";
    }

    @GetMapping("/savings")
    public String getSavings(HttpSession session, Model model){
        SavingsGoalModel currentSavingsGoal = (SavingsGoalModel) session.getAttribute("savingsGoalModel");
        model.addAttribute("savingsGoalModel", currentSavingsGoal!=null? currentSavingsGoal : new SavingsGoalModel());

        SavingsGoalModel savingsGoalModel = (SavingsGoalModel) model.getAttribute("savingsGoalModel");
        savingsGoalModel.setSavedAmount(savingsService.getSummedSavings());
        double progressPercentage = 0;

        if(savingsGoalModel.getGoalAmount()!=null && savingsGoalModel.getGoalAmount()!=0) {
            progressPercentage = (savingsService.getSummedSavings() * 100) / savingsGoalModel.getGoalAmount();
            model.addAttribute("progressPercentage", progressPercentage);
        } else {
            model.addAttribute("progressPercentage", progressPercentage);
        }
        model.addAttribute("savingsGoalModel", savingsGoalModel);
        savingsChart.generateBarChartYear();
        savingsChart.generateBarChartMonth();

        System.out.println(savingsGoalModel.getGoalName());
        System.out.println(savingsGoalModel.getGoalAmount());
        System.out.println(savingsGoalModel.getSavedAmount());
        System.out.println(progressPercentage);

        return "savings";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/settings")
    public String getSettings() {
        return "settings";
    }
}
