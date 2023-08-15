package com.example.demo.services.riskCalculation;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class DisabilityRiskCalculationTest extends ApplicationConfigTest {

    @Autowired
    private DisabilityRiskCalculation disabilityRiskCalculation;

    RiskScore riskScore = new RiskScore(0);
    User user = new User();

    @Test
    void givenUserHouseIsMortgaged_whenCalculateRisk_thenAdd1RiskPointToDisability() {
        user.setHouse(User.House.builder().ownershipStatus(User.OwnershipStatus.MORTGAGED).build());
        user.setDependents(0);
        user.setMaritalStatus(User.MaritalStatus.SINGLE);
        disabilityRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getDisability());
    }

    @Test
    void givenUserHasDependents_whenCalculateRisk_thenAdd1RiskPointToDisability() {
        user.setDependents(1);
        user.setHouse(User.House.builder().ownershipStatus(User.OwnershipStatus.OWNED).build());
        user.setMaritalStatus(User.MaritalStatus.SINGLE);
        disabilityRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(1, riskScore.getDisability());
    }

    @Test
    void givenUserIsMarried_whenCalculateRisk_thenRemove1RiskPointFromDisability() {
        user.setMaritalStatus(User.MaritalStatus.MARRIED);
        user.setDependents(0);
        user.setHouse(User.House.builder().ownershipStatus(User.OwnershipStatus.OWNED).build());
        disabilityRiskCalculation.calculateRisk(user, riskScore);
        assertEquals(-1, riskScore.getDisability());
    }

}