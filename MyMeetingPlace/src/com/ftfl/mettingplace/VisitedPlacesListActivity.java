package com.ftfl.mettingplace;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ftfl.mettingplace.adapter.Customadapter;
import com.ftfl.mettingplace.database.MyMettingPlaceDataSource;
import com.ftfl.mettingplace.database.DatabaseSQLiteHelper;
import com.ftfl.mettingplace.util.GPSTracker;
import com.ftfl.mettingplace.util.MettingPlacesModel;

public class VisitedPlacesListActivity extends Activity {
	List<String> mIdList = new ArrayList<String>();

	DatabaseSQLiteHelper mDBHelper;
	MyMettingPlaceDataSource mPlaceDataSource;
	ListView mListView;
	TextView mTvMyCurrentPositionLat;
	TextView mTvMyCurrentPositionLong;
	TextView mId_tv = null;
	TextView mLatitude_tv = null;
	TextView mLongitude_tv = null;
	String mId = "";
	String mLatitude = "";
	String mLongitude = "";
	GPSTracker gps;
	TextView mCurrentLocation;
	Button mBackButton;
	Integer mPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visitedplace_listview);
		mTvMyCurrentPositionLat = (TextView) findViewById(R.id.tvMyCurrentPositionLat);
		mTvMyCurrentPositionLong = (TextView) findViewById(R.id.tvMyCurrentPositionLong);
		//mCurrentLocation = (TextView) findViewById(R.id.tvMyCurrentPositionLat);

		// create class object
		gps = new GPSTracker(VisitedPlacesListActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			// \n is for new line

			mTvMyCurrentPositionLat.setText("LAT: "+String.valueOf(latitude));
			mTvMyCurrentPositionLong.setText("LNG: "+String.valueOf(longitude));
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
		mDBHelper = new DatabaseSQLiteHelper(this);
		mPlaceDataSource = new MyMettingPlaceDataSource(this);
		setListData();
		List<MettingPlacesModel> galery_list = mPlaceDataSource.allPlaces();
		Customadapter arrayAdapter = new Customadapter(this, galery_list);
		final String[] option = new String[] { "View Contact Details",
				"Edit Data", "Delete Data" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Option");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Log.e("Selected Item", String.valueOf(which));
				if (which == 0) {
					viewContact(mPosition);
				}

				if (which == 1) {
					editData(mPosition);
				}

				if (which == 2) {
					deleteData(mPosition);
				}
			}

		});
		final AlertDialog dialog = builder.create();

		mListView = (ListView) findViewById(R.id.lvPlacesList);
		mListView.setAdapter(arrayAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mPosition = position;
				dialog.show();
			}
		});

		mBackButton = (Button) findViewById(R.id.btn_home);
		mBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						MyMeetingPlaceHomeActivity.class);
				startActivity(i);
			}
		});
	}

	public void viewContact(Integer ePosition) {
		Intent mViewIntent = new Intent(getApplicationContext(),
				ContactDetailsActivity.class);
		Long eActivityId = Long.parseLong(mIdList.get(ePosition));
		mViewIntent.putExtra("id", eActivityId.toString());
		startActivity(mViewIntent);
	}

	public void editData(Integer ePosition) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				MettingPlaceUpdateActivity.class);
		Long eActivityId = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra("id", eActivityId.toString());
		startActivity(mEditIntent);
	}

	private void setListData() {
		MyMettingPlaceDataSource mPlaceDataSource = new MyMettingPlaceDataSource(this);
		List<MettingPlacesModel> galery_list = mPlaceDataSource.allPlaces();
		for (int i = 0; i < galery_list.size(); i++) {
			MettingPlacesModel mPlaces = galery_list.get(i);
			mIdList.add(mPlaces.getmId());

		}

	}

	public void deleteData(Integer ePosition) {
		mPlaceDataSource = new MyMettingPlaceDataSource(this);
		long eActivityId = Long.parseLong(mIdList.get(ePosition));
		mPlaceDataSource.deleteData(eActivityId);
		Intent i = new Intent(getApplicationContext(), VisitedPlacesListActivity.class);
		startActivity(i);
	}

}
