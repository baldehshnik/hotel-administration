package com.vd.university.presentation.reader;

import java.util.Scanner;

public class PassportReader {

    private final Scanner scanner;

    public PassportReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readPassportSeries() {
        System.out.print("Enter your passport series: ");
        try {
            String passportSeries = scanner.nextLine();
            if (passportSeries.length() == 2 && notContainsDigits(passportSeries)) {
                return passportSeries.toUpperCase();
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but you must enter correct passport series!");
        }

        return readPassportSeries();
    }

    public String readPassportNumber() {
        System.out.print("Enter your passport number: ");
        try {
            String passportNumber = scanner.nextLine();
            Integer.parseInt(passportNumber);
            if (passportNumber.length() != 7) {
                throw new Exception();
            } else {
                return passportNumber;
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but you must enter correct passport number!");
        }

        return readPassportNumber();
    }

    public boolean notContainsDigits(String value) {
        for (Character c : value.toCharArray()) {
            if (Character.isDigit(c)) return false;
        }

        return true;
    }
}