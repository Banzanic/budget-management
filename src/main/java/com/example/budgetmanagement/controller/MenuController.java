package com.example.budgetmanagement.controller;

import com.example.budgetmanagement.model.ExpensesModel;
import com.example.budgetmanagement.model.IncomeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class MenuController {

    @GetMapping({"/", "/home"})
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

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/settings")
    public String getSettings() {
        return "settings";
    }
}
