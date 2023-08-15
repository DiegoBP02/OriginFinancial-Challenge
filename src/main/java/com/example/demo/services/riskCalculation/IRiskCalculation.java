package com.example.demo.services.riskCalculation;

import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import com.example.demo.enums.Scores;

public interface IRiskCalculation {
    void calculateRisk(User user, RiskScore riskScore);
}
