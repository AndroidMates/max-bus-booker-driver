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
 * Created by Dennis Bilson on Wed at 7:00 PM.
 * Package name : io.travelaid.data
 * <p>
 * Driver data model
 */
public class Driver extends GlobalUser {
	
	private String uid;
	private String username;
	private String profile;
	private String phoneNumber;
	//Default gender is Male
	private String gender = AppUtils.GENDER_MALE;
	private String token;
	private Bus bus;
	private boolean online = true;
	
	public Driver() {
	}
	
	public Driver(String uid, String username, String profile, String phoneNumber, String gender, Bus bus, boolean online) {
		this.uid = uid;
		this.username = username;
		this.profile = profile;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.token = FirebaseInstanceId.getInstance().getToken();
		this.bus = bus;
		this.online = online;
	}
	
	protected Driver(Parcel in) {
		uid = in.readString();
		username = in.readString();
		profile = in.readString();
		phoneNumber = in.readString();
		gender = in.readString();
		token = in.readString();
		bus = in.readParcelable(Bus.class.getClassLoader());
		online = in.readByte() != 0;
	}
	
	public static Map<String, Object> toHashMap(Driver user) {
		Map<String, Object> hashMap = new HashMap<>(0);
		hashMap.put("uid", user.uid);
		hashMap.put("username", user.username);
		hashMap.put("profile", user.profile);
		hashMap.put("phoneNumber", user.phoneNumber);
		hashMap.put("gender", user.gender);
		hashMap.put("token", user.token);
		hashMap.put("online", user.online);
		hashMap.put("bus", Bus.toHashMap(user.bus));
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
		dest.writeParcelable(bus, flags);
		dest.writeByte((byte) (online ? 1 : 0));
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<Driver> CREATOR = new Parcelable.Creator<Driver>() {
		@Override
		public Driver createFromParcel(Parcel in) {
			return new Driver(in);
		}
		
		@Override
		public Driver[] newArray(int size) {
			return new Driver[size];
		}
	};
	
	public String getToken() {
		return token;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
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
	
	public Bus getBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public static class Builder {
		private String uid;
		private String username;
		private String profile;
		private String phoneNumber;
		//Default gender is Male
		private String gender = AppUtils.GENDER_MALE;
		private String token;
		private Bus bus;
		private boolean online = true;
		
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
		
		public Builder setBus(Bus bus) {
			this.bus = bus;
			return this;
		}
		
		public Builder setOnline(boolean online) {
			this.online = online;
			return this;
		}
		
		public Driver build() {
			return new Driver(uid, username, profile, phoneNumber, gender, bus, online);
		}
		
		public Driver from(Driver existing) {
			return new Driver(existing.uid, existing.username, existing.profile, existing.phoneNumber,
					existing.gender, existing.bus, existing.online);
		}
	}
}
