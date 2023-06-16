package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screens.LoginPage;
import ru.iteco.fmhandroid.ui.screens.MainPage;
import ru.iteco.fmhandroid.ui.screens.NewNewsPage;
import ru.iteco.fmhandroid.ui.screens.NewsMainPage;

public class NewsFunctionalTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    @Before
    public void setUp() {
        try {
            needWait(5000);
            LoginPage.validLogIn();
            needWait(9000);
            MainPage.openNewsPage();
        } catch (Exception e) {
            MainPage.openNewsPage();
        }
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("20. Создание новости (заполненные обязательные поля)")
    @Test
    public void shouldAddNews() {
        String id = UUID.randomUUID().toString();
        String newTitle = "Праздник " + id;
        NewsMainPage.clickEditAndPlus();
        NewNewsPage.addNews(newTitle);
        needWait(2_000);
        //Проверка темы и что статус новости "АКТИВНА"
        NewsMainPage.newsListRecycler.check(matches(hasDescendant(withText(newTitle))));
        //NewsMainPage.findNewsByTheme(newTitle).check(matches(hasSibling(withText("АКТИВНА"))));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("21. Создание новости (пустые поля)")
    @Test
    public void shouldAddEmptyNews() {
        NewsMainPage.clickEditAndPlus();
        NewNewsPage.saveButton.perform(click());
        //Проверка предупреждений по пустым полям
        NewNewsPage.categoryParentLayout.check(matches(hasDescendant(NewNewsPage.icon)))
                .check(matches(isNotClickable()));
        NewNewsPage.titleParentLayout.check(matches(hasDescendant(NewNewsPage.icon)))
                .check(matches(isNotClickable()));
        NewNewsPage.dateParentLayout.check(matches(hasDescendant(NewNewsPage.icon)))
                .check(matches(isNotClickable()));
        NewNewsPage.timeParentLayout.check(matches(hasDescendant(NewNewsPage.icon)))
                .check(matches(isNotClickable()));
        NewNewsPage.descriptionParentLayout.check(matches(hasDescendant(NewNewsPage.icon)))
                .check(matches(isNotClickable()));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("22 Удаление существующей новости")
    @Test
    public void shouldDeleteNews() {
        String id = UUID.randomUUID().toString();
        String title = "Праздник " + id;
        NewsMainPage.clickEditAndPlus();
        //Создание новости
        NewNewsPage.addNews(title);
        needWait(2_000);
        //Удаление новосозданной новости
        NewNewsPage.deleteNews(title);
        //Проверка, что новость с заданной темой отсутствует
        onView(allOf(withText(title), isDisplayed())).check(doesNotExist());
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("23. Редактирование существующей новости")
    @Test
    public void shouldEditNews() {
        String id = UUID.randomUUID().toString();
        String newId = UUID.randomUUID().toString();
        String title = "Праздник " + id;
        String newTitle = "Ред.Праздник " + newId;
        String newDescription = "Поправим и отметим";
        NewsMainPage.clickEditAndPlus();
        //Создание новости
        NewNewsPage.addNews(title);
        //Редактирование новосозданной новости
        NewNewsPage.editNews(title, newTitle, newDescription);
        needWait(2_000);
        //Проверка, что новость с отредактированной темой и описанием присутсвует
        NewsMainPage.newsListRecycler.check(matches(hasDescendant(withText(newTitle))));
        NewsMainPage.findNewsByTheme(newTitle).check(matches(hasSibling(withText(newDescription))));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("24. Смена статуса у новости с \"Активна\" на \"Не активна\"")
    @Test
    public void shouldChangeNewsStatus() {
        String id = UUID.randomUUID().toString();
        String title = "Праздник " + id;
        NewsMainPage.clickEditAndPlus();
        //Создание новости
        NewNewsPage.addNews(title);
        //Редактирование новосозданной новости
        NewNewsPage.changeStatus(title);
        needWait(2_000);
        //Проверка, что новость с заданной темой и измененным статусом присутсвует
        NewsMainPage.newsListRecycler.check(matches(hasDescendant(withText(title))));
        NewsMainPage.findNewsByTheme(title).check(matches(hasSibling(withText("НЕ АКТИВНА"))));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Новости (Функциональное тестирование)")
    @Story("25. Дата (время) публикации новости")
    @Test
    public void shouldAddNewsForFutureTime() {
        String id = UUID.randomUUID().toString();
        String title = "Будущее " + id;
        NewsMainPage.clickEditAndPlus();
        //Создание новости +2 минуты к текущему времени
        NewNewsPage.addNews(title, 2);
        needWait(2_000);
        //проверка, что Новость видна через редактирование новостей
        NewsMainPage.newsListRecycler.check(matches(hasDescendant(withText(title))));
        NewsMainPage.findNewsByTheme(title).check(matches(hasSibling(withText("АКТИВНА"))));
        //Проверка, что новость с заданной темой и большим временем публикации отсутствует в обычном режиме просмотра
        MainPage.openNewsPage();
        NewsMainPage.newsListRecycler.check(matches(not(hasDescendant(withText(title)))));
    }

}
