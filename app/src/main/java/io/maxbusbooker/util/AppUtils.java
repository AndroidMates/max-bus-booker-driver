package io.maxbusbooker.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import io.maxbusbooker.R;
import io.maxbusbooker.api.AuthenticationListener;
import io.maxbusbooker.api.DatabaseUpdateListener;
import io.maxbusbooker.api.DocumentAccessListener;
import io.maxbusbooker.api.GlobalUser;
import io.maxbusbooker.data.Driver;
import io.maxbusbooker.data.Passenger;
import io.maxbusbooker.ui.BaseActivity;
import timber.log.Timber;

/**
 * Project : max-travel-aid
 * Created by Dennis Bilson on Wed at 7:13 PM.
 * Package name : io.travelaid.util
 * <p>
 * Stores constants and static functions
 */
public class AppUtils {
	//Database references
	public static final String TICKET_REF = "tickets";
	public static final String PASSENGER_REF = "passengers";
	public static final String DRIVER_REF = "drivers";
	public static final String RECENTS_REF = "recents";
	public static final String IMAGE_REF = "images";
	
	public static final String DEFAULT_PATH = "bus-booker";
	
	//Bus Types
	public static final String BUS_TYPE_STC = "STC";
	public static final String BUS_TYPE_VIP = "VIP";
	
	//Gender Types
	public static final String GENDER_MALE = "MALE";
	public static final String GENDER_FEMALE = "FEMALE";
	
	//User Types
	public static final String TYPE_PASSENGER = "PASSENGER";
	public static final String TYPE_DRIVER = "DRIVER";
	
	//Get current user
	/*package*/
	static UserInfo getCurrentUser() {
		return FirebaseAuth.getInstance().getCurrentUser();
	}
	
	@Nullable
	public static String getUserUID() {
		return getCurrentUser().getUid();
	}
	
	/**
	 * Provides a call back for user document snapshots stored in Cloud Firestore
	 *
	 * @param host      The host activity
	 * @param reference An even number of paths, separated by a forward slash, to a database reference
	 * @param listener  A callback listener after the document has been retrieved or otherwise
	 */
	public static void getCurrentUser(@NonNull BaseActivity host, @NonNull String reference,
	                                  DocumentAccessListener listener) {
		host.getFirestore().document(reference)
				.get()
				.addOnCompleteListener(host, task -> {
					if (task.isSuccessful()) {
						listener.onSuccess(task.getResult().exists() ? task.getResult() : null);
					}
				}).addOnFailureListener(host, e -> listener.onFailure(e.getLocalizedMessage())).addOnFailureListener(host, e -> listener.onFailure(e.getMessage()));
	}
	
	/**
	 * Store or update user POJO class in database
	 */
	/*public static void storeOrUpdate(@NonNull BaseActivity host, @NonNull GlobalUser user,
	                                 DatabaseUpdateListener listener) {
		//Get user UID
		String uid = (user instanceof Driver) ? ((Driver) user).getUid() : ((Passenger) user).getUid();
		
		//Store user data
		DocumentReference document = host.getFirestore().collection((user instanceof Driver) ? DRIVER_REF : PASSENGER_REF)
				                             .document(uid);
		document.get().addOnCompleteListener(host, new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()) {
					//User document already exists
					if (task.getResult().exists()) {
						document.update((user instanceof Driver) ? Driver.toHashMap((Driver) user) : Passenger.toHashMap((Passenger) user))
								.addOnCompleteListener(task1 -> listener.onInstanceSuccess())
								.addOnFailureListener(host, e -> listener.onInstanceError(e.getLocalizedMessage()));
					} else {
						document.set((user instanceof Driver) ? (Driver) user : (Passenger) user)
								.addOnCompleteListener(host, _task -> {
									if (_task.isSuccessful()) {
										listener.onInstanceSuccess();
									}
								})
								.addOnFailureListener(host, e -> listener.onInstanceError(e.getLocalizedMessage()));
					}
				} else {
					listener.onInstanceError(task.getException().getLocalizedMessage());
				}
			}
		})
				.addOnFailureListener(host, e -> listener.onInstanceError(e.getMessage()));
		
	}
	*/
	
	public static void signInOrCreateUser(@NonNull BaseActivity host, @NonNull String username, @NonNull String password, AuthenticationListener listener) {
		//get auth instance
		FirebaseAuth auth = host.getAuth();
		
		//Sign in user
		auth.signInWithEmailAndPassword(username, password)
				.addOnCompleteListener(host, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							listener.onAuthSuccess(auth.getCurrentUser(), false);
						} else {
							
							//User does not exist so create a new one
							auth.createUserWithEmailAndPassword(username, password)
									.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
										@Override
										public void onComplete(@NonNull Task<AuthResult> task) {
											if (task.isSuccessful())
												if (isTextNullOrEmpty(auth.getUid())) {
													listener.onAuthAborted("User UID cannot be obtained");
												} else {
													listener.onAuthSuccess(auth.getCurrentUser(), true);
												}
											else
												listener.onAuthAborted(task.getException().getLocalizedMessage());
										}
									}).addOnFailureListener(e -> listener.onAuthFailure(e.getLocalizedMessage()));
						}
					}
				})
				.addOnFailureListener(host, e -> listener.onAuthFailure(e.getLocalizedMessage()));
		
	}
	
	//Show log messages during debugging
	public static void showLog(Activity host, Object logMessage) {
		Timber.d(host.getClass().getCanonicalName(), " Log message: %s", logMessage);
	}
	
	/**
	 * Shows a toast message to the screen
	 */
	public static void showToast(Context context, @Nullable Object message) {
		Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * A @Link{MaterialDialog} that shows an edit text view and returns a callback when the user clicks the ok button
	 *
	 * @param context context of the application
	 * @param title   title for the dialog
	 * @return content of the EditTextView
	 */
	public static void showEditContentDialog(@NonNull Context context, @NonNull String title, @NonNull DialogCallback dialogCallback) {
		EditText view = (EditText) LayoutInflater.from(context).inflate(R.layout.edit_content_dialog, null, false);
		MaterialDialog.SingleButtonCallback callback = (dialog, which) -> {
			if (which == DialogAction.POSITIVE) {
				dialogCallback.onMessageRetrieved(view.getText().toString());
			} else {
				dialog.dismiss();
			}
		};
		new MaterialDialog.Builder(context)
				.customView(view, true)
				.title(title)
				.theme(Theme.LIGHT)
				.cancelable(true)
				.positiveText("Done")
				.negativeText("Dismiss")
				.onNegative(callback)
				.onPositive(callback)
				.build().show();
	}
	
	/**
	 * Checks whether or not a given string value is null or empty
	 *
	 * @param content tring value
	 * @return state of content
	 */
	public static boolean isTextNullOrEmpty(String content) {
		return content == null || content.length() == 0;
	}
	
	//Validates an email field
	public static boolean isValidEmail(String email) {
		return !isTextNullOrEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	//Validates a password field
	public static boolean isValidPassword(String password) {
		return !isTextNullOrEmpty(password) && password.length() >= 4;
	}
	
	/**
	 * Custom interface for retrieving the content of a message box
	 */
	public interface DialogCallback {
		
		void onMessageRetrieved(String message);
		
	}
	
	
}
