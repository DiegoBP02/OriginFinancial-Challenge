package com.example.demo.services;

import com.example.demo.entities.InsuranceResult;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import com.example.demo.enums.Scores;
import com.example.demo.services.riskCalculation.IRiskCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private List<IRiskCalculation> riskCalculations;

    public InsuranceResult calculateRiskScore(User user) {
        RiskScore riskScore = getRiskScore(user);

        for (IRiskCalculation calculation : riskCalculations) {
            calculation.calculateRisk(user, riskScore);
        }

        deductPointsForAllInsurances(user, riskScore);

        InsuranceResult insuranceResult = getInsuranceResult(riskScore);

        setIneligibleInsurances(user, insuranceResult);

        return insuranceResult;
    }

    private void deductPointsForAllInsurances(User user, RiskScore riskScore) {
        if (user.getAge() < 30) riskScore.deductAll(2);
        if (user.getAge() >= 30 && user.getAge() <= 40) riskScore.deductAll(1);
        if (user.getIncome() > 200000) riskScore.deductAll(1);
    }

    private void setIneligibleInsurances(User user, InsuranceResult insuranceResult) {
        if (user.getIncome() == 0 || user.getAge() > 60) insuranceResult.setDisability(Scores.INELIGIBLE);
        if (user.getAge() > 60) insuranceResult.setLife(Scores.INELIGIBLE);
        if (Objects.isNull(user.getVehicle())) insuranceResult.setAuto(Scores.INELIGIBLE);
        if (Objects.isNull(user.getHouse())) insuranceResult.setHome(Scores.INELIGIBLE);
    }

    private InsuranceResult getInsuranceResult(RiskScore riskScore) {
        return InsuranceResult.builder()
                .auto(getFinalScore(riskScore.getAuto()))
                .life(getFinalScore(riskScore.getLife()))
                .disability(getFinalScore(riskScore.getDisability()))
                .home(getFinalScore(riskScore.getHome()))
                .build();
    }

    private RiskScore getRiskScore(User user) {
        int riskQuestionsScore = 0;
        for (boolean riskQuestion : user.getRiskQuestions()) {
            if (riskQuestion) {
                riskQuestionsScore++;
            }
        }
        return new RiskScore(riskQuestionsScore);
    }

    private Scores getFinalScore(int value) {
        if (value == 0) return Scores.ECONOMIC;
        if (value == 1 || value == 2) return Scores.REGULAR;
        return Scores.RESPONSIBLE;
    }
}
