package io.maxbusbooker.api;

import android.app.Application;

import timber.log.Timber;

/**
 * Application class
 */
public class MaxBusApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//Plant a timber for debugging
		Timber.plant(new Timber.DebugTree());
	}
	
	
}
