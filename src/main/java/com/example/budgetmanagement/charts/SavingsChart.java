package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.service.SavingsService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class SavingsChart {

    @Autowired
    private SavingsService savingsService;

    public void generateBarChartYear() {
        Map<Integer, Integer> yearlySavings = savingsService.getYearlySavings();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : yearlySavings.entrySet()) {
            dataset.addValue(entry.getValue(), "Savings", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Savings",
                "Year",
                "Savings",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/yearly-savings-chart.jpg"), chart, 700, 400);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateBarChartMonth() {
        Map<String, Integer> monthlySavings = savingsService.getMonthlySavingsByYear();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : monthlySavings.entrySet()) {
            dataset.addValue(entry.getValue(), "Savings", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Savings",
                "Month",
                "Savings",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/monthly-savings-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}
