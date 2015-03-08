package com.ftfl.mettingplace.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "myMeetingPlace.db";
	private static final int DATABASE_VERSION = 5;

	public static final String TABLE_NAME_PLACE = "meeting_place";
	public static final String COL_PLACE_ID = "place_id";
	public static final String COL_PLACE_DATE = "date";
	public static final String COL_PLACE_TIME = "time";
	public static final String COL_PLACE_LATITUDE = "latitude";
	public static final String COL_PLACE_LONGITUDE = "longitude";
	public static final String COL_PLACE_DESCRIPTION = "description";
	public static final String COL_IMAGE = "image";
	public static final String COL_CONTACT_NAME = "name";
	public static final String COL_EMAIL = "email";
	public static final String COL_PHONE = "phone";

	// Database creation sql statement
	private static final String TABLE_CREATE_PLACE = "create table "
			+ TABLE_NAME_PLACE + "(" + COL_PLACE_ID
			+ " integer primary key autoincrement, " + COL_PLACE_DATE
			+ " text not null," + COL_PLACE_TIME + " text not null,"
			+ COL_PLACE_LATITUDE + " text not null," + COL_PLACE_LONGITUDE
			+ " text not null," + COL_PLACE_DESCRIPTION + " text not null ,"
			+ COL_IMAGE + " text not null ," + COL_CONTACT_NAME
			+ " text not null ," + COL_EMAIL + " text not null ," + COL_PHONE
			+ "  text not null);";

	public DatabaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(TABLE_CREATE_PLACE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseSQLiteHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLACE);
		onCreate(db);
	}

}
