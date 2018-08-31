package io.maxbusbooker.api;

import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

/**
 * Handles user account creation callbacks
 */
public interface AuthenticationListener {
	
	void onAuthSuccess(@Nullable FirebaseUser user, boolean isNewUser);
	
	void onAuthFailure(String reason);
	
	void onAuthAborted(String reason);
	
}
