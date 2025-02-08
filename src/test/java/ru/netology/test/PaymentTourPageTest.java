package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PaymentTourPage;
import ru.netology.page.PurchaseMetodPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;


public class PaymentTourPageTest {
    PaymentTourPage paymentTourPage = new PaymentTourPage();
    PurchaseMetodPage purchaseMetodPage = new PurchaseMetodPage();
    String randomFirstName = getOwnerRandomFirstName();

    @BeforeEach
    void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //выбор карты
    @BeforeEach
    public void setUp() {
        open("http://localhost:8085");
    }

    @Test
    @DisplayName("Choosing to pay by card")
    void shouldGetPaymentPage() {
        val purchaseMetodPage = new PurchaseMetodPage();
        purchaseMetodPage.payByCard();
    }

    //выбор оплаты по кредиту
    @Test
    @DisplayName("Choosing to pay on credit")
    void shouldGetCreditPage() {
        val purchaseMetodPage = new PurchaseMetodPage();
        purchaseMetodPage.payByCreditCard();
    }

    //успешная оплата по карте
    @Test
    @DisplayName("Should successfully in the card with valid data and buy a tour")
    void shouldSuccessfullyInCard() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.successfulPaymentVisible("Операция одобрена Банком.");
    }

    // ишью
    @Test
    @DisplayName("Should get error notification if the card number is declined")
    void ShouldGetErrorNotificationIfTheCardNumberIsDeclined() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Should get error notification owner double name")
    void ShouldGetErrorNotificationOwnerDoubleName() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getDoubleName(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.successfulPaymentVisible("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Should get error notification if the card number is non existent")
    void ShouldGetErrorNotificationIfTheCardNumberIsNonExistent() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getNonExistentCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Should get error notification if the card number in invalid characters")
    void ShouldGetErrorNotificationIfTheCardNumberIsInvalidCharacters() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getCardNumberInInvalidCharacters(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if the card number with letters")
    void ShouldGetErrorNotificationIfTheCardNumberWithLetters() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getCardNumberWithLetters(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if the short card number")
    void ShouldGetErrorNotificationIfTheShortCardNumber() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getShortCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if the null card number")
    void ShouldGetErrorNotificationIfTheNullCardNumber() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(null, getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    // ишью
    @Test
    @DisplayName("Should get error notification if month zero passes next year")
    void ShouldGetErrorNotificationIfMonthZeroPassesNextYear() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getOnlyZeroInMonthField(), getNextYearInFiveYears(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Should get error notification if month zero")
    void ShouldGetErrorNotificationIfMonthZero() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getOnlyZeroInMonthField(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.cardExpirationDateIsIncorrectError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should get error notification if non exist month")
    void ShouldGetErrorNotificationIfNonExistMonth() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getNonExistMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.cardExpirationDateIsIncorrectError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should get error notification if month number with space")
    void ShouldGetErrorNotificationIfMonthNumberWithSpace() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthNumberWithSpace(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if month number with letters")
    void ShouldGetErrorNotificationIfMonthNumberWithLetters() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthNumberWithLetters(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if month number with symbols")
    void ShouldGetErrorNotificationIfMonthNumberWithSymbols() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthNumberWithSymbols(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if month number with symbols")
    void ShouldGetErrorNotificationIfMonthNumberWithNull() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), null, getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    //год
    @Test
    @DisplayName("Should get error notification if past year")
    void ShouldGetErrorNotificationIfPastYear() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getPastYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.cardExpirationYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should get error notification if year it consists of a single digit")
    void ShouldGetErrorNotificationIfYearItConsistsOfSingleDigit() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithOneDigit(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if year in letters")
    void ShouldGetErrorNotificationIfYearInLetters() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearInLetters(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if year in symbols")
    void ShouldGetErrorNotificationIfYearInSymbols() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearInSymbols(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if year with spaces")
    void ShouldGetErrorNotificationIfYearWithSpaces() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithSpaces(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification in thirty years")
    void ShouldGetErrorNotificationIfYearInThirty() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getNextYearInThirtyYears(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.cardExpirationDateIsIncorrectError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should get error notification in hundred years")
    void ShouldGetErrorNotificationIfYearInHundred() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getNextYearInHundredYears(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if the null year")
    void ShouldGetErrorNotificationIfTheNullYear() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), null, getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner in Russian")
    void ShouldGetErrorNotificationWriteOwnerInRussian() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInRussia(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner first name")
    void ShouldGetErrorNotificationWriteOwnerFirstName() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerRandomFirstName(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner with numbers")
    void ShouldGetErrorNotificationWriteOwnerWithNumberse() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerWithNumbers(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner name long")
    void ShouldGetErrorNotificationWriteOwnerNameLong() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameLong(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner name short")
    void ShouldGetErrorNotificationWriteOwnerNameShort() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getShortName(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification write the owner spaces at beginning and end")
    void ShouldGetErrorNotificationWriteOwnerWithSpacesAtBeginningAndEnd() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getNameWithSpacesAtBeginningAndEnd(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Should get error notification if the null owner")
    void ShouldGetErrorNotificationIfTheNullOwner() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), null, getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.ownerRequiredToFillInErrorVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should get error notification only spaces in owner field")
    void ShouldGetErrorNotificationOnlySpacesInOwnerField() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOnlySpacesInOwnerField(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.ownerRequiredToFillInErrorVisible("Поле обязательно для заполнения");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification owner an informal name")
    void ShouldGetErrorNotificationOwnerAnInformalName() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getAnInformalName(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }
//cvc

    @Test
    @DisplayName("Should get error notification letters in CVC")
    void ShouldGetErrorNotificationLettersInCVC() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getLettersInCVC());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification CVC number with symbols")
    void ShouldGetErrorNotificationCVCNumberWithSymbols() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCVCNumberWithSymbols());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification CVC in Two characters")
    void ShouldGetErrorNotificationCVCInTwoCharacters() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCVCInTwoCharacters());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification CVC with space")
    void ShouldGetErrorNotificationCVCWithSpace() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCVCWithSpace());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    @Test
    @DisplayName("Should get error notification if the null CVC")
    void ShouldGetErrorNotificationIfTheNullCvc() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), null);
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.incorrectCardFormatError("Неверный формат");
    }

    //ишью
    @Test
    @DisplayName("Should get error notification CVC invalid value")
    void ShouldGetErrorNotificationCVCInvalidValue() {
        DataHelper.CardInfo cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCVCInvalidValue());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.verifyErrorNotificationVisible("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Sending an empty card payment form")
    void sendingAnEmptyCardPaymentForm() {
        val purchaseMetodPage = new PurchaseMetodPage();
        purchaseMetodPage.payByCard();
        val paymentTourPage = new PaymentTourPage();
        paymentTourPage.notFillForm();
    }
}
