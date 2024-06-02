package com.example.budgetmanagement.repository;

import com.example.budgetmanagement.model.SavingsGoalModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsGoalRepository extends CrudRepository<SavingsGoalModel, Long> {
}
