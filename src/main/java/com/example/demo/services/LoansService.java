package com.example.demo.services;

import com.example.demo.domain.dto.AddLoanRequest;
import com.example.demo.domain.model.Loan;
import com.example.demo.domain.model.LoanStatus;
import com.example.demo.domain.model.User;
import com.example.demo.repository.LoansRepository;
import com.example.demo.repository.UserRepository;
import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class LoansService {
    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public Loan addLoan(@NonNull User user, @NonNull AddLoanRequest loan) {
        LoanStatus[] statuses = LoanStatus.values();
        Random random = new Random();
        Loan newLoan = Loan.builder()
                .amount(loan.getAmount())
                .date(Date.from(Instant.now()))
                .period(loan.getPeriod())
                .percent(loan.getPercent())
                .firstName(loan.getFirstName())
                .lastName(loan.getLastName())
                .phoneNumber(loan.getPhoneNumber())
                .status(statuses[random.nextInt(statuses.length)]).build();
        user.addLoan(newLoan);
        userRepository.save(user);
        return newLoan;
    }

    public List<Loan> getAllLoans(@NonNull User user) {
        return user.getLoans();
    }

    public Loan getLoanById(long id) {
        return loansRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan with this id not found"));
    }

    @Scheduled(fixedDelayString = "PT03M")
    @Async
    public void updateLoansStatus() {
        List<Loan> loans = loansRepository.findByStatus(LoanStatus.REGISTERED);
        loans.forEach((loan -> {
            loan.setStatus(LoanStatus.APPROVED);
        }));
        loansRepository.saveAll(loans);
    }
}
