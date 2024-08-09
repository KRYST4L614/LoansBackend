package com.example.demo.repository;

import com.example.demo.domain.model.LoanConditions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanConditionsRepository extends JpaRepository<LoanConditions, Long> {
}
