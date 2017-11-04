package isemin.com.menutranslator.isemin.com.dictionary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import isemin.com.menutranslator.HttpClient;
import shahruk.com.menutranslator.R;

public class Dictionary extends AppCompatActivity {

    private static final String TAG = Dictionary.class.getSimpleName();

    TextView mWord;
    EditText mEditText;
    private String language = "en";
    private String word, word_id, url;
    private Button mButton;
    private ProgressBar mPb;
    private String definition1, definition2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

 //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mWord = (TextView) findViewById(R.id.wordText);
        mEditText = (EditText) findViewById(R.id.search);
        mButton = (Button) findViewById(R.id.btn_search);
        mPb = (ProgressBar) findViewById(R.id.pb);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word = mEditText.getText().toString();
                if (word.isEmpty()) {
                    Toast.makeText(Dictionary.this, "Empty space not allowed", Toast.LENGTH_SHORT).show();
                    mWord.setText("");
                } else {
                    word_id = word.toLowerCase().toString();
                    url = "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
                    new DownloadWords().execute(url);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class DownloadWords extends AsyncTask<String, Integer, String> {

        private static final String TAG = "CallbackTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPb.setVisibility(View.VISIBLE);
            mWord.setText("");
        }


        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new HttpClient();

            // Making a request to url and getting response
            String jsonStr = httpClient.connect(url);

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray resultArr = jsonObj.getJSONArray("results");

                    JSONArray lexicalEntriesArr = resultArr.getJSONObject(0).getJSONArray("lexicalEntries");
                    JSONArray entriesArr = lexicalEntriesArr.getJSONObject(0).getJSONArray("entries");
                    JSONArray sensesArr = entriesArr.getJSONObject(0).getJSONArray("senses");

                    JSONArray definitionsArrOne = sensesArr.getJSONObject(0).getJSONArray("definitions");

                    JSONArray definitionsArrTwo = sensesArr.getJSONObject(1).getJSONArray("definitions");

                    definition1 = definitionsArrOne.toString();

                    definition2 = definitionsArrTwo.toString();

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mPb.setVisibility(View.GONE);
            mWord.append("Definition One: " + "\n\n" +
                    definition1 + "\n\n\n" +
                    "Definition Two: " + "\n\n" +
                    definition2);
        }
    }
}
