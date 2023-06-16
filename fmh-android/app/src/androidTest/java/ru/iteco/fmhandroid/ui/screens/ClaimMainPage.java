package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isA;
import static ru.iteco.fmhandroid.ui.steps.CustomViewAction.needWait;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import android.view.View;
import android.widget.Checkable;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class ClaimMainPage {
    public static TimeoutEspresso.TimedViewInteraction title = onViewWithTimeout(10000, withText("Заявки"));
    public static TimeoutEspresso.TimedViewInteraction filter = onViewWithTimeout(10000, withId(R.id.filters_material_button));
    public static TimeoutEspresso.TimedViewInteraction newClaim = onViewWithTimeout(10000, withId(R.id.add_new_claim_material_button));
    public static TimeoutEspresso.TimedViewInteraction openStatus = onViewWithTimeout(10000, withId(R.id.item_filter_open));
    public static TimeoutEspresso.TimedViewInteraction inProgressStatus = onViewWithTimeout(10000, withId(R.id.item_filter_in_progress));
    public static TimeoutEspresso.TimedViewInteraction executedStatus = onViewWithTimeout(10000, withId(R.id.item_filter_executed));
    public static TimeoutEspresso.TimedViewInteraction canceledStatus = onViewWithTimeout(10000, withId(R.id.item_filter_cancelled));
    public static TimeoutEspresso.TimedViewInteraction okFilterClaim = onViewWithTimeout(10000, withId(R.id.claim_list_filter_ok_material_button));
    public static TimeoutEspresso.TimedViewInteraction claimListRecycler = onViewWithTimeout(10000, withId(R.id.claim_list_recycler_view));

    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {}

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }

    @Step("Фильтрация заявки со статусом \"Открыта\"")
    public static void filteringByOpenStatus() {
        filter.perform(click());
        openStatus.perform(setChecked(true));
        inProgressStatus.perform(setChecked(false));
        executedStatus.perform(setChecked(false));
        canceledStatus.perform(setChecked(false));
        okFilterClaim.perform(click());
    }

    @Step("Фильтрация заявки со статусом \"В работе\"")
    public static void filteringByProgressStatus() {
        filter.perform(click());
        inProgressStatus.perform(setChecked(true));
        openStatus.perform(setChecked(false));
        executedStatus.perform(setChecked(false));
        canceledStatus.perform(setChecked(false));
        okFilterClaim.perform(click());
    }

    public static void openByIndex(int i) {
        needWait(1_000);
        claimListRecycler
                .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
    }
}
