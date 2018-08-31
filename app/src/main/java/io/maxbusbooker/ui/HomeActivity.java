package io.maxbusbooker.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

import io.maxbusbooker.R;
import io.maxbusbooker.widget.BaselineGridTextView;

public class HomeActivity extends AppCompatActivity {

    //objects of the drawer class
    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private BaselineGridTextView _setTime;
    private ImageButton _imageButton;
    private ConstraintLayout mLayout;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // instantiation of the navigation drawer classes
        mDrawer = findViewById(R.id.drawer);
        mNavView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,mDrawer,R.string.open_drawer,R.string.close_drawer);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

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
                        _setTime.setText(hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,false);
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
                Snackbar.make(mLayout,e.getStackTrace().toString(),Snackbar.LENGTH_LONG).show();
            }
            return;
        }
    }

}
