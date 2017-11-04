package isemin.com.menutranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import shahruk.com.menutranslator.R;
import java.util.ArrayList;
import java.util.List;

import isemin.com.menutranslator.isemin.com.dictionary.DatabaseHelper;
import isemin.com.menutranslator.isemin.com.dictionary.Dict;


public class Search extends AppCompatActivity {

    DatabaseHelper db;
    private static final String KEY_NAME = "name";
    private String[] DictionaryNames;
    List<Dict> SearchList = new ArrayList<Dict>();

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db = new DatabaseHelper(getApplicationContext());

        SearchList = db.getAllWordList();
        DictionaryNames = new String[SearchList.size()];
        int j = 0;
        for(Dict dt : SearchList){
            DictionaryNames[j++] = dt.word;
        }


        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_station, R.id.word_name, DictionaryNames);
        lv.setAdapter(adapter);

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Search.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                final String name = ((TextView) view.findViewById(R.id.word_name)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        ViewWord.class);
                // sending pid to next activity
                in.putExtra("name", name);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);



            }
        });

    }



}
