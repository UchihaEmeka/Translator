package isemin.com.menutranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import isemin.com.menutranslator.isemin.com.dictionary.DatabaseHelper;
import isemin.com.menutranslator.isemin.com.dictionary.Dict;


public class ViewWord extends AppCompatActivity {
    TextView Word;
    TextView Type;
    TextView Def;
    DatabaseHelper db;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);
        db = new DatabaseHelper(getApplicationContext());
        Word = (TextView)findViewById(R.id.textViewWord);
        Type = (TextView)findViewById(R.id.textViewType);
        Def = (TextView)findViewById(R.id.textViewDef);

        // getting job details from intent
        Intent i = getIntent();

        // getting job id (pid) from intent
        name = i.getStringExtra("name");

        Dict dt = new Dict();
        dt = db.getAWord(name);

        Word.setText(dt.word);
        Type.setText(dt.type);
        Def.setText(dt.definition);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
