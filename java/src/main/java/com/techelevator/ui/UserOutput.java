package com.techelevator.ui;

import com.techelevator.models.Item;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;


/**
 * Responsibilities: This class should handle formatting and displaying ALL
 * messages to the user
 * 
 * Dependencies: None
 */
public class UserOutput
{

    public void displayMessage(String message)
    {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    public void displayHomeScreen()
    {
        System.out.println();
        System.out.println("*****************************************************");
        System.out.println("\u001B[36m    Welcome to Scott and Marco's Vending Machine!\u001B[0m");
        System.out.println("*****************************************************");
        System.out.println();
    }

    public void displayVendingItems(List<Item> items, int purchaseCounter) {
        // NEED TO SHOW WHEN ITEM IS OUT OF STOCK
        System.out.println("\n\n*** Current Vending Machine Options ***");
        for (Item item : items) {

            String message = "";
            if (item.getQuantity() == 0) {
                message = "(Out of Stock)";
            } else {
                message = "(" + item.getQuantity() + " Available)";            }
            System.out.printf("(%s) %-18s %-15s %8s\n", item.getSlot(), item.getName(),
                    message, item.getFormattedDiscountPrice(purchaseCounter));
        }
        System.out.println();
    }

    public void displayConfirmation(Item item, BigDecimal currentMoney, int purchaseCounter){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        System.out.println();
        System.out.println("*** " + item.getDescription() + " ***");
        System.out.println("You purchased a " + item.getName() + " that costs " +
                item.getFormattedDiscountPrice(purchaseCounter) + ". Your remaining balance is: " + numberFormat.format(currentMoney));
        System.out.println();
    }












}
