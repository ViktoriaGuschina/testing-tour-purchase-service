package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PurchaseMetodPage {
    private SelenideElement paymentButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private SelenideElement paymentByCredit = $(byText("Кредит по данным карты"));

    public PaymentTourPage payByCard() {
        paymentButton.click();
        paymentByCard.shouldBe(visible);
        return new PaymentTourPage();
    }
    public CreditPayPage payByCreditCard(){
        creditButton.click();
        paymentByCredit.shouldBe(visible);
        return new CreditPayPage();
    }
}

