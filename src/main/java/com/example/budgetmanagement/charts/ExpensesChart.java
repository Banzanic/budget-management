package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.service.ExpensesService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class ExpensesChart {

    @Autowired
    private ExpensesService expensesService;


    public void generateBarChartYear() {
        Map<Integer, Integer> yearlyExpenses = expensesService.getYearlyExpenseSums();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : yearlyExpenses.entrySet()) {
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Expenses",
                "Year",
                "Expenses",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/yearly-expenses-chart.jpg"), chart, 700, 400);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateBarChartMonth() {
        Map<String, Integer> monthlyExpenses = expensesService.getMonthlyExpenseSumsByYear(expensesService.getLatestYear());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : monthlyExpenses.entrySet()) {
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Expenses",
                "Month",
                "Expenses",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/monthly-expenses-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateChart() {
        ExpensesModel expense = expensesService.getLatestExpense();
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        if (expense != null) {
            if (expense.getGroceries() != null) {
                pieDataset.setValue("groceries", expense.getGroceries());
            }
            if (expense.getRent() != null) {
                pieDataset.setValue("rent", expense.getRent());
            }
            if (expense.getTransportation() != null) {
                pieDataset.setValue("transportation", expense.getTransportation());
            }
            if (expense.getSubscriptions() != null) {
                pieDataset.setValue("subscriptions", expense.getSubscriptions());
            }
            if (expense.getHealthCare() != null) {
                pieDataset.setValue("health care", expense.getHealthCare());
            }
            if (expense.getEntertainment() != null) {
                pieDataset.setValue("entertainment", expense.getEntertainment());
            }
            if (expense.getDebt() != null) {
                pieDataset.setValue("debt", expense.getDebt());
            }
        }
        JFreeChart chart = ChartFactory.createPieChart
                ("Expenses",
                        pieDataset,
                        true,
                        true,
                        false
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/expenses-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}

