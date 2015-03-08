package com.ftfl.mettingplace.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ftfl.mettingplace.util.MettingPlacesModel;

public class MyMettingPlaceDataSource {

	// Database fields
	private SQLiteDatabase mPlaceDatabase;
	private DatabaseSQLiteHelper mPlaceDbHelper;

	List<MettingPlacesModel> mPlaces = new ArrayList<MettingPlacesModel>();

	public MyMettingPlaceDataSource(Context context) {
		mPlaceDbHelper = new DatabaseSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mPlaceDatabase = mPlaceDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mPlaceDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(MettingPlacesModel meetingPlaces) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(DatabaseSQLiteHelper.COL_PLACE_DATE, meetingPlaces.getmDate());
		cv.put(DatabaseSQLiteHelper.COL_PLACE_TIME, meetingPlaces.getmTime());
		cv.put(DatabaseSQLiteHelper.COL_PLACE_LATITUDE, meetingPlaces.getmLatitude());
		cv.put(DatabaseSQLiteHelper.COL_PLACE_LONGITUDE, meetingPlaces.getmLongitude());
		cv.put(DatabaseSQLiteHelper.COL_PLACE_DESCRIPTION, meetingPlaces.getmRemarks());
		cv.put(DatabaseSQLiteHelper.COL_IMAGE, meetingPlaces.getmPhotoPath());
		cv.put(DatabaseSQLiteHelper.COL_CONTACT_NAME, meetingPlaces.getmContactName());
		cv.put(DatabaseSQLiteHelper.COL_EMAIL, meetingPlaces.getmContactMail());
		cv.put(DatabaseSQLiteHelper.COL_PHONE, meetingPlaces.getmContactPhone());

		Long check = mPlaceDatabase.insert(DatabaseSQLiteHelper.TABLE_NAME_PLACE, null, cv);
		mPlaceDatabase.close();

		this.close();

		if (check < 0)
			return false;
		else
			return true;
	}

	// Updating database by id
	public boolean updateData(long eId, MettingPlacesModel eUpdateObject) {
		this.open();
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(DatabaseSQLiteHelper.COL_PLACE_DATE, eUpdateObject.getmDate());
		cvUpdate.put(DatabaseSQLiteHelper.COL_PLACE_TIME, eUpdateObject.getmTime());
		cvUpdate.put(DatabaseSQLiteHelper.COL_PLACE_LATITUDE,
				eUpdateObject.getmLatitude());
		cvUpdate.put(DatabaseSQLiteHelper.COL_PLACE_LONGITUDE,
				eUpdateObject.getmLongitude());
		cvUpdate.put(DatabaseSQLiteHelper.COL_PLACE_DESCRIPTION,
				eUpdateObject.getmRemarks());
		cvUpdate.put(DatabaseSQLiteHelper.COL_IMAGE, eUpdateObject.getmPhotoPath());
		cvUpdate.put(DatabaseSQLiteHelper.COL_CONTACT_NAME,
				eUpdateObject.getmContactName());
		cvUpdate.put(DatabaseSQLiteHelper.COL_EMAIL, eUpdateObject.getmContactMail());
		cvUpdate.put(DatabaseSQLiteHelper.COL_PHONE, eUpdateObject.getmContactPhone());

		int check = mPlaceDatabase.update(DatabaseSQLiteHelper.TABLE_NAME_PLACE, cvUpdate,
				DatabaseSQLiteHelper.COL_PLACE_ID + "=" + eId, null);
		mPlaceDatabase.close();

		this.close();
		if (check == 0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eActivityId) {
		this.open();
		try {
			mPlaceDatabase.delete(DatabaseSQLiteHelper.TABLE_NAME_PLACE,
					DatabaseSQLiteHelper.COL_PLACE_ID + "=" + eActivityId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<MettingPlacesModel> allPlaces() {
		this.open();

		Cursor mCursor = mPlaceDatabase.query(DatabaseSQLiteHelper.TABLE_NAME_PLACE,
				new String[] { DatabaseSQLiteHelper.COL_PLACE_ID,
						DatabaseSQLiteHelper.COL_PLACE_DATE,
						DatabaseSQLiteHelper.COL_PLACE_TIME,
						DatabaseSQLiteHelper.COL_PLACE_LATITUDE,
						DatabaseSQLiteHelper.COL_PLACE_LONGITUDE,
						DatabaseSQLiteHelper.COL_PLACE_DESCRIPTION,
						DatabaseSQLiteHelper.COL_IMAGE, DatabaseSQLiteHelper.COL_CONTACT_NAME,
						DatabaseSQLiteHelper.COL_EMAIL, DatabaseSQLiteHelper.COL_PHONE,

				}, null, null, null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {

					String mActivityId = mCursor.getString(mCursor
							.getColumnIndex("place_id"));
					String mActivityDate = mCursor.getString(mCursor
							.getColumnIndex("date"));
					String mActivityTime = mCursor.getString(mCursor
							.getColumnIndex("time"));
					String mActivityLatitude = mCursor.getString(mCursor
							.getColumnIndex("latitude"));
					String mActivityLongitude = mCursor.getString(mCursor
							.getColumnIndex("longitude"));
					String mActivityDescription = mCursor.getString(mCursor
							.getColumnIndex("description"));
					String mImage = mCursor.getString(mCursor
							.getColumnIndex("image"));
					String mName = mCursor.getString(mCursor
							.getColumnIndex("name"));
					String mEmail = mCursor.getString(mCursor
							.getColumnIndex("email"));
					String mPhone = mCursor.getString(mCursor
							.getColumnIndex("phone"));

					mPlaces.add(new MettingPlacesModel(mActivityId, mActivityDate,
							mActivityTime, mActivityLatitude,
							mActivityLongitude, mActivityDescription, mImage,
							mName, mEmail, mPhone));

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return mPlaces;
	}

	public MettingPlacesModel singlePlaceData(long mActivityId) {
		this.open();
		MettingPlacesModel informationObject;
		String mId;
		String mDate;
		String mTime;
		String mLatitude;
		String mLongitude;
		String mRemarks;
		String mPhotoPath;
		String mName;
		String mEmail;
		String mPhone;

		Cursor mUpdateCursor = mPlaceDatabase.query(DatabaseSQLiteHelper.TABLE_NAME_PLACE,
				new String[] { DatabaseSQLiteHelper.COL_PLACE_ID,
						DatabaseSQLiteHelper.COL_PLACE_DATE,
						DatabaseSQLiteHelper.COL_PLACE_TIME,
						DatabaseSQLiteHelper.COL_PLACE_LATITUDE,
						DatabaseSQLiteHelper.COL_PLACE_LONGITUDE,
						DatabaseSQLiteHelper.COL_PLACE_DESCRIPTION,
						DatabaseSQLiteHelper.COL_IMAGE, DatabaseSQLiteHelper.COL_CONTACT_NAME,
						DatabaseSQLiteHelper.COL_EMAIL, DatabaseSQLiteHelper.COL_PHONE, },
				DatabaseSQLiteHelper.COL_PLACE_ID + "=" + mActivityId, null, null,
				null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(0);
		mDate = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PLACE_DATE));
		mTime = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PLACE_TIME));
		mLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PLACE_LATITUDE));

		mLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PLACE_LONGITUDE));
		mRemarks = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PLACE_DESCRIPTION));
		mPhotoPath = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_IMAGE));
		mName = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_CONTACT_NAME));
		mEmail = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_EMAIL));
		mPhone = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(DatabaseSQLiteHelper.COL_PHONE));

		mUpdateCursor.close();
		informationObject = new MettingPlacesModel(mId, mDate, mTime, mLatitude,
				mLongitude, mRemarks, mPhotoPath, mName, mEmail, mPhone);
		this.close();
		return informationObject;
	}

}
