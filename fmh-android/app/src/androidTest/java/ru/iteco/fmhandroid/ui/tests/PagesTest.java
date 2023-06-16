package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.screens.AboutPage.policyText;
import static ru.iteco.fmhandroid.ui.screens.AboutPage.termsOfUseText;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;

import androidx.test.espresso.intent.Intents;
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
import ru.iteco.fmhandroid.ui.screens.AboutPage;
import ru.iteco.fmhandroid.ui.screens.ClaimMainPage;
import ru.iteco.fmhandroid.ui.screens.LoginPage;
import ru.iteco.fmhandroid.ui.screens.MainPage;
import ru.iteco.fmhandroid.ui.screens.NewsMainPage;
import ru.iteco.fmhandroid.ui.screens.OurMissionPage;

@RunWith(AllureAndroidJUnit4.class)
public class PagesTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

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
        LoginPage.validLogIn();
    }

    @After
    public void tearDown() throws Exception {
        MainPage.logOut();
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("4. Интерфейс главной страницы")
    @Test
    public void shouldCheckMainPage() {
        //Проверка наличия элементов на странице
        MainPage.mainLogo.check(matches(isDisplayed()));
        MainPage.newsContainer.check(matches(isDisplayed()));
        MainPage.claimContainer.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("5. Интерфейс приложения на странице  \"Заявки\"")
    @Test
    public void shouldCheckClaimPage() {
        MainPage.openClaimPage();
        //Проверка наличия элементов на странице
        ClaimMainPage.title.check(matches(isDisplayed()));
        ClaimMainPage.filter.check(matches(isDisplayed()));
        ClaimMainPage.newClaim.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("6. Интерфейс приложения на странице \"Новости\"")
    @Test
    public void shouldCheckNewsPage() {
        MainPage.openNewsPage();
        //Проверка наличия элементов на странице
        NewsMainPage.title.check(matches(isDisplayed()));
        NewsMainPage.editNews.check(matches(isDisplayed()));
        NewsMainPage.sort.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("7. Интерфейс приложения на странице  \"О приложении\"")
    @Test
    public void shouldCheckAboutPage() {
        MainPage.openAboutPage();
        //Проверка наличия элементов на странице
        AboutPage.versionTitleField.check(matches(isDisplayed()));
        AboutPage.versionNumberField.check(matches(isDisplayed()));
        AboutPage.privacyPolicy.check(matches(isDisplayed()));
        AboutPage.termsUse.check(matches(isDisplayed()));
        //Возвращение на главную
        AboutPage.backButton.perform(click());
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("8. Интерфейс приложения на странице  \"О приложении\"")
    @Test
    public void shouldCheckTermsUseLink() {
        MainPage.openAboutPage();
        Intents.init();
        AboutPage.termsUse.perform(click());
        intended(hasData("https://vhospice.org/#/terms-of-use"));
        Intents.release();
        termsOfUseText.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("9. Доступность \"Политики о конфиденциальности\"")
    @Test
    public void shouldCheckPrivacyPoliceLink() {
        MainPage.openAboutPage();
        Intents.init();
        AboutPage.privacyPolicy.perform(click());
        intended(hasData("https://vhospice.org/#/privacy-policy"));
        Intents.release();
        policyText.check(matches(isDisplayed()));
    }

    @Feature(value = "Тест-кейсы по проверке страниц через меню навигации приложения (GUI)")
    @Story("10. Доступность страницы \"О мотивации и ценностях\"")
    @Test
    public void shouldCheckOurMissionPage() {
        MainPage.ourMissionButton.perform(click());
        //Проверка наличия и соответствия заголовка
        OurMissionPage.title.check(matches(isDisplayed())).check(matches(withText("Главное - жить любя")));
        OurMissionPage.ourMissionList.check(matches(isDisplayed()));
    }

}
