package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public LoginPage() {
        loginField.should(visible);
    }

    public void verifyError(String expectedText) {
        errorNotification.should(visible).should(Condition.text(expectedText));
    }

    public VerificationPage validGoodLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void validBadLogin(DataHelper.AuthInfo info, String expectedText) {
        login(info);
        verifyError(expectedText);
        cleanFields();
    }

    public void login(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void cleanFields() {
        loginField.press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.DELETE);
        passwordField.press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.DELETE);
    }


}