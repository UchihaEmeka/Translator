package isemin.com.menutranslator.ocr.camera;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import java.io.IOException;

import isemin.com.menutranslator.R;

import static isemin.com.menutranslator.R.id.OPTIONS_COPY_RECOGNIZED_TEXT_ID;
import static isemin.com.menutranslator.R.id.OPTIONS_SHARE_RECOGNIZED_TEXT_ID;

public class OCRActivity extends Activity {
    private SurfaceView cameraView;
    private TextView barcodeValue;
    private CameraSource cameraSource;
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activit_ocr);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        barcodeValue = (TextView) findViewById(R.id.text_value);
        // Get the application context
        mContext = getApplicationContext();
        // Get the activity
        mActivity = OCRActivity.this;
        // First step to show a custom context menu on text view
        registerForContextMenu(barcodeValue);

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                Log.d("Main", "receiveDetections");
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {
                    barcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder value = new StringBuilder();
                            for (int i = 0; i < items.size(); ++i) {
                                TextBlock item = items.valueAt(i);
                                value.append(item.getValue());
                                value.append("\n");
                            }
                            //update text block content to TextView
                            barcodeValue.setText(value.toString());

                        }
                    });
                }

            }
        });

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available.");
        }

        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    //noinspection MissingPermission
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }



    //
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.setHeaderTitle("Select an option for TextView text.");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        switch (item.getItemId()) {

            case OPTIONS_COPY_RECOGNIZED_TEXT_ID:

                clipboardManager.setText(barcodeValue.getText());
                if (clipboardManager.hasText()) {
                    Toast toast = Toast.makeText(this, "Text copied.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                return true;
            //   }
            case OPTIONS_SHARE_RECOGNIZED_TEXT_ID:
                Intent shareRecognizedTextIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareRecognizedTextIntent.setType("text/plain");
                shareRecognizedTextIntent.putExtra(android.content.Intent.EXTRA_TEXT, barcodeValue.getText());
                startActivity(Intent.createChooser(shareRecognizedTextIntent, "Share via"));
                return true;
        }
                return super.onContextItemSelected(item);
        }
    }

