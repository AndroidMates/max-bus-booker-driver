package io.maxbusbooker.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.maxbusbooker.fragments.BaseFragment;
import io.maxbusbooker.util.AppUtils;

/**
 * Base class for all {@linkplain AppCompatActivity}s
 * Used to provide a simpler implementation of certain core functionality
 */
public abstract class BaseActivity extends AppCompatActivity {
	private MaterialDialog dialog;
	protected FirebaseAuth auth;
	protected FirebaseFirestore firestore;
	protected StorageReference storage;
	private FragmentManager manager;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		onViewReady(savedInstanceState, getIntent());
		
		//Init Firebase
		auth = FirebaseAuth.getInstance();
		firestore = FirebaseFirestore.getInstance();
		storage = FirebaseStorage.getInstance().getReference(AppUtils.DEFAULT_PATH);
		
		//Disable offline persistence in order to retrieve data in real-time at always
		firestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
				                               .setPersistenceEnabled(false)
				                               .build());
		
		//Get support fragment manager
		manager = getSupportFragmentManager();
	}
	
	/**
	 * Child activities must override this with their layout reference
	 *
	 * @return layout reference for the activity
	 */
	@LayoutRes
	protected abstract int getContentView();
	
	/**
	 * Must be called by child activities instead of {@linkplain #onCreate} and cannot delete the super class declaration
	 */
	protected abstract void onViewReady(Bundle savedInstanceState, Intent intent);
	
	protected void showDialog(boolean cancelable, String dialogMessage) {
		if (dialog == null) {
			dialog = new MaterialDialog.Builder(BaseActivity.this)
					         .theme(Theme.LIGHT)
					         .progress(true, 100)
					         .cancelable(cancelable)
					         .content(dialogMessage)
					         .build();
		}
		
		dialog.show();
	}
	
	protected void dismissDialog() {
		if (dialog != null && dialog.isShowing()) dialog.dismiss();
	}
	
	/**
	 * Creates an {@linkplain Intent} to the target class
	 */
	protected void intentTo(Class<? extends Activity> targetClass) {
		Intent intent = new Intent(this, targetClass);
		if (targetClass == LoginActivity.class || targetClass == HomeActivity.class) {
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		startActivity(intent);
	}
	
	/**
	 * Creates an {@linkplain Intent} to the target class
	 */
	protected void intentTo(Class<? extends Activity> targetClass, @NonNull Bundle bundle) {
		Intent intent = new Intent(this, targetClass);
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	/**
	 * Navigate to a web url via {@linkplain Intent}
	 */
	protected void intentToExternal(String url, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(Intent.createChooser(intent, "Open link with..."), requestCode);
	}
	
	/**
	 * Navigate to a {@linkplain BaseFragment}
	 */
	protected void navigateToFragment(BaseFragment fragment, @IdRes int layoutId) {
		manager.beginTransaction().replace(layoutId, fragment)
				//.addToBackStack(null)
				.commit();
	}
	
	/**
	 * Navigate to a {@linkplain BaseFragment} and add to backstack
	 */
	protected void navigateToFragment(BaseFragment fragment, @IdRes int layoutId, @Nullable String backStack) {
		manager.beginTransaction().replace(layoutId, fragment)
				.addToBackStack(backStack)
				.commit();
	}
	
	/**
	 * @return {@link FirebaseFirestore} instance
	 */
	public FirebaseFirestore getFirestore() {
		return firestore;
	}
	
	/**
	 * @return {@link FirebaseAuth} instance
	 */
	public FirebaseAuth getAuth() {
		return auth;
	}
}
