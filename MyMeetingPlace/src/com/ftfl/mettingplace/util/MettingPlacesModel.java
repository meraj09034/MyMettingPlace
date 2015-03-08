package com.ftfl.mettingplace.util;

public class MettingPlacesModel {

	String mId;
	String mDate;
	String mTime;
	String mLatitude;
	String mLongitude;
	String mRemarks;
	String mPhotoPath;
	String mContactName;
	String mContactMail;
	String mContactPhone;

	public String getmContactName() {
		return mContactName;
	}

	public void setmContactName(String mContactName) {
		this.mContactName = mContactName;
	}

	public String getmContactMail() {
		return mContactMail;
	}

	public void setmContactMail(String mContactMail) {
		this.mContactMail = mContactMail;
	}

	public String getmContactPhone() {
		return mContactPhone;
	}

	public void setmContactPhone(String mContactPhone) {
		this.mContactPhone = mContactPhone;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmRemarks() {
		return mRemarks;
	}

	public void setmRemarks(String mRemarks) {
		this.mRemarks = mRemarks;
	}

	public String getmPhotoPath() {
		return mPhotoPath;
	}

	public void setmPhotoPath(String mPhotoPath) {
		this.mPhotoPath = mPhotoPath;
	}

	public MettingPlacesModel() {
		super();
	}

	public MettingPlacesModel(String mId, String mDate, String mTime,
			String mLatitude, String mLongitude, String mRemarks,
			String mPhotoPath, String mContactName, String mContactMail,
			String mContactPhone) {
		super();
		this.mId = mId;
		this.mDate = mDate;
		this.mTime = mTime;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mRemarks = mRemarks;
		this.mPhotoPath = mPhotoPath;
		this.mContactName = mContactName;
		this.mContactMail = mContactMail;
		this.mContactPhone = mContactPhone;
	}

	public MettingPlacesModel(String mDate, String mTime, String mLatitude,
			String mLongitude, String mRemarks, String mPhotoPath,
			String mContactName, String mContactMail, String mContactPhone) {
		super();
		this.mDate = mDate;
		this.mTime = mTime;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mRemarks = mRemarks;
		this.mPhotoPath = mPhotoPath;
		this.mContactName = mContactName;
		this.mContactMail = mContactMail;
		this.mContactPhone = mContactPhone;
	}
	

}
