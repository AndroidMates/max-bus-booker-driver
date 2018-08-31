package io.maxbusbooker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import io.maxbusbooker.api.GlobalUser;
import io.maxbusbooker.api.UserLoginListener;
import io.maxbusbooker.data.Driver;
import io.maxbusbooker.data.Passenger;
import io.maxbusbooker.data.Driver;

/**
 * Base class for storing {@link GlobalUser} data locally
 */
public class UserPrefs {
	//Keys
	private static final String KEY_USERNAME = "KEY_USERNAME";
	private static final String KEY_UID = "KEU_UID";
	private static final String KEY_PROFILE = "KEY_PROFILE";
	private static final String KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER";
	private static final String KEY_GENDER = "KEY_GENDER";
	private static final String KEY_TOKEN = "KEY_TOKEN";
	private static final String KEY_ONLINE = "KEY_ONLINE";
	
	//Listener
	private List<UserLoginListener> listeners;
	
	//Prefs instance
	private static volatile UserPrefs singleton;
	
	//Shared preferences
	private final SharedPreferences prefs;
	
	//fields
	private String uid;
	private String username;
	private String profile;
	private String phoneNumber;
	//Default gender is Male
	private String gender;
	private String token;
	private boolean isOnline = false;
	private boolean isLoggedIn = false;
	private GlobalUser currentUser;
	
	public static UserPrefs get(Context context) {
		if (singleton == null) {
			synchronized (UserPrefs.class) {
				singleton = new UserPrefs(context);
			}
		}
		return singleton;
	}
	
	private UserPrefs(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		uid = getString(KEY_UID, null);
		username = getString(KEY_USERNAME, null);
		profile = getString(KEY_UID, null);
		phoneNumber = getString(KEY_UID, null);
		gender = getString(KEY_UID, AppUtils.GENDER_MALE);
		token = getString(KEY_UID, null);
		isOnline = getBoolean(KEY_ONLINE, false);
		
		isLoggedIn = !AppUtils.isTextNullOrEmpty(uid);
		
		if (isLoggedIn) {
			uid = getString(KEY_UID, null);
			username = getString(KEY_USERNAME, null);
			profile = getString(KEY_UID, null);
			phoneNumber = getString(KEY_UID, null);
			gender = getString(KEY_UID, AppUtils.GENDER_MALE);
			token = getString(KEY_UID, null);
			isOnline = getBoolean(KEY_ONLINE, false);
		}
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return prefs.getBoolean(key, defaultValue);
	}
	
	private String getString(String key, String defaultValue) {
		return prefs.getString(key, defaultValue);
	}
	
	private void putBoolean(String key, boolean value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}
	
	private void putString(String key, String value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.apply();
	}
	
	private void remove(String key) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(key);
		editor.apply();
	}
	
	public void addUserLoginListener(UserLoginListener listener) {
		//Create new instance if null
		if (listeners == null) listeners = new ArrayList<>(0);
		listeners.add(listener);
	}
	
	public void removeUserLoginListener(UserLoginListener listener) {
		//Remove all active listeners
		if (listeners != null && !listeners.isEmpty()) listeners.remove(listener);
	}
	
	/**
	 * Calls the {@linkplain UserLoginListener#onUserLogin} after user is logged in to notify all listeners
	 */
	private void dispatchLoginEvent() {
		if (listeners != null && !listeners.isEmpty()) {
			for (UserLoginListener l : listeners) {
				l.onUserLogin();
			}
		}
	}
	
	/**
	 * Calls the {@linkplain UserLoginListener#onUserLogout()} after user is logged in to notify all listeners
	 */
	private void dispatchLogoutEvent() {
		if (listeners != null && !listeners.isEmpty()) {
			for (UserLoginListener l : listeners) {
				l.onUserLogout();
			}
		}
	}
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	public GlobalUser getUser() {
		GlobalUser newUser;
		if (currentUser instanceof Driver) {
			newUser = new Driver.Builder()
					          .setBus(null)
					          .setGender(gender)
					          .setOnline(isLoggedIn)
					          .setPhoneNumber(phoneNumber)
					          .setProfile(profile)
					          .setToken(token)
					          .setUid(uid)
					          .setUsername(username)
					          .build();
		} else {
			
			newUser = new Passenger.Builder()
					          .setGender(gender)
					          .setPhoneNumber(phoneNumber)
					          .setProfile(profile)
					          .setToken(token)
					          .setUid(uid)
					          .setUsername(username)
					          .build();
		}
		
		return newUser;
	}
	
	public boolean isOnline() {
		return getBoolean(KEY_ONLINE, false);
	}
	
	public void setOnline(boolean online) {
		isOnline = online;
		putBoolean(KEY_ONLINE, isOnline);
	}
	
	public void setLoggedInUser(GlobalUser user) {
		//Get the type of user
		if (user instanceof Driver) {
			isLoggedIn = true;
			putString(KEY_USERNAME, ((Driver) user).getUsername());
			putString(KEY_UID, ((Driver) user).getUid());
			putString(KEY_PROFILE, ((Driver) user).getProfile());
			putString(KEY_PHONE_NUMBER, ((Driver) user).getPhoneNumber());
			putString(KEY_GENDER, ((Driver) user).getGender());
			putString(KEY_TOKEN, ((Driver) user).getToken());
			
			//Create new user instance
			currentUser = new Driver.Builder().from((Driver) user);
			//Alert listeners of any changes
			dispatchLoginEvent();
			
		} else if (user instanceof Passenger) {
			isLoggedIn = true;
			putString(KEY_USERNAME, ((Passenger) user).getUsername());
			putString(KEY_UID, ((Passenger) user).getUid());
			putString(KEY_PROFILE, ((Passenger) user).getProfile());
			putString(KEY_PHONE_NUMBER, ((Passenger) user).getPhoneNumber());
			putString(KEY_GENDER, ((Passenger) user).getGender());
			putString(KEY_TOKEN, ((Passenger) user).getToken());
			
			//Create new user instance from the existing one
			currentUser = new Passenger.Builder().from((Passenger) user);
			//Alert listeners of any changes
			dispatchLoginEvent();
			
		}  /*do nothing*/
	}
	
	public void logout() {
		isLoggedIn = false;
		remove(KEY_USERNAME);
		remove(KEY_UID);
		remove(KEY_PROFILE);
		remove(KEY_PHONE_NUMBER);
		remove(KEY_GENDER);
		remove(KEY_TOKEN);
		dispatchLogoutEvent();
	}
	
}
