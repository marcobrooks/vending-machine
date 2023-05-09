package com.techelevator.application;

import java.math.BigDecimal;

public class BigDecimalDemo {
    public static void main(String[] args) {
        double doubleOne = 1.07;
        doubleOne -= 1.06;

        BigDecimal bigOne = new BigDecimal("1.65");

        if (bigOne.compareTo(new BigDecimal(3.00)) <= 0){
            System.out.println("bigOne is the smaller value");
        }

        if (bigOne.compareTo(new BigDecimal("1.65")) == 0){
            System.out.println("They are equal");
        }

        if (bigOne.compareTo(new BigDecimal(1.65)) == 0){
            System.out.println("They are equal again"); // this is not true!
        }

        // subtract big decimal
        bigOne = bigOne.subtract(new BigDecimal("0.35"));
        System.out.println(bigOne);

    }
}
