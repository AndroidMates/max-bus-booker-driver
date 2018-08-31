package io.maxbusbooker.api;

import android.support.annotation.Nullable;

/**
 * Firestore document retrieval listener
 */
public interface DatabaseUpdateListener {
	
	void onInstanceSuccess();
	
	void onInstanceError(@Nullable String errorMessage);
}
