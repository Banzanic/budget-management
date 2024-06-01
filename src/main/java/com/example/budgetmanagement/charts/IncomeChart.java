package com.example.budgetmanagement.charts;

import com.example.budgetmanagement.model.IncomeModel;
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
import java.util.Map;

@Component
public class IncomeChart {

    @Autowired
    private IncomeService incomeService;

    public void generateBarChartYear() {
        Map<Integer, Integer> yearlyIncomes = incomeService.getYearlyIncomeSums();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Integer, Integer> entry : yearlyIncomes.entrySet()) {
            dataset.addValue(entry.getValue(), "Income", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Income",
                "Year",
                "Income",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/yearly-income-chart.jpg"), chart, 700, 400);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateBarChartMonth() {
        Map<String, Integer> monthlyIncomes = incomeService.getMonthlyIncomeSumsByYear(incomeService.getLatestYear());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : monthlyIncomes.entrySet()) {
            dataset.addValue(entry.getValue(), "Income", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Income",
                "Month",
                "Income",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/monthly-income-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }

    public void generateChart() {
        IncomeModel income = incomeService.getLatestIncome();
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        if (income != null) {
            if (income.getSalary() != null) {
                pieDataset.setValue("Salary", income.getSalary());
            }
            if (income.getInvestment() != null) {
                pieDataset.setValue("Investment", income.getInvestment());
            }
            if (income.getGift() != null) {
                pieDataset.setValue("Gift", income.getGift());
            }
            if (income.getInterest() != null) {
                pieDataset.setValue("Interest", income.getInterest());
            }
            if (income.getRental() != null) {
                pieDataset.setValue("Rental", income.getRental());
            }
            if (income.getSales() != null) {
                pieDataset.setValue("Sales", income.getSales());
            }
        }
        JFreeChart chart = ChartFactory.createPieChart
                ("Income",
                        pieDataset,
                        true,
                        true,
                        false
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/income-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}
