package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setUp() {
        SQLHelper.cleanDatabase();
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfullyLoginWithValidCredentialsAndCode() {
        var authInfo = DataHelper.getFirstUser();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var code = SQLHelper.getVerificationCode(authInfo.getLogin());
        verificationPage.validVerify(code);
    }
}
