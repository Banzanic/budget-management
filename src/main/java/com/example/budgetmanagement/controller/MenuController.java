package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.charts.ExpensesChart;
import com.example.budgetmanagement.charts.IncomeChart;
import com.example.budgetmanagement.charts.SavingsChart;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
        model.addAttribute("savingsGoalModel", new SavingsGoalModel());
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
    public String getExpensesArchive(Model model) {
        List<ExpensesModel> archivedExpenses = expensesService.getExpenses();
        archivedExpenses.sort(Collections.reverseOrder());

        model.addAttribute("expenses", archivedExpenses);
        String[] monthNames = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        model.addAttribute("monthNames", monthNames);
        return "expensesArchive";
    }

    @GetMapping("/incomeArchive")
    public String getIncomeArchive(Model model) {
        List<IncomeModel> archivedIncomes = incomeService.getIncome();
        archivedIncomes.sort(Collections.reverseOrder());

        model.addAttribute("incomes", archivedIncomes);
        String[] monthNames = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        model.addAttribute("monthNames", monthNames);
        return "incomeArchive";
    }

    @GetMapping("/savings")
    public String getSavings(HttpSession session, Model model) {
        SavingsGoalModel currentSavingsGoal = (SavingsGoalModel) session.getAttribute("savingsGoalModel");
        model.addAttribute("savingsGoalModel", currentSavingsGoal != null ? currentSavingsGoal : new SavingsGoalModel());
        model.addAttribute("currentYear", Integer.toString(LocalDate.now().getYear()));
        model.addAttribute("currentMonth", LocalDate.now().getMonth());

        SavingsGoalModel savingsGoalModel = (SavingsGoalModel) model.getAttribute("savingsGoalModel");
        savingsGoalModel.setSavedAmount(savingsService.getSummedSavings());

        double progressPercentage = 0;
        double monthlySavings = savingsService.getAverageMonthlySavings();
        String estimatedGoalDate = "";
        double requiredMonthlySavings = 0;
        if (savingsGoalModel.getGoalAmount() != null && savingsGoalModel.getGoalAmount() != 0) {
            if (monthlySavings > 0) {
                estimatedGoalDate = getEstimatedGoalDate(savingsGoalModel, monthlySavings);
            }
            if (savingsGoalModel.getYearDesiredDate() != null && savingsGoalModel.getMonthDesiredDate() != null) {
                requiredMonthlySavings = getRequiredMonthlySavings(savingsGoalModel);
            }
            progressPercentage = (savingsService.getSummedSavings() * 100) / savingsGoalModel.getGoalAmount();

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
        model.addAttribute("savingsGoalModel", savingsGoalModel);
        savingsChart.generateBarChartYear();
        savingsChart.generateBarChartMonth();

        System.out.println("Goal Name:" + savingsGoalModel.getGoalName());
        System.out.println("Goal Amount: " + savingsGoalModel.getGoalAmount());
        System.out.println("Saved amount: " + savingsGoalModel.getSavedAmount());
        System.out.println("Progress Percentage: " + progressPercentage);
        System.out.println("Required monthly savings: " + requiredMonthlySavings);

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

    private double getRequiredMonthlySavings(SavingsGoalModel savingsGoalModel) {
        LocalDate targetDate = LocalDate.of(savingsGoalModel.getYearDesiredDate(), Integer.parseInt(savingsGoalModel.getMonthDesiredDate()), 1);
        long monthsRemaining = ChronoUnit.MONTHS.between(LocalDate.now().withDayOfMonth(1), targetDate.withDayOfMonth(1));

        return (savingsGoalModel.getGoalAmount() - savingsService.getSummedSavings()) / monthsRemaining;
    }
}
