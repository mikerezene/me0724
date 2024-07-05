package RentalApp.Tests;

import RentalApp.Checkout;
import RentalApp.Model.RentalAgreement;
import RentalApp.Model.Tool;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class CheckoutTest {
    Tool ladder;
    Tool chainsaw;
    Tool jackhammerDeWalt;
    Tool jackhammerRidgid;

    public CheckoutTest(){
        ladder = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
        chainsaw = new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
        jackhammerDeWalt = new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
        jackhammerRidgid = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
    }


    @Test
    public void testScenario1() {
        Checkout checkout = new Checkout();
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkout.checkout(jackhammerRidgid, 5, 101, checkoutDate);
        });

        String expectedMessage = "Discount percent must be between 0 and 100.";
        String actualMessage = exception.getMessage();

        System.out.println("Expected Exception Message: " + expectedMessage);
        System.out.println("Actual Exception Message: " + actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testScenario2() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout(ladder, 3, 10, LocalDate.of(2020, 7, 2));

        assertEquals("LADW", agreement.getTool().getToolCode());
        assertEquals("Ladder", agreement.getTool().getToolType());
        assertEquals("Werner", agreement.getTool().getBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
        assertEquals(1.99, agreement.getDailyRentalCharge(),0.0001);
        assertEquals(2, agreement.getChargeDays());
        assertEquals(3.98, agreement.getPreDiscountCharge(), 0.0001);
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(0.40, agreement.getDiscountAmount(), 0.0001);
        assertEquals(3.58, agreement.getFinalCharge(), 0.0001);
    }

    @Test
    public void testScenario3() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout(chainsaw, 5, 25, LocalDate.of(2015, 7, 2));

        assertEquals("CHNS", agreement.getTool().getToolCode());
        assertEquals("Chainsaw", agreement.getTool().getToolType());
        assertEquals("Stihl", agreement.getTool().getBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
        assertEquals(1.49, agreement.getDailyRentalCharge(),0.0001);
        assertEquals(3, agreement.getChargeDays());
        assertEquals(4.47, agreement.getPreDiscountCharge(),0.0001);
        assertEquals(25, agreement.getDiscountPercent());
        assertEquals(1.12, agreement.getDiscountAmount(),0.0001);
        assertEquals(3.35, agreement.getFinalCharge(),0.0001);
    }

    @Test
    public void testScenario4() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout(jackhammerDeWalt, 6, 0, LocalDate.of(2015, 9, 3));

        assertEquals("JAKD", agreement.getTool().getToolCode());
        assertEquals("Jackhammer", agreement.getTool().getToolType());
        assertEquals("DeWalt", agreement.getTool().getBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
        assertEquals(2.99, agreement.getDailyRentalCharge(),0.0001);
        assertEquals(3, agreement.getChargeDays());
        assertEquals(8.97, agreement.getPreDiscountCharge(),0.0001);
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0.00, agreement.getDiscountAmount(),0.0001);
        assertEquals(8.97, agreement.getFinalCharge(),0.0001);
    }

    @Test
    public void testScenario5() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout(jackhammerRidgid, 9, 0, LocalDate.of(2015, 7, 2));

        assertEquals("JAKR", agreement.getTool().getToolCode());
        assertEquals("Jackhammer", agreement.getTool().getToolType());
        assertEquals("Ridgid", agreement.getTool().getBrand());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
        assertEquals(2.99, agreement.getDailyRentalCharge(),0.0001);
        assertEquals(5, agreement.getChargeDays());
        assertEquals(14.95, agreement.getPreDiscountCharge(),0.0001);
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0.00, agreement.getDiscountAmount(),0.0001);
        assertEquals(14.95, agreement.getFinalCharge(),0.0001);
    }

    @Test
    public void testScenario6() {
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.checkout(jackhammerRidgid, 4, 50, LocalDate.of(2020, 7, 2));

        assertEquals("JAKR", agreement.getTool().getToolCode());
        assertEquals("Jackhammer", agreement.getTool().getToolType());
        assertEquals("Ridgid", agreement.getTool().getBrand());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
        assertEquals(2.99, agreement.getDailyRentalCharge(),0.0001);
        assertEquals(1, agreement.getChargeDays());
        assertEquals(2.99, agreement.getPreDiscountCharge(),0.0001);
        assertEquals(50, agreement.getDiscountPercent());
        assertEquals(1.50, agreement.getDiscountAmount(),0.0001);
        assertEquals(1.5, agreement.getFinalCharge(),0.0001);
    }

}

