package io.maxbusbooker.ui;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Calendar;

import io.maxbusbooker.R;
import io.maxbusbooker.widget.BaselineGridTextView;

public class SearchActivity extends AppCompatActivity {

    private BaselineGridTextView display_date;

    private RelativeLayout relativeLayout;

    private EditText _origin;
    private EditText _destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        display_date = findViewById(R.id.display_date);

        relativeLayout = findViewById(R.id.relativeLayout);

        _origin = findViewById(R.id.origin);
        _destination = findViewById(R.id.destination);
    }

    public void pickDate(View view) {

        //To show current date in the datePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //text view to display chosen date
                display_date.setText(day + "/" + (month + 1) + "/" + year);
            }
        },year,month,dayOfMonth);

        //prevents the user from choosing past dates
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //display the datePicker dialog
        datePickerDialog.show();
    }

    public void search(View view){
        // getting text or input from view
        String origin = _origin.getText().toString().trim();
        String destination = _destination.getText().toString().trim();

        // error messages
        String error_msg_origin = "Your origin is required to proceed";
        String error_msg_destination = "Your destination is required to proceed";

        if(origin.isEmpty()){
            _origin.setError(error_msg_origin);
            return;
        }
        else if(destination.isEmpty()){
            _destination.setError(error_msg_destination);
            return;
        }
        else{
            // call to the searchBus method
            searchBus();
        }
    }

    // method to search for  available buses
    public void searchBus(){
        // display a Snackbar
        Snackbar.make(relativeLayout,"Not Implemented",Snackbar.LENGTH_LONG).show();
        // do nothing
    }


}
