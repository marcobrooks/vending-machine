package com.techelevator.application;

import com.techelevator.models.Item;
import com.techelevator.ui.UserInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;
    @Before
    public void init(){
        this.vendingMachine = new VendingMachine();
        vendingMachine.importInventoryFromFile("catering.csv");
        vendingMachine.setCurrentMoney(new BigDecimal("20"));
    }

    @Test
    public void makePurchase_a1_expects1835(){
        vendingMachine.dispenseItem("a1");
        BigDecimal actual = vendingMachine.getCurrentMoney();
        BigDecimal expected = new BigDecimal("18.35");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makePurchase_d3_with_discount_expects1865(){
        vendingMachine.setPurchaseCounter(1);
        vendingMachine.dispenseItem("d3");
        BigDecimal actual = vendingMachine.getCurrentMoney();
        BigDecimal expected = new BigDecimal("18.65");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makePurchase_null_with_expects_no_change(){
        vendingMachine.dispenseItem(null);
        BigDecimal actual = vendingMachine.getCurrentMoney();
        BigDecimal expected = new BigDecimal("20");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makePurchase_with_0_money_expects_0(){
        vendingMachine.setCurrentMoney(new BigDecimal("0"));
        vendingMachine.dispenseItem("d3");
        BigDecimal actual = vendingMachine.getCurrentMoney();
        BigDecimal expected = new BigDecimal("0");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makePurchase_with_exact_change_expects_0(){
        vendingMachine.setCurrentMoney(new BigDecimal("2.35"));
        vendingMachine.dispenseItem("d3");
        BigDecimal actual = vendingMachine.getCurrentMoney();
        BigDecimal expected = new BigDecimal("0.00");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void moneyFed_invalid_option_expects_20() {
        vendingMachine.feedMoney(new BigDecimal("3.00"));
        Assert.assertEquals(new BigDecimal("20"), vendingMachine.getCurrentMoney());
    }

    @Test
    public void moneyFed_invalid_option_neg10_expects_20() {
        vendingMachine.feedMoney(new BigDecimal("-10"));
        Assert.assertEquals(new BigDecimal("20"), vendingMachine.getCurrentMoney());
    }

    @Test
    public void moneyFed_5_expects_25() {
        vendingMachine.feedMoney(new BigDecimal("5"));
        Assert.assertEquals(new BigDecimal("25"), vendingMachine.getCurrentMoney());
    }

    @Test
    public void ill_think_of_a_name_later() {
        List<Item> items = vendingMachine.getItems();
        Item item = items.get(0);
        Assert.assertEquals("U-Chews", item.getName());
    }

    @Test
    public void test_for_correct_price_on_imported_item() {
        List<Item> items = vendingMachine.getItems();
        Item item = items.get(5);
        Assert.assertEquals(new BigDecimal("3.45"), item.getPrice());
    }

    @Test
    public void test_format_price() {
        String test = vendingMachine.formatPrice(new BigDecimal("153.25"));
        Assert.assertEquals("$153.25", test);
    }

    @Test
    public void test_format_price_negative() {
        String test = vendingMachine.formatPrice(new BigDecimal("-23"));
        Assert.assertEquals("-$23.00", test);
    }

    @Test
    public void slotExists_send_in_a1_returns_0(){
        int slotNumber = vendingMachine.slotExists("a1");
        Assert.assertEquals(0, slotNumber);
    }

    @Test
    public void slotExists_(){
        int slotNumber = vendingMachine.slotExists("x7");
        Assert.assertEquals(-1, slotNumber);
    }

























}