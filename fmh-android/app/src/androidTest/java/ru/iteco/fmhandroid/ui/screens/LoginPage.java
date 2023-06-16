package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import android.view.View;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.DataHelper;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class LoginPage {
    public static TimeoutEspresso.TimedViewInteraction loginField = onViewWithTimeout(withHint("Логин"));
    public static TimeoutEspresso.TimedViewInteraction passwordField = onViewWithTimeout(withHint("Пароль"));
    public static TimeoutEspresso.TimedViewInteraction loginButton = onViewWithTimeout(withId(R.id.enter_button));
    public static TimeoutEspresso.TimedViewInteraction title = onViewWithTimeout(withText("Авторизация"));

    @Step("Авторизация под валидным пользователем - Иванов И.И.")
    public static void validLogIn() {
        DataHelper help = new DataHelper();
        LoginPage.loginField.perform(typeText(help.getValidUser().getLogin()), closeSoftKeyboard());
        LoginPage.passwordField.perform(typeText(help.getValidUser().getPassword()), closeSoftKeyboard());
        LoginPage.loginButton.perform(click());
        needWait(3_000);
    }

    @Step("Авторизация под НЕвалидной учеткой")
    public static void notValidLogIn() {
        DataHelper help = new DataHelper();
        LoginPage.loginField.perform(typeText(help.getNotValidUser().getLogin()), closeSoftKeyboard());
        LoginPage.passwordField.perform(typeText(help.getNotValidUser().getPassword()), closeSoftKeyboard());
        LoginPage.loginButton.perform(click());
    }

    public static void checkToastMessageText(String text, View decorView) {
        onView(withText(text))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }
}
