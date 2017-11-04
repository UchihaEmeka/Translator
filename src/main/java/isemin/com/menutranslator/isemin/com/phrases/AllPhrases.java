package isemin.com.menutranslator.isemin.com.phrases;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import isemin.com.menutranslator.R;


/**
 * Created by userpc on 10/7/2017.
 */

public class AllPhrases extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
    }
    //go To General Phrases
    public void goToGeneralPhrases(View v) {
        Intent intent = new Intent(this, Generalphrase.class);
        startActivity(intent);
    }

    //go To Restaurant Phrases
    public void goToRestaurant(View v) {
        Intent intent = new Intent(this, Restaurantphrase.class);
        startActivity(intent);
    }
    //go To Time ans Day Phrases
    public void goToTimeAndDay(View v) {
        Intent intent = new Intent(this, TimeAndDay.class);
        startActivity(intent);
    }
    //go To Direction Phrases
    public void goToDirections(View v) {
        Intent intent = new Intent(this, Directionphrase.class);
        startActivity(intent);
    }
    //go To others Phrases
    public void goToOthers(View v) {
        Intent intent = new Intent(this, Others.class);
        startActivity(intent);}
}
