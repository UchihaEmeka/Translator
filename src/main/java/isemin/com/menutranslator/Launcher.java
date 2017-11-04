package isemin.com.menutranslator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class Launcher extends Activity {
    private final int WAIT_TIME = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(Launcher.this,MainActivity.class);
                Launcher.this.startActivity(mainIntent);
                Launcher.this.finish();
            }
        }, WAIT_TIME);
    }



}
