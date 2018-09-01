package io.maxbusbooker.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.maxbusbooker.R;
import io.maxbusbooker.api.GlobalUser;
import io.maxbusbooker.api.UserLoginListener;
import io.maxbusbooker.data.Bus;
import io.maxbusbooker.data.Passenger;
import io.maxbusbooker.data.Seat;
import io.maxbusbooker.fragments.ProfileFragment;
import io.maxbusbooker.util.AppUtils;
import io.maxbusbooker.util.GlideApp;
import io.maxbusbooker.util.UserPrefs;
import io.maxbusbooker.widget.BaselineGridTextView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, UserLoginListener {

    //User extra
    public static final String EXTRA_USER = "EXTRA_USER";

    //Prefs
    private UserPrefs prefs;

    //objects of the drawer class
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mToggle;

    private BaselineGridTextView _setTime;
    private ImageButton _imageButton;
    private ConstraintLayout mLayout;
    private GlobalUser globalUser;

    private static final int REQUEST_CODE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {

        // instantiation of the navigation drawer classes
        mDrawerLayout = findViewById(R.id.drawer);
        mNavView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open_drawer,R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        if (globalUser != null) {
            setupHeaderView(mNavView.getHeaderView(0));
        }

        //mNavView.setNavigationItemSelectedListener(this);
        _setTime = findViewById(R.id.dpt_time_text);
        _imageButton = findViewById(R.id.choose_image);
        mLayout = findViewById(R.id.container);

    }

    //method to set time
    public void selectTime(View View) {

        // Get Current Time
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        //sets the current hour and minutes to the textView
                        _setTime.setText(hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,true);
        // display the TimePickerDialog
        timePickerDialog.show();

    }

    // method to select image from gallery
    public void selectImage(View view){
        // creates an intent to pick image from gallery
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("images/*");
        startActivityForResult(pickIntent,REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            try {
                Uri uri = data.getData();
                _imageButton.setImageURI(uri);
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                String path = saveImage(bitmap);
                Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                _imageButton.setImageBitmap(bitmap);*/
            }
            catch (Exception e){
                // displays a snackbar with error message
                Snackbar.make(mLayout,e.getStackTrace().toString(), Snackbar.LENGTH_LONG).show();
            }
            return;
        }
    }

    private void setupHeaderView(View headerView) {
        ImageView avatar = headerView.findViewById(R.id.header_avatar);
        TextView username = headerView.findViewById(R.id.header_username);
        TextView email = headerView.findViewById(R.id.header_email);

        //Load image for user
        GlideApp.with(this)
                .asBitmap()
                .load(globalUser.profile)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .fallback(R.drawable.avatar_placeholder)
                .circleCrop()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(avatar);

        if (AppUtils.isTextNullOrEmpty(globalUser.username) || AppUtils.isTextNullOrEmpty(globalUser.phoneNumber)) {
            email.setVisibility(View.GONE);
            username.setText(R.string.profile_update_prompt);
        } else {
            username.setText(globalUser.username);
            email.setText(globalUser.phoneNumber);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_driver,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_about:
                //starts the about us activity
                intentTo(AboutUsActivity.class);
                break;
            case R.id.menu_suggestion:
                intentTo(SuggestionActivity.class);
                break;
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    //todo: set profile navigation here
    public void viewProfile(View view) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            //ids for the items in the navigation drawer
            case R.id.profile:
                navigateToFragment(new ProfileFragment(), R.id.frame_home);
                break;
            case R.id.seat:
                navigateToFragment(new ProfileFragment(), R.id.frame_home);
                break;
            case R.id.toggle:
                /*Moved to settings*/
                break;
            case R.id.logout:
                auth.signOut(); //Sign out of firebase
                prefs.logout(); //Sign out of application
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onUserLogin() {
        /*do nothing here*/
    }

    @Override
    public void onUserLogout() {
        intentTo(LoginActivity.class);
    }

    @Override
    protected void onDestroy() {
        prefs.removeUserLoginListener(this);
        super.onDestroy();
    }

    public void login(View view) {

    }

    public void register(View view) {

    }
}
