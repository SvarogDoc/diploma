package ru.iteco.fmhandroid.ui.activity;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataHelper {
    public User getValidUser() {
        return new User("login2", "password2");
    }
    public User getNotValidUser() {
        return new User("wrong", "wrong2");
    }

    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public String getTimeNow() {
        timeFormat.setTimeZone(TimeZone.getDefault());
        return timeFormat.format(new Date());
    }

    public String addMinToCurrentTime(int min) {
        DateTime shiftTime = new DateTime().plusMinutes(min);
        return shiftTime.toString("HH:mm");
    }

    public String getDateToday() {
        return dateFormat.format(new Date());
    }

    public class User {
        private final String login;
        private final String password;

        public User(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }
}
