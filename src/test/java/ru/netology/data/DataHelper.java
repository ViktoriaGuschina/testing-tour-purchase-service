package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    @Value
    @RequiredArgsConstructor
    public static class CardInfo {
        private String numberCard;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }

    //карта
    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    public static String getNonExistentCardNumber() {
        return ("1111 2222 3333 6789");
    }

    public static String getCardNumberInInvalidCharacters() {
        return ("1234 1234 1234 *!@$");
    }

    public static String getCardNumberWithLetters() {
        return ("1234 1234 1234 Abя");
    }

    public static String getShortCardNumber() {
        return ("1234 1234 1234");
    }


    //поле месяц
    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getOnlyZeroInMonthField() {
        return ("00");
    }

    public static String getNonExistMonth() {
        return ("14");
    }

    public static String getMonthNumberWithSpace() {
        return (" ");
    }

    public static String getMonthNumberWithLetters() {
        return ("on");
    }

    public static String getMonthNumberWithSymbols() {
        return ("!@");
    }

    // год
    public static String getValidYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return Year.now().format(formatter);
    }

    public static String getPastYear() {
        LocalDate localDate = LocalDate.now().minusYears(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return localDate.format(formatter);
    }

    public static String getYearWithOneDigit() {
        return ("2");
    }

    public static String getYearInLetters() {
        return ("веки");
    }

    public static String getYearInSymbols() {
        return ("!@%^");
    }

    public static String getYearWithSpaces() {
        return (" 2");
    }

    public static String getNextYearInFiveYears() {
        LocalDate localDate = LocalDate.now().plusYears(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return localDate.format(formatter);
    }

    public static String getNextYearInThirtyYears() {
        LocalDate localDate = LocalDate.now().plusYears(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return localDate.format(formatter);
    }

    public static String getNextYearInHundredYears() {
        LocalDate localDate = LocalDate.now();
        return String.format("yy", localDate.plusYears(100));
    }

//владелец

    public static String getGenerateRandomOwner() {
        return faker.name().fullName();
    }

    public static String getOwnerNameInRussia() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getOwnerRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getOwnerWithNumbers() {
        return ("Pety1");
    }

    public static String getOwnerNameLong() {
        return "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij";
    }

    public static String getShortName() {
        return ("f");
    }

    public static String getNameWithSpacesAtBeginningAndEnd() {
        return (" John Smith Doe ");
    }

    public static String getOnlySpacesInOwnerField() {
        return ("   ");
    }

    public static String getAnInformalName() {
        return ("Jake Johnson");
    }

    public static String getDoubleName() {
        return ("Emily-Rose Thompson");
    }

    //cvc

    public static String getCvc() {
        return ("999");
    }

    public static String getLettersInCVC() {
        return ("0vj");
    }

    public static String getCVCNumberWithSymbols() {
        return ("!@#");
    }

    public static String getCVCInTwoCharacters() {
        return ("34");
    }

    public static String getCVCWithSpace() {
        return ("2 3");
    }

    public static String getCVCInvalidValue() {
        return ("000");
    }
}