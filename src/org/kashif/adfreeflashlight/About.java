package org.kashif.adfreeflashlight;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends Activity {
    String infoText = "Use your phones flashlight as Torch to use in the dark. There are similar apps but this one is 100% free & open source - without ads."
	    + "\n\n"
	    + "With this app you can turn On or Off your phone's flashlight. One Unique feature of this app while making in 2014 is that you can choose whether to keep flashlight On or Off in the background - ie: after locked, after screen off, while using other apps."
	    + "\n\n "
	    + " This is my first app, all praise and thanks to Allah who alone is worthy of worship, with Allah's grace, i am with this app stepping into Android development.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_about);
	// Show the Up button in the action bar.
	setupActionBar();

	TextView tv = (TextView) findViewById(R.id.infoView);
	tv.setText(infoText);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	}
    }

}
