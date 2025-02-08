package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.PaymentTourPage;
import ru.netology.page.PurchaseMetodPage;

import static ru.netology.data.DataHelper.*;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.CardInfo.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DBtest {
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

    @BeforeEach
    public void openSource() {
        open("http://localhost:8085");
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldBeApprovedWithApprovedCard() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.successfulPaymentVisible("Операция одобрена Банком.");
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    //ишью
    @Test
    void shouldBeDeclinedWithDeclinedCard() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getGenerateRandomOwner(), getCvc());
        val paymentTourPage = purchaseMetodPage.payByCard();
        paymentTourPage.fillForm(cardInfo);
        paymentTourPage.successfulPaymentVisible("Операция одобрена Банком.");
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

}