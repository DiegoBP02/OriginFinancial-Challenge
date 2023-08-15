package com.example.demo.services;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.InsuranceResult;
import com.example.demo.entities.RiskScore;
import com.example.demo.entities.User;
import com.example.demo.enums.Scores;
import com.example.demo.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends ApplicationConfigTest {

    @Autowired
    private UserService userService;

    User user = TestDataBuilder.buildUser();
    RiskScore riskScore = new RiskScore();
    InsuranceResult insuranceResult = new InsuranceResult();

    @Test
    void givenUser_whenCalculateRiskScore_thenReturnCorrectResult() {
        InsuranceResult expectedResult = InsuranceResult.builder()
                .home(Scores.ECONOMIC)
                .auto(Scores.REGULAR)
                .life(Scores.REGULAR)
                .disability(Scores.INELIGIBLE)
                .build();

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(expectedResult, result);
    }

    @Test
    void givenUserDoesNotHaveIncome_whenCalculateRiskScore_thenSetDisabilityIneligible() {
        user.setIncome(0);

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(Scores.INELIGIBLE, result.getDisability());
    }

    @Test
    void givenUserDoesNotHaveVehicles_whenCalculateRiskScore_thenSetAutoIneligible() {
        user.setVehicle(null);

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(Scores.INELIGIBLE, result.getAuto());
    }

    @Test
    void givenUserDoesNotHaveHouses_whenCalculateRiskScore_thenSetHomeIneligible() {
        user.setHouse(null);

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(Scores.INELIGIBLE, result.getHome());
    }

    @Test
    void givenUserIsAbove60YearsOld_whenCalculateRiskScore_thenSetDisabilityAndLifeIneligible() {
        user.setAge(65);

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(Scores.INELIGIBLE, result.getDisability());
        assertEquals(Scores.INELIGIBLE, result.getLife());
    }

    @Test
    void givenUserIsUnder30YearsOld_whenCalculateRiskScore_thenDeduct2RiskPointsFromAllLinesOfInsurance() {
        user.setAge(20);
        user.setIncome(1);

        InsuranceResult expectedResult = InsuranceResult.builder()
                .home(Scores.RESPONSIBLE)
                .auto(Scores.ECONOMIC)
                .life(Scores.REGULAR)
                .disability(Scores.RESPONSIBLE)
                .build();

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(expectedResult, result);
    }

    @Test
    void givenUserIsBetween30And40YearsOld_whenCalculateRiskScore_thenDeduct1RiskPointFromAllLinesOfInsurance() {
        user.setAge(35);
        user.setIncome(1);

        InsuranceResult expectedResult = InsuranceResult.builder()
                .home(Scores.ECONOMIC)
                .auto(Scores.REGULAR)
                .life(Scores.REGULAR)
                .disability(Scores.ECONOMIC)
                .build();

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(expectedResult, result);
    }

    @Test
    void givenUserIncomeIsAbove200k_whenCalculateRiskScore_thenDeduct1RiskPointFromAllLinesOfInsurance() {
        user.setIncome(200000);

        InsuranceResult expectedResult = InsuranceResult.builder()
                .home(Scores.ECONOMIC)
                .auto(Scores.REGULAR)
                .life(Scores.REGULAR)
                .disability(Scores.ECONOMIC)
                .build();

        InsuranceResult result = userService.calculateRiskScore(user);

        assertEquals(expectedResult, result);
    }

}