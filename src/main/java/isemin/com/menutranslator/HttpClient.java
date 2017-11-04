package isemin.com.menutranslator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by King Jaycee on 22/09/2017.
 */

public class HttpClient {
    private static final String TAG = HttpClient.class.getSimpleName();

    public HttpClient() {
    }

    public String connect(String reqUrl) {

        final String app_id = "78463769";
        final String app_key = "60f1325fee5e8db8c8cf2b04485eb279";

        try {
            URL url = new URL(reqUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
