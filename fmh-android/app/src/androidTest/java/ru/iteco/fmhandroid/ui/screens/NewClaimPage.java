package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.RecyclerViewActions;

import java.util.UUID;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.activity.DataHelper;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class NewClaimPage {

    public static TimeoutEspresso.TimedViewInteraction themeParentLayout = onViewWithTimeout(withId(R.id.title_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction subject = onViewWithTimeout(withHint("Тема"));
    public static TimeoutEspresso.TimedViewInteraction executor = onViewWithTimeout(withId(R.id.executor_drop_menu_auto_complete_text_view));
    public static TimeoutEspresso.TimedViewInteraction executorIvanov = onViewWithTimeout(withText("Ivanov Ivan Ivanovich"));
    public static TimeoutEspresso.TimedViewInteraction dateParentLayout = onViewWithTimeout(withId(R.id.date_in_plan_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction date = onViewWithTimeout(withId(R.id.date_in_plan_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction timeParentLayout = onViewWithTimeout(withId(R.id.time_in_plan_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction time = onViewWithTimeout(withId(R.id.time_in_plan_text_input_edit_text));
    public static TimeoutEspresso.TimedViewInteraction descriptionParentLayout = onViewWithTimeout(withId(R.id.description_text_input_layout));
    public static TimeoutEspresso.TimedViewInteraction description = onViewWithTimeout(withId(R.id.description_edit_text));
    public static TimeoutEspresso.TimedViewInteraction saveButton = onViewWithTimeout(withId(R.id.save_button));
    public static TimeoutEspresso.TimedViewInteraction message = onViewWithTimeout(withText("Заполните пустые поля"));
    public static TimeoutEspresso.TimedViewInteraction okButtonMessage = onViewWithTimeout(withText("OK"));
    public static TimeoutEspresso.TimedViewInteraction claimRecyclerView = onViewWithTimeout(withId(R.id.claim_list_recycler_view));

    @Step("Создание новой полностью заполненной заявки и ее открытие")
    public static void addNewClaimAndOpenIt() {
        DataHelper helper = new DataHelper();
        String id = UUID.randomUUID().toString();
        String subject = "New order " + id;
        //Создание и заполнение новой заявки
        ClaimMainPage.newClaim.perform(click());
        NewClaimPage.subject.perform(replaceText(subject), closeSoftKeyboard());
        executor.perform(click());
        executorIvanov.inRoot(isPlatformPopup()).perform(click());
        date.perform(replaceText(helper.getDateToday()), closeSoftKeyboard());
        time.perform(replaceText(helper.getTimeNow()), closeSoftKeyboard());
        description.perform(replaceText("Срочно позвонить! Сроки подходят"), closeSoftKeyboard());
        needWait(1_000);
        saveButton.perform(click());
        // Поиск заявки в списке по теме. Поиск по RecyclerView
        needWait(3_000);
        claimRecyclerView.perform(RecyclerViewActions
                .actionOnItem(hasDescendant(withText(subject)), click())
        );
    }

    @Step("Создание новой заявки без исполнителя")
    public static void addNewClaimWithoutExecutorAndOpenIt() {
        DataHelper helper = new DataHelper();
        String id = UUID.randomUUID().toString();
        String subject = "New order " + id;

        //Создание и заполнение новой заявки без исполнителя
        ClaimMainPage.newClaim.perform(click());
        needWait(1_000);
        NewClaimPage.subject.perform(replaceText(subject), closeSoftKeyboard());
        date.perform(replaceText(helper.getDateToday()), closeSoftKeyboard());
        time.perform(replaceText(helper.getTimeNow()), closeSoftKeyboard());
        description.perform(replaceText("Кому нибудь срочно позвонить! Сроки подходят!"), closeSoftKeyboard());
        needWait(1_000);
        saveButton.perform(scrollTo(), click());
        // Поиск заявки в списке, Поиск по RecyclerView
        needWait(2_000);
        claimRecyclerView.perform(RecyclerViewActions
                .actionOnItem(hasDescendant(withText(subject)), click())
        );

    }
}
