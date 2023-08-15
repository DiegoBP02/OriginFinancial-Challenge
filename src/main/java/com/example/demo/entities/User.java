package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private int age;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private int dependents;

    @Embedded
    private House house;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private int income;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Size(min = 3, max = 3)
    @ElementCollection
    private List<Boolean> riskQuestions;

    @Embedded
    private Vehicle vehicle;

    @Data
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class House {
        @Enumerated(EnumType.STRING)
        @Column(nullable = true)
        private OwnershipStatus ownershipStatus;
    }

    @Data
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vehicle {
        @Column(nullable = true)
        private int year;
    }

    public enum OwnershipStatus {
        OWNED,
        MORTGAGED
    }

    public enum MaritalStatus {
        SINGLE,
        MARRIED
    }
}
