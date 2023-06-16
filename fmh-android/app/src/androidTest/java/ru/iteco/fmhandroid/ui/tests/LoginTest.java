package ru.iteco.fmhandroid.ui.tests;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.not;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screens.LoginPage;
import ru.iteco.fmhandroid.ui.screens.MainPage;

@RunWith(AllureAndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    private View decorView;

    @Before
    public void setUp() {
        needWait(8_000);
        try {
            LoginPage.title.check(matches(isDisplayed()));
        } catch (Exception e) {
            MainPage.logOut();
            needWait(2_000);
            LoginPage.title.check(matches(isDisplayed()));
        }
        activityScenarioRule.getScenario()
                .onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @After
    public void tearDown() throws Exception {
        try {
            MainPage.logOut();
        } catch (Exception ignored) {
        }
    }

    @Feature(value = "Тест-кейсы по Авторизации: LogIn, выход из учетной записи: LogOut")
    @Story("1. Вход с валидным логином и паролем")
    @Test
    public void shouldLoginByValidUser() {
        LoginPage.validLogIn();
        needWait(2_000);
        //Проверка главной страницы, лого и блоки Новости и Заявки
        MainPage.mainLogo.check(matches(isDisplayed()));
        MainPage.newsContainer.check(matches(isDisplayed()));
        MainPage.claimContainer.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по Авторизации: LogIn, выход из учетной записи: LogOut")
    @Story("2. Выход из учётной записи: LogOut")
    @Test
    public void shouldLogoff() {
        LoginPage.validLogIn();
        needWait(2_000);
        //Выход из приложения
        MainPage.logOut();
        //Проверка страницы авторизации, заголовок и поля логин/пароль
        LoginPage.title.check(matches(isDisplayed()));
        LoginPage.loginButton.check(matches(isDisplayed()));
        LoginPage.passwordField.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по Авторизации: LogIn, выход из учетной записи: LogOut")
    @Story("3. Вход с невалидными данными")
    @Test
    public void shouldNotLoginByNotValidUser() {
        LoginPage.notValidLogIn();
        needWait(500);
        //Проверить всплывающее сообщение
        LoginPage.checkToastMessageText("Неверный логин или пароль", decorView);
        //Проверка что остались на странице авторизации
        MainPage.mainLogo.check(matches(not(isDisplayed())));
        LoginPage.title.check(matches(isDisplayed()));
    }

}
