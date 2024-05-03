package com.example.budgetmanagement.repository;

import com.example.budgetmanagement.model.ExpensesModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends CrudRepository<ExpensesModel, Long> {
}
