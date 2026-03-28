package com.smartcrop.model;

public class WeatherTip {

    private String title;
    private String message;

    public WeatherTip() {}

    public WeatherTip(String title, String message) {
        this.title   = title;
        this.message = message;
    }

    public String getTitle()          { return title; }
    public void setTitle(String v)    { this.title = v; }

    public String getMessage()        { return message; }
    public void setMessage(String v)  { this.message = v; }
}
