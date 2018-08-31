package io.maxbusbooker.api;

import android.support.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;

public interface DocumentAccessListener {
	
	void onSuccess(@Nullable DocumentSnapshot snapshot);
	
	void onFailure(String reason);
}
