package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

public class CustomViewAction {
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static void returnBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    public static void needWait(final long millis) {
        onView(isRoot()).perform(waitFor(millis));
    }

    private static boolean waitForElementUntilDisplayed(ViewInteraction element) {
        int i = 0;
        int ATTEMPTS = 150;
        while (i++ < ATTEMPTS) {
            try {
                element.check(matches(isDisplayed()));
                return true;
            } catch (Exception e) {
                //e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static ViewInteraction findById(int itemId) {
        ViewInteraction element = onView(withId(itemId));
        waitForElementUntilDisplayed(onView(withId(itemId)));
        return element;
    }

    public static ViewInteraction findByText(String text) {
        ViewInteraction element = onView(withText(text));
        waitForElementUntilDisplayed(element);
        return element;
    }

    public static ViewInteraction findByHintText(String text) {
        ViewInteraction element = onView(withHint(text));
        waitForElementUntilDisplayed(element);
        return element;
    }



}
