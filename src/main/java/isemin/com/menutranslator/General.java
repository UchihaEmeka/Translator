package isemin.com.menutranslator;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import isemin.com.menutranslator.isemin.com.dictionary.DatabaseHelper;
import shahruk.com.menutranslator.R;


public class General extends ListActivity {
    ArrayList<HashMap<String, String>> restList;
    List<Item> list = new ArrayList<Item>();
    DatabaseHelper db;
    private static final String KEY_RES_HW = "headword";
    private static final String KEY_RES_MY = "malay";
    private static final String KEY_RES_FR = "french";
    private static final String KEY_RES_SP = "spanish";
    private static final String KEY_RES_IT = "italian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        db = new DatabaseHelper(getApplicationContext());
        restList = new ArrayList<HashMap<String, String>>();

        list = db.getAllGeneralList();

        for(Item it : list){
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            map.put(KEY_RES_HW, it.HeadSentence);
            map.put(KEY_RES_MY, it.Malay);
            map.put(KEY_RES_FR, it.French);
            map.put(KEY_RES_SP, it.Spanish);
            map.put(KEY_RES_IT, it.Italian);


            // adding HashList to ArrayList
            restList.add(map);
        }


        runOnUiThread(new Runnable() {
            public void run() {

                ListAdapter adapter = new SimpleAdapter(
                        General.this, restList,
                        R.layout.restaurent, new String[]{KEY_RES_HW,
                        KEY_RES_MY, KEY_RES_FR, KEY_RES_SP, KEY_RES_IT},
                        new int[]{R.id.HW, R.id.MY, R.id.FR, R.id.SP, R.id.IT});

                //updating listview
                setListAdapter(adapter);
            }});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as parent activity is specified in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
