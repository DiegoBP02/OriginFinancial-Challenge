package com.example.demo.services.riskCalculation;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AutoRiskCalculationTest extends ApplicationConfigTest {

    @Autowired
    private AutoRiskCalculation autoRiskCalculation;

    RiskScore riskScore = new RiskScore(0);
    User user = new User();

    @Test
    void givenUserVehicleWasProducedInTheLast5Years_whenCalculateRiskScore_thenAdd1RiskPointToVehicle() {
        user.setVehicle(User.Vehicle.builder().year(Year.now().getValue()).build());
        autoRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getAuto());
    }

}