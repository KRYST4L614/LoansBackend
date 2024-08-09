package com.example.demo.services;

import com.example.demo.domain.model.LoanConditions;
import com.example.demo.repository.LoanConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoanConditionsService {
    @Autowired
    private LoanConditionsRepository loanConditionsRepository;

    public LoanConditions getLoanConditions() {
        Random random = new Random();
        LoanConditions loanConditions = null;
        if (loanConditionsRepository.count() == 0) {
            loanConditions = LoanConditions.builder()
                    .percent(Math.round(random.nextDouble(3.0, 30.0) * 100) / 100.0)
                    .maxAmount(random.nextInt(5, 500) * 1000)
                    .period(random.nextInt(3, 90)).build();
            loanConditionsRepository.save(loanConditions);
        } else {
            loanConditions = loanConditionsRepository.findAll().get(0);
        }
        return loanConditions;
    }

    @Scheduled(fixedDelayString = "PT02H")
    @Async
    public void updateLoanConditions() {
        Random random = new Random();
        LoanConditions loanConditions = getLoanConditions();
        loanConditions.setMaxAmount(random.nextInt(5, 500) * 1000);
        loanConditions.setPeriod(random.nextInt(3, 90));
        loanConditions.setPercent(Math.round(random.nextDouble(3.0, 30.0) * 100) / 100.0);
        loanConditionsRepository.save(loanConditions);
    }
}
