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
import java.util.List;
import java.util.Map;

@Component
public class IncomeChart {

    @Autowired
    private IncomeService incomeService;

    public void generateBarChartYear(){
        Map<Integer, Integer> yearlyIncomes = incomeService.getYearlyIncomeSums();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Map.Entry<Integer, Integer> entry : yearlyIncomes.entrySet()){
            dataset.addValue(entry.getValue(), "Income", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Yearly Income",        // chart title
                "Year",                 // domain axis label
                "Income",                // range axis label
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

    public void generateBarChartMonth(){
        Map<String, Integer> monthlyIncomes = incomeService.getMonthlyIncomeSumsByYear(incomeService.getLatestYear());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Map.Entry<String, Integer> entry : monthlyIncomes.entrySet()){
            dataset.addValue(entry.getValue(), "Income", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Income",        // chart title
                "Month",                 // domain axis label
                "Income",                // range axis label
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
        List<IncomeModel> incomes = incomeService.getIncome();
        IncomeModel income = incomes.get(0);
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Salary", income.getSalary());
        pieDataset.setValue("Investment", income.getInvestment());
        pieDataset.setValue("Gift", income.getGift());
        pieDataset.setValue("Interest", income.getInterest());
        pieDataset.setValue("Rental", income.getRental());
        pieDataset.setValue("Sales", income.getSales());
        JFreeChart chart = ChartFactory.createPieChart
                ("Income", // Title
                        pieDataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false // Configure chart to generate URLs?
                );
        try {
            ChartUtilities.saveChartAsJPEG(new File("src/main/resources/charts/income-chart.jpg"), chart, 500, 300);
            System.out.println("Chart Updated");
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }
}
