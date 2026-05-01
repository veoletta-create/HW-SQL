package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static ru.netology.data.SQLHelper.cleanDatabase;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    public LoginPage loginPage;

    @AfterAll
    static void cleanTables() {
        cleanDatabase();
    }

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validGoodLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorRandomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validBadLogin(authInfo, "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorRandomCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validGoodLogin(authInfo);
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.invalidVerify(verificationCode.getCode(), "Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    void shouldGetErrorBadPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoBadPassword();
        loginPage.validBadLogin(authInfo, "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorThreeBadPasswords() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoBadPassword();
        for (int i = 0; i < 3; i++) {
            loginPage.validBadLogin(authInfo, "Ошибка! Неверно указан логин или пароль");
        }
        loginPage.validBadLogin(authInfo,"Ошибка! Пользователь заблокирован");
    }
}
