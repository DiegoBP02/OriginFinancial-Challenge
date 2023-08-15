package com.example.demo.services.riskCalculation;

import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

@Service
public class LifeRiskCalculation implements IRiskCalculation{

    @Override
    public void calculateRisk(User user, RiskScore riskScore) {
        if (user.getMaritalStatus().equals(User.MaritalStatus.MARRIED)) {
            riskScore.setLife(riskScore.getLife() + 1);
        }

        if (user.getDependents() > 0) {
            riskScore.setLife(riskScore.getLife() + 1);
        }
    }
}
