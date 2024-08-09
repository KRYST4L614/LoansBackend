package com.example.demo.repository;

import com.example.demo.domain.model.Loan;
import com.example.demo.domain.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoansRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus status);
}
