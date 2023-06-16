package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class NewsMainPage {
    public static TimeoutEspresso.TimedViewInteraction title = onViewWithTimeout(withText("Новости"));
    public static TimeoutEspresso.TimedViewInteraction sort = onViewWithTimeout(withId(R.id.sort_news_material_button));
    public static TimeoutEspresso.TimedViewInteraction editNews = onViewWithTimeout(withId(R.id.edit_news_material_button));
    public static TimeoutEspresso.TimedViewInteraction addNews = onViewWithTimeout(withId(R.id.add_news_image_view));
    public static TimeoutEspresso.TimedViewInteraction newsListRecycler = onViewWithTimeout(10000, withId(R.id.news_list_recycler_view));

    @Step("Нажать кнопку редактировать и плюс")
    public static void clickEditAndPlus() {
        NewsMainPage.editNews.perform(click());
        NewsMainPage.addNews.perform(click());
    }

    public static TimeoutEspresso.TimedViewInteraction findNewsByTheme(String subject) {
        return onViewWithTimeout(withText(subject));
    }

}
