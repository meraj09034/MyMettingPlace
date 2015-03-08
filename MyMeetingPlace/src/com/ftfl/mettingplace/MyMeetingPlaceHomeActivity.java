package com.ftfl.mettingplace;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyMeetingPlaceHomeActivity extends Activity {

	Button mBtnRegister;
	Button mBtnRetrieve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_main);

		mBtnRegister = (Button) findViewById(R.id.btnRegister);
		mBtnRetrieve = (Button) findViewById(R.id.btnRetrieve);

		mBtnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						InsertMettingPlaceInfoActivity.class);
				startActivity(i);

			}
		});

		mBtnRetrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						VisitedPlacesListActivity.class);
				startActivity(i);

			}
		});

	}

}
