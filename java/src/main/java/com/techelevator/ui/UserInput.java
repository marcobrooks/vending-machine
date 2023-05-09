package com.techelevator.ui;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Responsibilities: This class should handle receiving ALL input from the User
 * 
 * Dependencies: None
 */
public class UserInput
{
    private static Scanner scanner = new Scanner(System.in);

    public  String getHomeScreenOption()
    {
        System.out.println("What would you like to do?");
        System.out.println();

        System.out.println("\u001B[36mD)\u001B[0m Display Vending Machine Items");
        System.out.println("\u001B[36mP)\u001B[0m Purchase");
        System.out.println("\u001B[36mE)\u001B[0m Exit");

        System.out.println();
        System.out.print("Please select an option: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toLowerCase();

        if (option.equals("d"))
        {
            return "display";
        }
        else if (option.equals("p"))
        {
            return "purchase";
        }
        else if (option.equals("e"))
        {
            return "exit";
        }
        else
        {
            return "";
        }

    }

    public String getPurchaseOption(BigDecimal currentMoney) {
        NumberFormat moneyFormatted = NumberFormat.getCurrencyInstance();
        System.out.println("\u001B[36mM)\u001B[0m Feed Money");
        System.out.println("\u001B[36mS)\u001B[0m Select Item");
        System.out.println("\u001B[36mF)\u001B[0m Finish Transaction");
        System.out.println();
        System.out.println("Current Money Provided: " + moneyFormatted.format(currentMoney));
        System.out.println();
        System.out.print("Please select an option: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toLowerCase();

        if (option.equals("m")) {
            return "feedmoney";
        } else if (option.equals("s")) {
            return "selectitem";
        } else if (option.equals("f")) {
            return "finish";
        } else {
            return "";
        }
    }

    public BigDecimal getMoneyFed() {
        System.out.println();
        System.out.println("Bills accepted: $1, $5, $10, or $20.");
        System.out.print("How much would you like to add? ");
        String moneyInput = scanner.nextLine();
        System.out.println();
        moneyInput = moneyInput.replace("$", "").trim();
        BigDecimal money = new BigDecimal(moneyInput);
        return money;
    }

    public String getItemToPurchase(){
        System.out.print("Enter slot number of the item you would like to purchase: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toLowerCase();
        // if item doesn't exist inform user and return to pruchase menu
        return option;
        // if item is out of stock inform user and return to purchase menu
    }
    
}
