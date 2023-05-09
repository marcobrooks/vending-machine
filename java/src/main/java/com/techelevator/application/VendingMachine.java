package com.techelevator.application;

import com.techelevator.logger.Logger;
import com.techelevator.models.Item;
import com.techelevator.ui.UserInput;
import com.techelevator.ui.UserOutput;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class VendingMachine {
    private UserOutput userOutput;
    private UserInput userInput;
    private BigDecimal currentMoney = new BigDecimal("0");
    private List<Item> items = new ArrayList<>();
    private Logger log = new Logger();
    private int purchaseCounter = 0;

    public void setPurchaseCounter(int purchaseCounter) {
        this.purchaseCounter = purchaseCounter;
    }

    public VendingMachine() {
        this.userInput = new UserInput();
        this.userOutput = new UserOutput();
    }

    public void run() {
        importInventoryFromFile("catering.csv");
        while (true) {
            userOutput.displayHomeScreen();
            String choice = userInput.getHomeScreenOption();

            if (choice.equals("display")) {
                // display the vending machine slots
                userOutput.displayVendingItems(items, purchaseCounter);
            } else if (choice.equals("purchase")) {
                // make a purchase
                purchaseItem();
            } else if (choice.equals("exit")) {
                // good bye
                userOutput.displayMessage("Thank you for using our vending machine!");
                break;
            }
        }
    }

    public void purchaseItem() {
        while (true) {
            String choice = userInput.getPurchaseOption(currentMoney);
            if (choice.equals("feedmoney")) {
                BigDecimal moneyFed = userInput.getMoneyFed();
                feedMoney(moneyFed);
            } else if (choice.equals("selectitem")) {
                if (currentMoney.compareTo(new BigDecimal("0")) == 0) {
                    userOutput.displayMessage("\n*** Please add money first ***\n");
                } else {
                    userOutput.displayVendingItems(items, purchaseCounter);
                    String itemToPurchase = userInput.getItemToPurchase();
                    dispenseItem(itemToPurchase);
                }
            } else if (choice.equals("finish")) {
                userOutput.displayMessage("Transaction Finished");
                ChangeCalculator changeCalculator = new ChangeCalculator(currentMoney, userOutput);
                changeCalculator.calculateChange();
                String changeGiven = String.format("%-18s %6s %7s", "CHANGE GIVEN: ",
                        formatPrice(currentMoney), formatPrice(new BigDecimal("0")));
                log.write(changeGiven);
                currentMoney = new BigDecimal(0);
                purchaseCounter = 0;
                break;
            }
        }
    }

    public int slotExists(String slot) {
        int index = 0;
        for (Item item : items) {
            if (item.getSlot().equalsIgnoreCase(slot)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void dispenseItem(String itemToPurchase) {
        int itemIndex = slotExists(itemToPurchase);
        if (itemIndex != -1) {
            Item item = items.get(itemIndex);
            if (item.isInStock()) {
                processDispensedItem(item);
            } else {
                userOutput.displayMessage("Item is out of stock, please select another item.");
            }
        } else {
            userOutput.displayMessage("Invalid Selection");
        }
    }

    public void processDispensedItem(Item item) {
        BigDecimal itemPrice = item.getDiscountPrice(purchaseCounter);
        if (currentMoney.compareTo(itemPrice) >= 0) {
            BigDecimal previousMoney = currentMoney;
            currentMoney = currentMoney.subtract(itemPrice);
            item.setQuantity(item.getQuantity() - 1);
            userOutput.displayConfirmation(item, currentMoney, purchaseCounter);

            String formattedLine = String.format("%-15s %s %6s %7s", item.getName(), item.getSlot(),
                    formatPrice(previousMoney), formatPrice(currentMoney));
            log.write(formattedLine);
            purchaseCounter++;
        } else if (currentMoney.compareTo(itemPrice) == -1) {
            System.out.println(currentMoney);
            System.out.println(itemPrice);
            userOutput.displayMessage("*** Not enough funds to purchase this item. ***");
        }
    }
/*
    public void dispenseItem(String itemToPurchase) {
        boolean hasSlot = false;
        boolean isInStock = true;

        for (Item item : items) {
            // find if the entered slot exists
            if (item.getSlot().equalsIgnoreCase(itemToPurchase)) {
                // test if in stock
                isInStock = item.isInStock();
                BigDecimal itemPrice = item.getDiscountPrice(purchaseCounter);

                hasSlot = true;
                if (currentMoney.compareTo(itemPrice) >= 0 && isInStock) {
                    BigDecimal previousMoney = currentMoney;
                    currentMoney = currentMoney.subtract(itemPrice);
                    item.setQuantity(item.getQuantity() - 1);
                    userOutput.displayConfirmation(item, currentMoney, purchaseCounter);

                    String formattedLine = String.format("%-15s %s %6s %7s", item.getName(), item.getSlot(),
                            formatPrice(previousMoney), formatPrice(currentMoney));
                    log.write(formattedLine);
                    purchaseCounter++;
                } else if (currentMoney.compareTo(itemPrice) == -1) {
                    System.out.println(currentMoney);
                    System.out.println(itemPrice);
                    userOutput.displayMessage("*** Not enough funds to purchase this item. ***");
                }
                break;
            }
        }
        if (!hasSlot) {
            userOutput.displayMessage("Invalid Selection");
        } else if (!isInStock) {
            userOutput.displayMessage("Item is out of stock, please select another item.");
        }
    }*/

    public void feedMoney(BigDecimal moneyFed) {
        BigDecimal[] accepted = new BigDecimal[]{new BigDecimal("1"), new BigDecimal("5"),
                new BigDecimal("10"), new BigDecimal("20")};
        List<BigDecimal> acceptedList = Arrays.asList(accepted);
        if (acceptedList.contains(moneyFed)) {
            currentMoney = currentMoney.add(moneyFed);
            String formattedLine = String.format("%-18s %6s %7s", "MONEY FED: ", formatPrice(moneyFed),
                    formatPrice(currentMoney));
            log.write(formattedLine);
        } else {
            userOutput.displayMessage("No, we don't accept that denomination.");
        }
    }

    public void importInventoryFromFile(String path) {
        File inventoryFile = new File(path);
        try (Scanner input = new Scanner(inventoryFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] itemProps = line.split(",");
                String slot = itemProps[0];
                String name = itemProps[1];
                BigDecimal price = new BigDecimal(itemProps[2]);
                String type = itemProps[3];
                Item item = new Item(slot, name, price, type);
                items.add(item);
            }
        } catch (FileNotFoundException e) {
            userOutput.displayMessage(e.getMessage());
        }
    }

    public String formatPrice(BigDecimal price) {
        NumberFormat formattedPrice = NumberFormat.getCurrencyInstance();
        return formattedPrice.format(price);
    }

    public void setCurrentMoney(BigDecimal bigDecimal) {
        this.currentMoney = bigDecimal;
    }

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public List<Item> getItems() {
        return items;
    }
}
