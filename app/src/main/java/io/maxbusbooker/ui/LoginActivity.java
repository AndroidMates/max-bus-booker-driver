package io.maxbusbooker.ui;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.maxbusbooker.R;

public class LoginActivity extends AppCompatActivity {

    // Global or class variables
    private EditText editText_email;
    private EditText editText_password;

    private ConstraintLayout container;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = findViewById(R.id.email);
        editText_password = findViewById(R.id.password);

        container = findViewById(R.id.container);

        progressBar = findViewById(R.id.progressBar);

        // getting an instance of the FirebaseAuth class
        mAuth = FirebaseAuth.getInstance();

    }

    public void register(View view) {

        // getting text from user
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        /**
         * Input validation
         */
        if(email.isEmpty()){
            editText_email.setError(getString(R.string.error_empty_email));
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email.setError(getString(R.string.error_empty_email));
            return;
        }
        else if(password.isEmpty()){
            editText_password.setError(getString(R.string.error_empty_password));
            editText_password.requestFocus();
            return;
        }
        else if(password.length() < 6 ){
            editText_password.setError(getString(R.string.error_password_length));
            editText_password.requestFocus();
            return;
        }
        else {
            // Method call
            registerUser();
        }

    }

    // Method to register users
    public void registerUser(){

        // makes the progressBar visible
        progressBar.setVisibility(View.VISIBLE);

        // getting text from user
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // method call
                            sendVerificationEmail();

                            // display a success message
                            Snackbar.make(container,getString(R.string.text_sign_up_and_verification_sent), Toast.LENGTH_LONG);

                            // clears fields after a successful sign up
                            clearFields();

                        }
                        else {
                            // display an error message
                            Snackbar.make(container,task.getException().getMessage(), Toast.LENGTH_LONG);
                        }

                        // dismiss the progressBar
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    // Method to send email verification
    private void sendVerificationEmail(){

        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // signs out user
                    mAuth.signOut();
                }
                else{
                    // display if error occurs
                    Snackbar.make(container,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    // clears the email and password field
    private void clearFields(){
        editText_email.setText(null);
        editText_password.setText(null);
    }

    public void login(View view) {

        // getting text from user
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        /**
         * Input validation
         */
        if(email.isEmpty()){
            editText_email.setError(getString(R.string.error_empty_email));
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email.setError(getString(R.string.error_empty_email));
            return;
        }
        else if(password.isEmpty()){
            editText_password.setError(getString(R.string.error_empty_password));
            editText_password.requestFocus();
            return;
        }
        else if(password.length() < 6 ){
            editText_password.setError(getString(R.string.error_password_length));
            editText_password.requestFocus();
            return;
        }
        else {
            // Method call
            loginUser();
        }

    }

    // Method to login user
    public void loginUser(){

        progressBar.setVisibility(View.VISIBLE);

        // getting text from user
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // method call
                            checkIfEmailIsVerified();

                        }
                        else{

                            // display error message
                            Snackbar.make(container,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();

                        }

                        // dismiss the progressBar
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    public void checkIfEmailIsVerified(){

        FirebaseUser user = mAuth.getCurrentUser();

        boolean isEmailVerified = user.isEmailVerified();

        if(isEmailVerified){

            // display a toast message
            Toast.makeText(LoginActivity.this,getString(R.string.login_successful),Toast.LENGTH_SHORT).show();

            // clear the text fields
            clearFields();

            // starts the home activity
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));

            // finishes the current activity
            finish();

        }
        else{

            // display error message and restarts the activity
            Snackbar.make(container,getString(R.string.text_email_not_verified),Toast.LENGTH_LONG).show();

            // restarts the activity
            startActivity(new Intent(LoginActivity.this,LoginActivity.class));

            // finishes the current activity
            finish();

        }

    }
}
