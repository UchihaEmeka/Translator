package isemin.com.menutranslator.isemin.com.translator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;

import isemin.com.menutranslator.R;


public class MyTranslator extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Spinner sp;
    String[] myLanguages;
    String ang;
    String Lang;
    String text;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_translator);
        myLanguages = new String[]{"MALAY", "FRENCH", "GERMAN", "INDONESIAN", "HINDI", "THAI","IGBO","HAUSA","YORUBA"};
        sp = (Spinner) findViewById(R.id.mySpinner);
        tts = new TextToSpeech(this, this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myLanguages);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aa);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lang = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Target Lang : " + lang, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});
        Lang = myLanguages[sp.getSelectedItemPosition()];
      //  final String tryN =SourceLanguage(lang);
        ((Button) findViewById(R.id.bSpeak)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               speakOut(((TextView) findViewById(R.id.tvTranslatedText)).getText().toString());
            }
        });

        ((Button) findViewById(R.id.bTranslate)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                text = ((EditText) findViewById(R.id.etUserText)).getText().toString();

                class bgStuff extends AsyncTask<Void, Void, Void> {
                    final String from= "en";
                    protected final String to = TargetLang();
                    String Text=text.replace(" ", "%20");
                    String translatedText = "";

                    @Override
                    protected Void doInBackground(Void... params) {
                        // TODO Auto-generated method stub
                        try {
                            translatedText = translate(Text,from,to);
                        } catch (Exception e) {
                            e.printStackTrace();
                            translatedText = "Please connect to internet!";
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // TODO Auto-generated method stub
                        ((TextView) findViewById(R.id.tvTranslatedText)).setText(translatedText);
                        super.onPostExecute(result);
                    }

                }

                new bgStuff().execute();
            }
        });
    }

    public String TargetLang() {
        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
        if (lang.equals("MALAY")){
            ang="ms";
        }else  if (lang.equals("FRENCH")) {
            ang="fr";

        } else if (lang.equals("GERMAN")) {
            ang="de";

        } else if (lang.equals("INDONESIAN")) {
            ang="id";

        } else if (lang.equals("HINDI")) {
            ang="hi";

        } else if (lang.equals("THAI")) {
            ang="th";

        } else if (lang.equals("ENGLISH")) {
            ang="en";}
        else if (lang.equals("IGBO")) {
            ang="ig";}
        else if (lang.equals("HAUSA")) {
            ang="ha";}
        else if (lang.equals("YORUBA")) {
            ang="yo";}


        return ang;
    }
    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.GERMAN);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                //speakOut("Ich");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    String translate(String text, String from, String to)  {
        StringBuilder result = new StringBuilder();
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String urlStr = "https://www.googleapis.com/language/translate/v2?key=AIzaSyDAGfq1u1zgsNduapAwcyVYWu52mFIPJd8&source="+from+"&target="+to+"&q="+text;
            //"https://www.googleapis.com/language/translate/v2?key=" + key + "&q=" + encodedText + "&target=" + to + "&source=" + from;

            URL url = new URL(urlStr);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            InputStream stream;
            if (conn.getResponseCode() == 200) //success
            {
                stream = conn.getInputStream();
            } else
                stream = conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(result.toString());

            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("error") == null) {
                    String translatedText = obj.get("data").getAsJsonObject().
                            get("translations").getAsJsonArray().
                            get(0).getAsJsonObject().
                            get("translatedText").getAsString();
                    return translatedText;}
            }
            if (conn.getResponseCode() != 200) {
                System.err.println(result);
            }

        } catch (IOException | JsonSyntaxException ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }



}
