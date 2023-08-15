package com.example.demo.services.riskCalculation;

import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DisabilityRiskCalculation implements IRiskCalculation{
    @Override
    public void calculateRisk(User user, RiskScore riskScore) {
        if (user.getMaritalStatus().equals(User.MaritalStatus.MARRIED)) {
            riskScore.setDisability(riskScore.getDisability() - 1);
        }

        if(user.getDependents() > 0) {
            riskScore.setDisability(riskScore.getDisability() + 1);
        }

        if (!Objects.isNull(user.getHouse())
                && user.getHouse().getOwnershipStatus().equals(User.OwnershipStatus.MORTGAGED)) {
            riskScore.setDisability(riskScore.getDisability() + 1);
        }
    }
}
