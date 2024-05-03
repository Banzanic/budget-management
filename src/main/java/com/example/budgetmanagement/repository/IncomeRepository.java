package com.example.budgetmanagement.repository;

import com.example.budgetmanagement.model.IncomeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends CrudRepository<IncomeModel, Long> {
}
