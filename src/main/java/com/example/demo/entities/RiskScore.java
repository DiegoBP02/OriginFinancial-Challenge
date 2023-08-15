package com.example.demo.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RiskScore {
    private int life;
    private int disability;
    private int home;
    private int auto;

    public RiskScore(int base) {
        this.life = base;
        this.disability = base;
        this.home = base;
        this.auto = base;
    }

    public void deductAll(int value){
        this.life -= value;
        this.disability -= value;
        this.home -= value;
        this.auto -= value;
    }
}
