package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.service.ExpensesService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.util.List;

public class ExpensesChart {

    private ExpensesService expensesService;

    //    public void generateExpensesChart()
    public void generateChart() {

//        List<ExpensesModel> expenses = expensesService.getExpenses();
//        ExpensesModel expense = expenses.get(0);
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("groceries", 100);
        pieDataset.setValue("rent", 10);
        pieDataset.setValue("transportation", 10);
        pieDataset.setValue("subscriptions", 5);
        JFreeChart chart = ChartFactory.createPieChart
                ("Expenses", // Title
                        pieDataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        true // Configure chart to generate URLs?
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/static/charts/expenses-chart.jpg"), chart, 500, 300);
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}

