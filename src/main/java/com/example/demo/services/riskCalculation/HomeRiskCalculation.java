package com.example.demo.services.riskCalculation;

import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HomeRiskCalculation implements IRiskCalculation{
    @Override
    public void calculateRisk(User user, RiskScore riskScore) {
        if (!Objects.isNull(user.getHouse())
                && user.getHouse().getOwnershipStatus().equals(User.OwnershipStatus.MORTGAGED)) {
            riskScore.setHome(riskScore.getHome() + 1);
        }
    }
}
