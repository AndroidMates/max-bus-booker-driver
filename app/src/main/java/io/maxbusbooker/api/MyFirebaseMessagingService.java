package io.maxbusbooker.api;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * FirebaseMessagingService for handling incoming messages
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
	@Override
	public void onNewToken(String s) {
		// If you want to send messages to this application instance or
		// manage this apps subscriptions on the server side, send the
		// Instance ID token to your app server.
		sendRegistrationToServer(s);
	}
	
	private void sendRegistrationToServer(String token) {
		//todo: send token
	}
}
