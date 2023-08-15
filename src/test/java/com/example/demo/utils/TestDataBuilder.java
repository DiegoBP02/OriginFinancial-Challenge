package com.example.demo.utils;

import com.example.demo.entities.User;

import java.util.List;

public class TestDataBuilder {
    public static User buildUser() {
        return User.builder()
                .age(35)
                .dependents(2)
                .house(User.House.builder().ownershipStatus(User.OwnershipStatus.OWNED).build())
                .income(0)
                .maritalStatus(User.MaritalStatus.MARRIED)
                .riskQuestions(List.of(false, true, false))
                .vehicle(User.Vehicle.builder().year(2018).build())
                .build();
    }
}
