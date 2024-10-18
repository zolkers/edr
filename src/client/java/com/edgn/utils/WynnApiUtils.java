package com.edgn.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WynnApiUtils {
    private final static String WYNN_API_URL = "https://api.wynncraft.com/v3/";

    public static String getStringFromURL(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL turl = new URI(url).toURL();
            HttpsURLConnection connection = (HttpsURLConnection) turl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
