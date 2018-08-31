package io.maxbusbooker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import io.maxbusbooker.R;
import io.maxbusbooker.data.Passenger;
import io.maxbusbooker.util.AppUtils;
import io.maxbusbooker.util.UserPrefs;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        UserPrefs prefs = UserPrefs.get(this);
        //todo: remove this user
        prefs.setLoggedInUser(new Passenger(
                String.valueOf(System.currentTimeMillis()), getString(R.string.dummy_username),
                getString(R.string.quabynah_profile_url),
                "0554022344", AppUtils.GENDER_MALE
        ));

        //Show screen for 0.8 secs and navigate to the appropriate screen after user login state
        // is verified
        new Handler().postDelayed(() -> {
            if (prefs.isLoggedIn()) {
                intentTo(HomeActivity.class);
            } else {
                intentTo(LoginActivity.class);
            }
        }, 1850);

    }
}
