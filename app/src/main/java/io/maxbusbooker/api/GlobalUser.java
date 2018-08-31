package io.maxbusbooker.api;

import android.os.Parcelable;

import io.maxbusbooker.util.AppUtils;

public abstract class GlobalUser implements Parcelable {
	
	public String uid;
	public String username;
	public String profile;
	public String phoneNumber;
	//Default gender is Male
	public String gender = AppUtils.GENDER_MALE;
	public String token;
	
	@Override
	public String toString() {
		return "GlobalUser{" +
				       "uid='" + uid + '\'' +
				       ", username='" + username + '\'' +
				       ", profile='" + profile + '\'' +
				       ", phoneNumber='" + phoneNumber + '\'' +
				       ", gender='" + gender + '\'' +
				       ", token='" + token + '\'' +
				       '}';
	}
}
