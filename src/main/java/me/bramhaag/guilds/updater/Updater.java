package me.bramhaag.guilds.updater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Updater {

    private static int RESOURCE_ID = 13388;
    private static final String USER_AGENT = "";
    private static final String REQUEST_URL = "https://api.spiget.org/v2/resources/12345";

    private static List<UpdateResponse> getVersions() {
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);

            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);

            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<UpdateResponse>>() { }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
