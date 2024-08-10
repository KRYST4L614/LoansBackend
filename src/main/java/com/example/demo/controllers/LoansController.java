package com.example.demo.controllers;

import com.example.demo.domain.dto.AddLoanRequest;
import com.example.demo.domain.dto.LoanConditionsResponse;
import com.example.demo.domain.model.Loan;
import com.example.demo.domain.model.LoanConditions;
import com.example.demo.services.LoanConditionsService;
import com.example.demo.services.LoansService;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(name = "Loans API")
public class LoansController {
    @Autowired
    private LoansService loansService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanConditionsService loanConditionsService;

    @PostMapping("/add")
    @Operation(description = "Add loan to user")
    public Loan add(@RequestBody AddLoanRequest loan) {
        return loansService.addLoan(userService.getCurrentUser(), loan);
    }

    @GetMapping("/all")
    @Operation(description = "Get all loans by user")
    public List<Loan> all() {
        return loansService.getAllLoans(userService.getCurrentUser());
    }

    @GetMapping("/conditions")
    @Operation(description = "Get conditions for loans")
    public LoanConditionsResponse conditions() {
        LoanConditions loanConditions = loanConditionsService.getLoanConditions();
        return LoanConditionsResponse.builder()
                .percent(loanConditions.getPercent())
                .period(loanConditions.getPeriod())
                .maxAmount(loanConditions.getMaxAmount()).build();
    }

}
