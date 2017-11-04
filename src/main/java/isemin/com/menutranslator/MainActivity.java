package isemin.com.menutranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import isemin.com.menutranslator.ocr.camera.OCRActivity;
import isemin.com.menutranslator.isemin.com.dictionary.Dictionary;
import isemin.com.menutranslator.isemin.com.phrases.AllPhrases;
import isemin.com.menutranslator.isemin.com.translator.MyTranslator;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //goToOCR
    public void goToOCR(View v) {
        Intent intent = new Intent(this, OCRActivity.class);
        startActivity(intent);
    }
    //goToPhrases
    public void goToPhrases(View v) {
        Intent intent = new Intent(this, AllPhrases.class);
        startActivity(intent);
    }
    //goToSearch //Dictionary
    public void goToSearch(View v) {
        Intent intent = new Intent(this, Dictionary.class);
        startActivity(intent);
    }
    //goToMyTranslator
    public void goToTranslator(View v) {
        Intent intent = new Intent(this, MyTranslator.class);
        startActivity(intent);
    }



}