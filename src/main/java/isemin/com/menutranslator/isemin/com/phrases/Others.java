package isemin.com.menutranslator.isemin.com.phrases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import isemin.com.menutranslator.R;

/**
 * Created by userpc on 10/7/2017.
 */

public class Others extends AppCompatActivity {
        String[] others =PhraseBox.getOthers();
         private TextView mPhrasess;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayphrases);
        mPhrasess =  findViewById(R.id.phrases_list);
        for (String i : others) {
            mPhrasess.append(i + "\n");
    }
}}
