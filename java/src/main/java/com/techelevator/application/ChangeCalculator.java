package com.techelevator.application;

import com.techelevator.ui.UserOutput;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ChangeCalculator {
    private BigDecimal remainingFunds;
    private UserOutput userOutput;

    public ChangeCalculator(BigDecimal remainingFunds, UserOutput userOutput) {
        this.remainingFunds = remainingFunds;
        this.userOutput = userOutput;
    }

    public void calculateChange() {
        BigDecimal[] denoms = new BigDecimal[] {new BigDecimal("1"), new BigDecimal("0.25"), new BigDecimal(".10"), new BigDecimal("0.05")};
        String[] denomNames = new String[] {"Dollar(s)", "Quarter(s)", "Dime(s)", "Nickel(s)"};
        int[] change = new int[] {0,0,0,0};
        for (int i = 0; i < denoms.length; i++) {
            int num = remainingFunds.divide(denoms[i], RoundingMode.DOWN).intValue();
            remainingFunds = remainingFunds.subtract(denoms[i].multiply(new BigDecimal(num)));
            change[i] = num;
            //userOutput.displayMessage(change.toString());
        }
        List<String> changeCountList = new ArrayList<>();
        for (int i = 0; i < change.length; i++) {
            if (change[i] != 0) {
                changeCountList.add(change[i] + " " + denomNames[i]);
                //changeCountList  += change[i] + " " + denomNames[i] + ", ";
            }
        }
        userOutput.displayMessage("Your change is: " + String.join(", ", changeCountList));
    }
}
