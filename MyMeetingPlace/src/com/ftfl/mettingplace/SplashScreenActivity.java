package com.ftfl.mettingplace;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		 
        new Timer().schedule(new TimerTask() {
			public void run() {
				SplashScreenActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						startActivity(new Intent(SplashScreenActivity.this,
								MyMeetingPlaceHomeActivity.class));
						finish();
					}
				});
			}
		}, 2000);
	}

	
}
