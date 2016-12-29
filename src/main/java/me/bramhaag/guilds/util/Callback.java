package me.bramhaag.guilds.util;

public interface Callback <T> {
    void onQueryComplete(T result);
    void onQueryError(Exception ex);
}
