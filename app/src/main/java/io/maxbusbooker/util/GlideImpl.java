package io.maxbusbooker.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.bumptech.glide.load.DecodeFormat.PREFER_RGB_565;

/**
 * Implementation of the {@linkplain AppGlideModule} to expose certain functionality
 */
@GlideModule
public class GlideImpl extends AppGlideModule {
	
	@Override
	public boolean isManifestParsingEnabled() {
		return false;
	}
	
	@SuppressLint("CheckResult")
	@Override
	public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		final RequestOptions defaultOptions = new RequestOptions();
		// Prefer higher quality images unless we're on a low RAM device
		ActivityManager activityManager =
				(ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		defaultOptions.format((activityManager != null && activityManager.isLowRamDevice()) ? PREFER_RGB_565 : PREFER_ARGB_8888);
		// Disable hardware bitmaps as they don't play nicely with Palette
		defaultOptions.disallowHardwareConfig();
		builder.setDefaultRequestOptions(defaultOptions);
	}
}
