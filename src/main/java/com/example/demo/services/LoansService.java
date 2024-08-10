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
import java.util.*;

@Service
public class LoansService {
    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public Loan addLoan(@NonNull User user, @NonNull AddLoanRequest loan) {
        LoanStatus[] statuses = new LoanStatus[]{LoanStatus.REJECTED, LoanStatus.REGISTERED};
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(Instant.now()));
        calendar.add(Calendar.DATE, random.nextInt(2, loan.getPeriod()));
        Date issueDate = calendar.getTime();
        Loan newLoan = Loan.builder()
                .amount(loan.getAmount())
                .date(Date.from(Instant.now()))
                .issueDate(issueDate)
                .period(loan.getPeriod())
                .percent(loan.getPercent())
                .firstName(loan.getFirstName())
                .lastName(loan.getLastName())
                .phoneNumber(loan.getPhoneNumber())
                .status(statuses[random.nextInt(statuses.length)]).build();
        if (newLoan.getStatus() != LoanStatus.REJECTED) {
            user.addLoan(newLoan);
            userRepository.save(user);
        }
        return newLoan;
    }

    public List<Loan> getAllLoans(@NonNull User user) {
        return user.getLoans();
    }

    public Loan getLoanById(long id) {
        return loansRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan with this id not found"));
    }

    @Scheduled(fixedDelayString = "PT15M")
    @Async
    public void updateLoansStatus() {
        List<Loan> loans = loansRepository.findByStatus(LoanStatus.REGISTERED);
        loans.forEach((loan -> {
            loan.setStatus(LoanStatus.APPROVED);
        }));
        loansRepository.saveAll(loans);
    }
}
