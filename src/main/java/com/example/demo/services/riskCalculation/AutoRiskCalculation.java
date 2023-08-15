package com.example.demo.services.riskCalculation;

import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import com.example.demo.enums.Scores;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Objects;

@Service
public class AutoRiskCalculation implements IRiskCalculation{
    @Override
    public void calculateRisk(User user, RiskScore riskScore) {
        if (!Objects.isNull(user.getVehicle())
                && Year.now().getValue() - user.getVehicle().getYear() <= 5) {
            riskScore.setAuto(riskScore.getAuto() + 1);
        }
    }
}
