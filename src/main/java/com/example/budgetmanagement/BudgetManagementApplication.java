package com.example.budgetmanagement;

import com.example.budgetmanagement.charts.ExpensesChart;
import com.example.budgetmanagement.charts.IncomeChart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagementApplication.class, args);
	}
}
