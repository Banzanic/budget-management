package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.service.ExpensesService;
import com.example.budgetmanagement.service.IncomeService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

@Component
public class ExpensesChart {

    @Autowired
    private ExpensesService expensesService;


    public void generateBarChartYear(){
        Map<Integer, Integer> yearlyExpenses = expensesService.getYearlyExpenseSums();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Map.Entry<Integer, Integer> entry : yearlyExpenses.entrySet()){
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Expenses",        // chart title
                "Year",                 // domain axis label
                "Expenses",                // range axis label
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

    public void generateBarChartMonth(){
        Map<String, Integer> monthlyExpenses = expensesService.getMonthlyExpenseSumsByYear(expensesService.getLatestYear());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Map.Entry<String, Integer> entry : monthlyExpenses.entrySet()){
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Expenses",        // chart title
                "Month",                 // domain axis label
                "Expenses",                // range axis label
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
        List<ExpensesModel> expenses = expensesService.getExpenses();
        ExpensesModel expense = expenses.get(0);
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("groceries", expense.getGroceries());
        pieDataset.setValue("rent", expense.getRent());
        pieDataset.setValue("transportation", expense.getTransportation());
        pieDataset.setValue("subscriptions", expense.getSubscriptions());
        pieDataset.setValue("health care", expense.getHealthCare());
        pieDataset.setValue("entertainment", expense.getEntertainment());
        pieDataset.setValue("debt", expense.getDebt());
        JFreeChart chart = ChartFactory.createPieChart
                ("Expenses", // Title
                        pieDataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false // Configure chart to generate URLs?
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/expenses-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}

