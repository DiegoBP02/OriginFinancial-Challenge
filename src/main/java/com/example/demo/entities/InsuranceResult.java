package com.example.demo.entities;

import com.example.demo.enums.Scores;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResult {
    private Scores auto;
    private Scores disability;
    private Scores home;
    private Scores life;
}
