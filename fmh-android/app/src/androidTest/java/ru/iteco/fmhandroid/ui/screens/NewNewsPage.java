package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.clickChildViewWithId;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.DataHelper;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class NewNewsPage {
    public static TimeoutEspresso.TimedViewInteraction titlePage =
            onViewWithTimeout(withId(R.id.custom_app_bar_sub_title_text_view));
    public static TimeoutEspresso.TimedViewInteraction category =
            onViewWithTimeout(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static TimeoutEspresso.TimedViewInteraction categoryParentLayout =
            onViewWithTimeout(withId(R.id.news_item_category_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction holiday =
            onViewWithTimeout(withText("Праздник"));
    public static TimeoutEspresso.TimedViewInteraction title = onViewWithTimeout(withId(R.id.news_item_title_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction titleParentLayout = onViewWithTimeout(withId(R.id.news_item_title_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction date =
            onViewWithTimeout(withId(R.id.news_item_publish_date_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction dateParentLayout =
            onViewWithTimeout(withId(R.id.news_item_create_date_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction time =
            onViewWithTimeout(withId(R.id.news_item_publish_time_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction timeParentLayout =
            onViewWithTimeout(withId(R.id.news_item_publish_time_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction description =
            onViewWithTimeout(withId(R.id.news_item_description_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction descriptionParentLayout =
            onViewWithTimeout(withId(R.id.news_item_description_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction switcher = onViewWithTimeout(withId(R.id.switcher));
    public static TimeoutEspresso.TimedViewInteraction statusNews = onViewWithTimeout(withId(R.id.news_item_published_text_view));
    public static TimeoutEspresso.TimedViewInteraction deleteNews = onViewWithTimeout(withId(R.id.delete_news_item_image_view));
    public static TimeoutEspresso.TimedViewInteraction saveButton = onViewWithTimeout(withId(R.id.save_button));
    public static TimeoutEspresso.TimedViewInteraction cancelButton = onViewWithTimeout(withId(R.id.cancel_button));
    public static TimeoutEspresso.TimedViewInteraction okButtonMessage = onViewWithTimeout(withText("OK"));
    public static Matcher<View> icon = withId(R.id.text_input_end_icon);
    public static TimeoutEspresso.TimedViewInteraction newsRecyclerList = onViewWithTimeout(10000, withId(R.id.news_list_recycler_view));


    @Step("Создание новости")
    public static void addNews(String newTitle) {
        DataHelper help = new DataHelper();
        category.perform(click());
        holiday.inRoot(isPlatformPopup()).perform(click());
        title.perform(replaceText(newTitle), closeSoftKeyboard());
        date.perform(replaceText(help.getDateToday()), closeSoftKeyboard());
        time.perform(replaceText(help.getTimeNow()), closeSoftKeyboard());
        description.perform(replaceText("Задорно отметим"), closeSoftKeyboard());
        saveButton.perform(click());
        needWait(2_000);
        newsRecyclerList.perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText(newTitle)),
                click()));
    }

    @Step("Создание новости")
    public static void addNews(String newTitle, int minuteShift) {
        DataHelper help = new DataHelper();
        category.perform(click());
        holiday.inRoot(isPlatformPopup()).perform(click());
        title.perform(replaceText(newTitle), closeSoftKeyboard());
        date.perform(replaceText(help.getDateToday()), closeSoftKeyboard());
        time.perform(replaceText(help.addMinToCurrentTime(minuteShift)), closeSoftKeyboard());
        description.perform(replaceText("Задорно отметим"), closeSoftKeyboard());
        saveButton.perform(click());
    }

    @Step("Удаление новости")
    public static void deleteNews(String title) {
        newsRecyclerList
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(title)),
                        clickChildViewWithId(R.id.delete_news_item_image_view)));
        okButtonMessage.perform(click());
    }

    @Step("Редактирование новости: темы и описание")
    public static void editNews(String oldTitle, String newTitle, String newDescription) {
        //Поиск новости по заголовку и редактирование
        newsRecyclerList
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(oldTitle)),
                        clickChildViewWithId(R.id.edit_news_item_image_view)));
        //Правка темы и описания
        title.perform(replaceText(newTitle), closeSoftKeyboard());
        description.perform(replaceText(newDescription), closeSoftKeyboard());
        saveButton.perform(click());
    }

    @Step("Редактирование новости: сменить статус")
    public static void changeStatus(String title) {
        //Поиск новости по заголовку для дальнейшего редактирование
        newsRecyclerList
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(title)),
                        clickChildViewWithId(R.id.edit_news_item_image_view)));
        switcher.perform(click());
        saveButton.perform(click());
    }
}
