package ru.iteco.fmhandroid.ui.screens;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.steps.TimeoutEspresso.onViewWithTimeout;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.steps.TimeoutEspresso;

public class AboutPage {
    public static TimeoutEspresso.TimedViewInteraction backButton = onViewWithTimeout(withId(R.id.about_back_image_button));
    public static TimeoutEspresso.TimedViewInteraction versionTitleField = onViewWithTimeout(withId(R.id.about_version_title_text_view));
    public static TimeoutEspresso.TimedViewInteraction versionNumberField = onViewWithTimeout(withId(R.id.about_version_value_text_view));
    public static TimeoutEspresso.TimedViewInteraction privacyPolicy = onViewWithTimeout(withId(R.id.about_privacy_policy_value_text_view));
    public static TimeoutEspresso.TimedViewInteraction termsUse = onViewWithTimeout(withId(R.id.about_terms_of_use_value_text_view));
    public static TimeoutEspresso.TimedViewInteraction policyText = onViewWithTimeout(5000,withText("Политика конфиденциальности"));
    public static TimeoutEspresso.TimedViewInteraction termsOfUseText = onViewWithTimeout(5000, withText("Пользовательское соглашение"));
}
