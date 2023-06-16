package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.activity.DataHelper;
import ru.iteco.fmhandroid.ui.screens.ClaimMainPage;
import ru.iteco.fmhandroid.ui.screens.ClaimPage;
import ru.iteco.fmhandroid.ui.screens.LoginPage;
import ru.iteco.fmhandroid.ui.screens.MainPage;
import ru.iteco.fmhandroid.ui.screens.NewClaimPage;
import ru.iteco.fmhandroid.ui.steps.CustomViewAction;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimFunctionalTest {

    private final DataHelper helper = new DataHelper();

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
            LoginPage.validLogIn();
        } catch (Exception ignored) {
        }
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("11. Создание заявки с заполненными обязательными полями")
    @Test
    public void shouldCreateNewClaimWithExecutor() {
        NewClaimPage.addNewClaimAndOpenIt();
        ClaimPage.statusLabel.check(matches(withText("В работе")));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("12. Создание заявки с пустыми полями")
    @Test
    public void shouldNotCreateEmptyClaim() {
        ClaimMainPage.newClaim.perform(click());
        NewClaimPage.saveButton.perform(click());
        //Проверка на отображение диалогового окна про заполнение пустых полей
        NewClaimPage.message.inRoot(isDialog()).check(matches(isDisplayed()));
        NewClaimPage.okButtonMessage.inRoot(isDialog()).perform(click());
        //Проверка появления некликабельных иконок предупреждения по полям: Тема, Дата, Время, Описание
        NewClaimPage.themeParentLayout.check(matches(hasDescendant(withId(R.id.text_input_end_icon))))
                .check(matches(isNotClickable()));
        NewClaimPage.dateParentLayout.check(matches(hasDescendant(withId(R.id.text_input_end_icon))))
                .check(matches(isNotClickable()));
        NewClaimPage.timeParentLayout.check(matches(hasDescendant(withId(R.id.text_input_end_icon))))
                .check(matches(isNotClickable()));
        NewClaimPage.descriptionParentLayout.check(matches(hasDescendant(withId(R.id.text_input_end_icon))))
                .check(matches(isNotClickable()));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("13. Создание заявки с незаполненным полем Исполнитель")
    @Test
    public void shouldCreateNewClaimWithoutExecutor() {
        NewClaimPage.addNewClaimWithoutExecutorAndOpenIt();
        ClaimPage.statusLabel.checkWithTimeout(4_000, matches(withText("Открыта")));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("14. Фильтрация по статусу заявки \"Открыта\"")
    @Test
    public void shouldFilterByOpenStatusLabel() {
        MainPage.openClaimPage();
        ClaimMainPage.filteringByOpenStatus();
        //Перебор 5-ти первых заявок
        for (int i = 0; i < 5; i++) {
            ClaimMainPage.openByIndex(i);
            needWait(2_000);
            //Проверка статуса и что исполнитель не назначен
            ClaimPage.statusLabel.checkWithTimeout(2_000, matches(withText("Открыта")));
            ClaimPage.executorName.check(matches(withText("НЕ НАЗНАЧЕН")));
            //Нажатие кнопки Back
            CustomViewAction.returnBack();
        }
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("14. Фильтрация по статусу заявки \"В работе\"")
    @Test
    public void shouldFilterByInProgressStatusLabel() {
        MainPage.openClaimPage();
        ClaimMainPage.filteringByProgressStatus();
        //Перебор 5-ти первых заявок
        for (int i = 0; i < 5; i++) {
            ClaimMainPage.openByIndex(i);
            needWait(2_000);
            //Проверка статуса
            ClaimPage.statusLabel.checkWithTimeout(2_000, matches(withText("В работе")));
            //Нажатие кнопки Back
            CustomViewAction.returnBack();
        }
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("15. Редактирование \"Открытой\" заявки")
    @Test
    public void shouldEditClaimWithOpenStatus() {
        String id = UUID.randomUUID().toString().substring(0, 23);
        String editedSubject = "Редактирование " + id;
        NewClaimPage.addNewClaimWithoutExecutorAndOpenIt();
        ClaimPage.executorName.check(matches(withText("НЕ НАЗНАЧЕН")));
        ClaimPage.editButton.perform(click());
        NewClaimPage.subject.perform(replaceText(editedSubject), closeSoftKeyboard());
        needWait(1000);
        NewClaimPage.saveButton.perform(click());
        needWait(1000);
        //Проверка, что тема была изменена
        ClaimPage.titleText.check(matches(withText(editedSubject)));
        //Нажатие Системной кнопки Back
        CustomViewAction.returnBack();
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("16. Смена этапа заявки с этапа \"Открыта\" на \"В работе\"")
    @Test
    public void shouldChangeClaimStatusFromOpenToInProgress() {
        NewClaimPage.addNewClaimWithoutExecutorAndOpenIt();
        ClaimPage.statusLabel.check(matches(withText("Открыта")));
        ClaimPage.executorName.check(matches(withText("НЕ НАЗНАЧЕН")));
        ClaimPage.statusProcessingButton.perform(click());
        ClaimPage.inProgressStatusButton.perform(click());
        //Проверка, что статус поменялся на "В работе" и исполнитель на Ivanov Ivan Ivanovich.
        ClaimPage.statusLabel.check(matches(withText("В работе")));
        ClaimPage.executorName.check(matches(withText("Ivanov Ivan Ivanovich")));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("17. Смена этапа заявки с этапа\"В работе\" на \"Выполнена\"")
    @Test
    public void shouldChangeClaimStatusFromInProgressToDone() {
        NewClaimPage.addNewClaimAndOpenIt();
        // Проверка статуса заявки при заполненном исполнителе
        ClaimPage.statusLabel.check(matches(withText("В работе")));
        ClaimPage.statusProcessingButton.perform(click());
        ClaimPage.statusToExecuteButton.perform(click());
        //Ввод комментария
        ClaimPage.addTextDialogComment("Исполнено идеально");
        //Проверка, что статус поменялся на "Выполнена"
        ClaimPage.statusLabel.check(matches(withText("Выполнена")));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("18. Добавления комментария к заявке")
    @Test
    public void shouldAddComment() {
        String id = UUID.randomUUID().toString().substring(0, 23);
        String newComment = "Комментарий " + id;
        NewClaimPage.addNewClaimAndOpenIt();
        //Добавление комментария
        ClaimPage.addCommentButton.perform(click());
        //Фиксация текущей даты и времени
        String currentTimeForComment = helper.getTimeNow();
        String currentDateForComment = helper.getDateToday();
        ClaimPage.addTextComment(newComment);
        //Проверка текста комментария
        ClaimPage.commentDescription.check(matches(withText(newComment)));
        //Проверка автора комментария
        ClaimPage.commentatorName.check(matches(withText("Ivanov Ivan Ivanovich")));
        //Проверка даты и времени комментария
        ClaimPage.commentDate.check(matches(withText(currentDateForComment)));
        ClaimPage.commentTime.check(matches(withText(currentTimeForComment)));
    }

    @Feature(value = "Tест-кейсы по проверке функционала Заявок (Функциональное тестирование)")
    @Story("19. Редактирование комментария к заявке")
    @Test
    public void shouldEditComment() {
        String id = UUID.randomUUID().toString().substring(0, 23);
        String newComment = "Комментарий " + id;
        String editComment = "Правленный " + id;
        NewClaimPage.addNewClaimAndOpenIt();
        //Добавление комментария
        ClaimPage.addCommentButton.perform(click());
        ClaimPage.addTextComment(newComment);
        //Проверка текста комментария
        ClaimPage.commentDescription.check(matches(withText(newComment)));
        ClaimPage.editCommentButton.perform(click());
        //Фиксация текущей даты и времени
        String currentTimeForComment = helper.getTimeNow();
        String currentDateForComment = helper.getDateToday();
        ClaimPage.addTextComment(editComment);
        //Проверка автора комментария
        ClaimPage.commentatorName.check(matches(withText("Ivanov Ivan Ivanovich")));
        //Проверка даты и времени исправленного комментария
        ClaimPage.commentDate.check(matches(withText(currentDateForComment)));
        ClaimPage.commentTime.check(matches(withText(currentTimeForComment)));
    }
}
