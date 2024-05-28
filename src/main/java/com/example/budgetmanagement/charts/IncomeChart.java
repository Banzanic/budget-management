package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.service.ExpensesService;
import com.example.budgetmanagement.service.IncomeService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;

public class IncomeChart {

    private IncomeService expensesService;

    //    public void generateExpensesChart()
    public void generateChart() {

//        List<ExpensesModel> expenses = expensesService.getExpenses();
//        ExpensesModel expense = expenses.get(0);
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Salary", 100);
        pieDataset.setValue("Investment", 10);
        pieDataset.setValue("Gift", 10);
        pieDataset.setValue("Interest", 5);
        JFreeChart chart = ChartFactory.createPieChart
                ("Income", // Title
                        pieDataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false // Configure chart to generate URLs?
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/static/charts/income-chart.jpg"), chart, 500, 300);
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}
