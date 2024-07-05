package RentalApp;

import RentalApp.Model.RentalAgreement;
import RentalApp.Model.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class Checkout {
    public RentalAgreement checkout(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate) {

        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeDays(checkoutDate, dueDate, tool, checkoutDate.getYear());

        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100;
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(tool, rentalDays, checkoutDate, dueDate, tool.getDailyCharge(), chargeDays, round(preDiscountCharge), discountPercent, round(discountAmount), round(finalCharge));
    }

    private int calculateChargeDays(LocalDate startDate, LocalDate endDate, Tool tool, int year) {
        int chargeDays = 0;
        LocalDate currentDate = startDate.plusDays(1);

        while (!currentDate.isAfter(endDate)) {
            if (isChargeableDay(currentDate, tool, year)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return chargeDays;
    }

    private boolean isChargeableDay(LocalDate date, Tool tool, int year) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (isHoliday(date, year) && !tool.isHolidayCharge()) {
            return false;
        }
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return tool.isWeekendCharge();
        }
        return tool.isWeekdayCharge();
    }

    private LocalDate getIndependenceDay(int year) {
        LocalDate julyFourth = LocalDate.of(year, Month.JULY, 4);
        DayOfWeek dayOfWeek = julyFourth.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return julyFourth.minusDays(1);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return julyFourth.plusDays(1);
        } else {
            return julyFourth;
        }
    }

    //had to check the year everytime under the assumption that the checkout date and the due date might be in a different year
    private LocalDate getLaborDay(int year) {
        LocalDate firstMondayInSeptember = LocalDate.of(year, Month.SEPTEMBER, 1);

        while (firstMondayInSeptember.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstMondayInSeptember = firstMondayInSeptember.plusDays(1);
        }
        return firstMondayInSeptember;
    }

    private boolean isHoliday(LocalDate date, int year) {
        LocalDate july4th = getIndependenceDay(year);
        LocalDate laborDay = getLaborDay(year);

        return date.equals(july4th) || date.equals(laborDay);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
