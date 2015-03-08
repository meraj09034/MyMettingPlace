package com.ftfl.mettingplace;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.mettingplace.R;
import com.ftfl.mettingplace.database.MyMettingPlaceDataSource;
import com.ftfl.mettingplace.util.GPSTracker;
import com.ftfl.mettingplace.util.MettingPlacesModel;

public class ContactDetailsActivity extends Activity {
	String mID = "";
	Long mLId;
	MyMettingPlaceDataSource mImageDS = null;
	MettingPlacesModel mViewContact = null;
	TextView mEtName;
	TextView mEtMail;
	TextView mEtPhone;
	ImageView mBtnCall;
	ImageView mBtnEmail;
	ImageView mBtnSMS;
	Button mBtnAddContact;
	TextView mTvMyCurrentPositionLat;
	TextView mTvMyCurrentPositionLong;
	GPSTracker gps;
	String mName;
	String mEmail;
	String mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("id");

		mEtName = (TextView) this.findViewById(R.id.viewName);
		mEtMail = (TextView) this.findViewById(R.id.viewEmail);
		mEtPhone = (TextView) this.findViewById(R.id.viewMobile);
		mBtnCall = (ImageView) findViewById(R.id.view_img_call);
		mBtnEmail = (ImageView) findViewById(R.id.view_img_Email);
		mBtnSMS = (ImageView) findViewById(R.id.view_img_message);
		mBtnAddContact = (Button) findViewById(R.id.btn_add_contact_phone);

		// final String mName = mViewContact.getmContactName();
		// final String mEmail = mViewContact.getmContactMail();
		// final String mPhone = mViewContact.getmContactPhone();
		//

		if (mID != null) {
			mLId = Long.parseLong(mID);
			mImageDS = new MyMettingPlaceDataSource(this);
			mViewContact = mImageDS.singlePlaceData(mLId);

			mName = mViewContact.getmContactName();
			mEmail = mViewContact.getmContactMail();
			mPhone = mViewContact.getmContactPhone();

			mEtName.setText(mName);

			mEtMail.setText(mEmail);

			mEtPhone.setText(mPhone);

			mTvMyCurrentPositionLat = (TextView) findViewById(R.id.viewLatitude);
			mTvMyCurrentPositionLong = (TextView) findViewById(R.id.viewLongitude);
			// mCurrentLocation = (TextView)
			// findViewById(R.id.tvMyCurrentPositionLat);

			// create class object
			gps = new GPSTracker(ContactDetailsActivity.this);
			// check if GPS enabled
			if (gps.canGetLocation()) {
				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				// \n is for new line

				mTvMyCurrentPositionLat.setText(String.valueOf(latitude));
				mTvMyCurrentPositionLong.setText(String.valueOf(longitude));
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}
		}
		mBtnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ mPhone));
				startActivity(intent);

			}
		});

		mBtnEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", mEmail, null));
				// emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));

			}
		});
		mBtnSMS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {

					Intent smsIntent = new Intent(Intent.ACTION_VIEW);
					smsIntent.putExtra("address", mPhone);
					smsIntent.setType("vnd.android-dir/mms-sms");
					startActivity(smsIntent);

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "SMS faild!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
		mBtnAddContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
				int rawContactInsertIndex = ops.size();

				ops.add(ContentProviderOperation
						.newInsert(RawContacts.CONTENT_URI)
						.withValue(RawContacts.ACCOUNT_TYPE, null)
						.withValue(RawContacts.ACCOUNT_NAME, null).build());
				ops.add(ContentProviderOperation
						.newInsert(Data.CONTENT_URI)
						.withValueBackReference(Data.RAW_CONTACT_ID,
								rawContactInsertIndex)
						.withValue(Data.MIMETYPE,
								StructuredName.CONTENT_ITEM_TYPE)
						.withValue(StructuredName.DISPLAY_NAME, mName) // Name
																		// of
																		// the
																		// person
						.build());
				ops.add(ContentProviderOperation
						.newInsert(Data.CONTENT_URI)
						.withValueBackReference(
								ContactsContract.Data.RAW_CONTACT_ID,
								rawContactInsertIndex)
						.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
						.withValue(Phone.NUMBER, mPhone) // Number of the person
						.withValue(Phone.TYPE, Phone.TYPE_MOBILE).build()); // Type
																			// of
																			// mobile
																			// number
				try {
					ContentProviderResult[] res = getContentResolver()
							.applyBatch(ContactsContract.AUTHORITY, ops);

					Toast.makeText(getApplicationContext(),
							"Successfully  Contract Added !!!!!!!",
							Toast.LENGTH_LONG).show();
				} catch (RemoteException e) {
					// error
				} catch (OperationApplicationException e) {
					// error
				}
			}
		});

	}

}
