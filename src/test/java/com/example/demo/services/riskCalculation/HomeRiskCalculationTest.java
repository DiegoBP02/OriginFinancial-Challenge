package com.example.demo.services.riskCalculation;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class HomeRiskCalculationTest extends ApplicationConfigTest {

    @Autowired
    private HomeRiskCalculation homeRiskCalculation;

    RiskScore riskScore = new RiskScore(0);
    User user = new User();

    @Test
    void givenUserHouseIsMortgaged_whenCalculateRisk_thenAdd1RiskPointToHome() {
        user.setHouse(User.House.builder().ownershipStatus(User.OwnershipStatus.MORTGAGED).build());
        homeRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getHome());
    }

}