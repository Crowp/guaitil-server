package com.guaitilsoft.utils;

import org.springframework.beans.factory.annotation.Value;

public class GuaitilEmailInfo {
    private static final String title = "GuaitilTour";
    private static final String phoneNumber = "59863587";
    private static final String emailFrom = "guaitiltour.cr@gmail.com";

    @Value("${guaitil-client.domain}")
    private static String urlGuaitil;

    public static String getTitle() { return title; }
    public static String getEmailFrom() { return emailFrom; }
    public static String getPhoneNumber() { return phoneNumber; }
    public static String getUrlGuaitil() { return urlGuaitil; }
}
