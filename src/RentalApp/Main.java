package RentalApp;
import java.time.LocalDate;
import RentalApp.Model.*;

public class Main {
    public static void main(String[] args) {
        Tool ladder = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
        Tool chainsaw = new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
        Tool jackhammerDeWalt = new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
        Tool jackhammerRidgid = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);

        Checkout checkout = new Checkout();
        System.out.println("***********************************************");
        try {
            RentalAgreement agreement1 = checkout.checkout(jackhammerRidgid, 5, 101, LocalDate.of(2015, 9, 3));
            agreement1.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

        try {
            RentalAgreement agreement2 = checkout.checkout(ladder, 3, 10, LocalDate.of(2020, 7, 2));
            agreement2.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

        try {
            RentalAgreement agreement3 = checkout.checkout(chainsaw, 5, 25, LocalDate.of(2015, 7, 2));
            agreement3.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

        try {
            RentalAgreement agreement4 = checkout.checkout(jackhammerDeWalt, 6, 0, LocalDate.of(2015, 9, 3));
            agreement4.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

        try {
            RentalAgreement agreement5 = checkout.checkout(jackhammerRidgid, 9, 0, LocalDate.of(2015, 7, 2));
            agreement5.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

        try {
            RentalAgreement agreement6 = checkout.checkout(jackhammerRidgid, 4, 50, LocalDate.of(2020, 7, 2));
            agreement6.print();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********************************************");

    }
}

