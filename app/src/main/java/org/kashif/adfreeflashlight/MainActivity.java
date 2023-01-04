package org.kashif.adfreeflashlight;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public Camera camera;
    public Camera.Parameters camera_parameters;

    public String flash_mode;
    public boolean isCam;
    public boolean isFlash = false;
    public boolean bgFlash = false;
    public boolean flashStatus = true;

    public CheckBox checkbox;
    ImageView iv;
    RelativeLayout RL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RL = (RelativeLayout) findViewById(R.id.mainView);
        iv = (ImageView) findViewById(R.id.imageView1);

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fbuttonResponse();
            }
        });

        checkbox = (CheckBox) findViewById(R.id.checkBox1);
        checkbox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked())
                    bgFlash = true;
                else if (((CheckBox) v).isChecked() == false)
                    bgFlash = false;

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();

        initCam();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isFlash == true && bgFlash == false) {
            turnOff();
        }

    }

    @Override
    public void onDestroy() {

        if (isFlash == true) {
            camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(camera_parameters);

            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }

        super.onDestroy();
    }

    public void fbuttonResponse() {
        if (flashStatus == true) {

            if (isFlash == true) {
                Anim();
                turnOff();
            }
        } else if (flashStatus == false) {
            Anim();
            initCam();
        }

    }

    public void turnOff() {

        flashStatus = false;

        Toast.makeText(this, "Flashlight turned Off", Toast.LENGTH_SHORT)
                .show();

        camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(camera_parameters);

        RL.setBackgroundColor(getResources().getColor(R.color.black));
    }

    // Animate icon
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void Anim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator va = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
            va.setDuration(750);
            va.start();
        }

    }

    public void initCam() {

        try {
            camera = Camera.open();
            camera_parameters = camera.getParameters();
            flash_mode = camera_parameters.getFlashMode();
        } catch (Exception e) {
            isCam = false;
            isFlash = false;
        }

        if (flash_mode != null) {
            isFlash = true;
            flashStatus = true;
            camera_parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(camera_parameters);

            Toast.makeText(this, "Flashlight turned On", Toast.LENGTH_SHORT)
                    .show();

            RL.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (flash_mode == null) {
            isFlash = false;
            Toast.makeText(this, "Flashlight not Available", Toast.LENGTH_LONG)
                    .show();

            TextView fna = (TextView) findViewById(R.id.flashna);
            fna.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:
                about();
                return true;

            case R.id.action_more:
                gotoWeb();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void about() {

        Intent intent = new Intent(this, About.class);
        startActivity(intent);

    }

    void gotoWeb() {
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/dev?id=8296321551976073279"));
        startActivity(intent);
    }

}
