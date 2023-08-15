package com.example.demo.services.riskCalculation;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class LifeRiskCalculationTest extends ApplicationConfigTest {

    @Autowired
    private LifeRiskCalculation lifeRiskCalculation;

    RiskScore riskScore = new RiskScore(0);
    User user = new User();

    @Test
    void givenUserHasDependents_whenCalculateRisk_thenAdd1RiskPointToLife() {
        user.setMaritalStatus(User.MaritalStatus.SINGLE);
        user.setDependents(1);
        lifeRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getLife());
    }

    @Test
    void givenUserIsMarried_whenCalculateRisk_thenAdd1RiskPointToLife() {
        user.setMaritalStatus(User.MaritalStatus.MARRIED);
        user.setDependents(0);
        lifeRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getLife());
    }

}