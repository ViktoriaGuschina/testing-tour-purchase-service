package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.awt.SystemColor.info;

public class PaymentTourPage {

    private final SelenideElement numberCard = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvc = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement numberCardError = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement expiredCardError = $(byText("Истёк срок действия карты")).parent().$(".input__sub");
    private final SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");
    private final SelenideElement successfulPaymentCard = $(".notification.notification_status_ok");
    private final SelenideElement errorNotification = $(".notification.notification_status_error");
    private final SelenideElement incorrectCardFormatError = $(byText("Неверный формат")).parent().$(".input__sub");
    private final SelenideElement cardExpirationDateIsIncorrect = $(byText("Неверно указан срок действия карты")).parent().$(".input__sub");
    private final SelenideElement ownerRequiredToFillInError = $(byText("Поле обязательно для заполнения")).parent().$(".input__sub");

    public void fillForm(DataHelper.CardInfo cardInfo) {
        numberCard.setValue(cardInfo.getNumberCard());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getOwner());
        cvc.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void notFillForm() {
        continueButton.click();
        numberCardError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        ownerError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    public void successfulPaymentVisible(String expectedText) {
        successfulPaymentCard.shouldHave(Condition.text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void verifyErrorNotificationVisible(String expectedText) {
        errorNotification.shouldHave(Condition.text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void incorrectCardFormatError(String expectedText) {
        incorrectCardFormatError.shouldBe(visible);
    }

    public void cardExpirationDateIsIncorrectError(String expectedText) {
        cardExpirationDateIsIncorrect.shouldBe(visible);
    }

    public void cardExpirationYearError(String expectedText) {
        expiredCardError.shouldBe(visible);
    }
    public void ownerRequiredToFillInErrorVisible (String expectedText) {
        ownerRequiredToFillInError.shouldBe(visible);
    }

}
