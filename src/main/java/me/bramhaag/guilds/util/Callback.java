package me.bramhaag.guilds.util;

import java.sql.ResultSet;

public interface Callback <T> {
    void onQueryComplete(T result);
    void onQueryError(Exception exception);
}
