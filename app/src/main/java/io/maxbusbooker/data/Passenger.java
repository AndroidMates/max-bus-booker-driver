package io.maxbusbooker.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import io.maxbusbooker.api.GlobalUser;
import io.maxbusbooker.util.AppUtils;

/**
 * Project : max-travel-aid
 * Created by Dennis Bilson on Wed at 6:46 PM.
 * Package name : io.travelaid.data
 * <p>
 * Passenger data model
 */
public class Passenger extends GlobalUser {
	
	private String uid;
	private String username;
	private String profile;
	private String phoneNumber;
	//Default gender is Male
	private String gender = AppUtils.GENDER_MALE;
	private String token;
	
	//For serialization
	public Passenger() {
	}
	
	public Passenger(String uid, String username, String profile, String phoneNumber, String gender) {
		this.uid = uid;
		this.username = username;
		this.profile = profile;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.token = FirebaseInstanceId.getInstance().getToken();
	}
	
	
	protected Passenger(Parcel in) {
		uid = in.readString();
		username = in.readString();
		profile = in.readString();
		phoneNumber = in.readString();
		gender = in.readString();
		token = in.readString();
	}
	
	public static Map<String, Object> toHashMap(Passenger user) {
		Map<String, Object> hashMap = new HashMap<>(0);
		hashMap.put("uid", user.uid);
		hashMap.put("username", user.username);
		hashMap.put("profile", user.profile);
		hashMap.put("phoneNumber", user.phoneNumber);
		hashMap.put("gender", user.gender);
		hashMap.put("token", user.token);
		return hashMap;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(uid);
		dest.writeString(username);
		dest.writeString(profile);
		dest.writeString(phoneNumber);
		dest.writeString(gender);
		dest.writeString(token);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<Passenger> CREATOR = new Parcelable.Creator<Passenger>() {
		@Override
		public Passenger createFromParcel(Parcel in) {
			return new Passenger(in);
		}
		
		@Override
		public Passenger[] newArray(int size) {
			return new Passenger[size];
		}
	};
	
	public String getToken() {
		return token;
	}
	
	public String getUid() {
		return uid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public static class Builder {
		private String uid;
		private String username;
		private String profile;
		private String phoneNumber;
		//Default gender is Male
		private String gender = AppUtils.GENDER_MALE;
		private String token;
		
		public Builder setUid(String uid) {
			this.uid = uid;
			return this;
		}
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder setProfile(String profile) {
			this.profile = profile;
			return this;
		}
		
		public Builder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		
		public Builder setGender(String gender) {
			this.gender = gender;
			return this;
		}
		
		public Builder setToken(String token) {
			this.token = token;
			return this;
		}
		
		public Passenger build() {
			return new Passenger(uid, username, profile, phoneNumber, gender);
		}
		
		public Passenger from(Passenger existing) {
			return new Passenger(existing.uid, existing.username, existing.profile, existing.phoneNumber, existing.gender);
		}
	}
	
}
