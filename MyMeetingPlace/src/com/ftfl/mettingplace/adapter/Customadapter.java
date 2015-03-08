package com.ftfl.mettingplace.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.mettingplace.R;
import com.ftfl.mettingplace.util.GPSTracker;
import com.ftfl.mettingplace.util.MettingPlacesModel;

public class Customadapter extends ArrayAdapter<MettingPlacesModel> {

	private static LayoutInflater mInflater = null;

	List<MettingPlacesModel> mPlaces;
	public String mImage;
	String yourLatitude;
	String yourlongitude;
	String provider;
	Location location;
	GPSTracker gps;

	public Customadapter(Activity context, List<MettingPlacesModel> ePlaces) {
		super(context, R.layout.activity_adapter_placeslist, ePlaces);
		this.mPlaces = ePlaces;

		/*********** Layout inflator to call external xml layout () ***********/
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView mId;
		public TextView mDate;
		public TextView mTime;
		public TextView mLatitude;
		public TextView mLongitude;
		public TextView mDescription;
		public TextView mDistance;
		public ImageView mIvImage;

	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = mInflater.inflate(R.layout.activity_adapter_placeslist, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.mId = (TextView) vi.findViewById(R.id.tvPlaceId);
			holder.mDate = (TextView) vi.findViewById(R.id.tvDate);
			holder.mTime = (TextView) vi.findViewById(R.id.tvTime);
			holder.mLatitude = (TextView) vi.findViewById(R.id.tvLatitude);
			holder.mLongitude = (TextView) vi.findViewById(R.id.tvLongitude);
			holder.mDescription = (TextView) vi
					.findViewById(R.id.tvDescription);
			holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);
			holder.mIvImage = (ImageView) vi.findViewById(R.id.ivImage);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		MettingPlacesModel places;
		places = mPlaces.get(position);

		holder.mId.setText(places.getmId().toString());
		holder.mDate.setText("Date: " + places.getmDate().toString());
		holder.mTime.setText("Time: " + places.getmTime().toString());
		holder.mLatitude.setText("LAT: " + places.getmLatitude().toString());
		holder.mLongitude.setText("LNG: " + places.getmLongitude().toString());
		holder.mDescription.setText("Description: "
				+ places.getmRemarks().toString());
		holder.mDistance = (TextView) vi.findViewById(R.id.tvDistance);

		// create class object
		gps = new GPSTracker(getContext());
		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			yourLatitude = String.valueOf(latitude);
			yourlongitude = String.valueOf(longitude);
			// \n is for new line

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		Location l1 = new Location("One");
		l1.setLatitude(Double.parseDouble(yourLatitude));
		l1.setLongitude(Double.parseDouble(yourlongitude));

		Location l2 = new Location("Two");
		l2.setLatitude(Double.parseDouble(places.getmLatitude().toString()));
		l2.setLongitude(Double.parseDouble(places.getmLongitude().toString()));

		float distance_bw_one_and_two = Math.round(l1.distanceTo(l2));
		float distance = 0;
		if (distance_bw_one_and_two > 1000) {
			distance = distance_bw_one_and_two / 1000;
			holder.mDistance.setText("Distance: " + String.valueOf(distance)
					+ "km");
		} else {
			holder.mDistance.setText("Distance: "
					+ String.valueOf(distance_bw_one_and_two) + "m");
		}
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap bitmap = BitmapFactory.decodeFile(places.getmPhotoPath(),
				options);
		holder.mIvImage.setImageBitmap(bitmap);

		;

		return vi;
	}
}
