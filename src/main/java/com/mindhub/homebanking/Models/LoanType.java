package com.mindhub.homebanking.Models;

import java.util.ArrayList;
import java.util.List;

public enum LoanType {
    HIPOTECARIO(500000),
    PERSONAL(100000),
    AUTOMOTRIZ(300000);

    private double maxAmount;

    LoanType(double maxAmount) {
    }
}
