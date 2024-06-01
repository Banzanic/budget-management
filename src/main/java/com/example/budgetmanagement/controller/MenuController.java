package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.charts.ExpensesChart;
import com.example.budgetmanagement.charts.IncomeChart;
import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import com.example.budgetmanagement.service.ExpensesService;
import com.example.budgetmanagement.service.IncomeService;
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
    private IncomeChart incomeChart;

    @Autowired
    private ExpensesChart expensesChart;

    @GetMapping("/")
    public String startApplication(Model model) {
        model.addAttribute("expensesModel", new ExpensesModel());
        model.addAttribute("incomeModel", new IncomeModel());
        model.addAttribute("currentYear", Integer.toString(LocalDate.now().getYear()));
        model.addAttribute("currentMonth", LocalDate.now().getMonth());
        incomeChart.generateBarChartYear();
        incomeChart.generateBarChartMonth();
        incomeChart.generateChart();
        expensesChart.generateBarChartYear();
        expensesChart.generateBarChartMonth();
        expensesChart.generateChart();
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
    public String getSavings(Model model){
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
