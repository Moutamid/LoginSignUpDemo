package dev.moutamid.facefilters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivitySignUp extends AppCompatActivity {

    //    private LinearLayout maleBtnLayout, femaleBtnLayout;
    //    EditText userNameEditText;
    //    EditText confirmPasswordEditText;
    //    EditText passwordEditText;
    private EditText userNameEditText, passwordEditText, confirmPasswordEditText;
    //    private ImageView showPasswordBtn, showConfirmPasswordBtn;
    private Button signUpBtn;
//    private TextView goToLoginActivityBtn;

    //    private String userGender = "male";
//    private Boolean passwordShowing = false;
//    private Boolean confirmPasswordShowing = false;
    private ProgressDialog mDialog;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabaseUsers;
//    private Boolean isOnline = false;
//    private SharedPreferences sharedPreferences;

//    String profileImageLink;
//    private int randomNmbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(ActivitySignUp.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        findViewById(R.id.loginBtn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySignUp.this, ActivityLogin.class));
            }
        });

//        findViewById(R.id.).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {


//                Toast.makeText(ActivitySignUp.this, "You are signed in using name: " + name.getText().toString() + " email: " + email.getText().toString() + " password: " + password.getText().toString() + " confirm password: " + confirmpassword.getText().toString() + " and Bla Bla Bla ", Toast.LENGTH_SHORT).show();
//            }
//        });

//        findViewById(R.id.google_btn_btn_activity_sign_up).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ActivitySignUp.this, "You are signed in using Google+ Bla Bla Bla", Toast.LENGTH_SHORT).show();
//            }
//        });

        findViewById(R.id.backbtn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference();
        mDatabaseUsers.keepSynced(true);



//        sharedPreferences = this.

//                getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        mDialog.setMessage("Signing you in...");

        initViews();

//        setListenersToWidgets();
    }


//    private View.OnClickListener goToLoginActivityBtnListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
//            }
//        };
//    }

    private View.OnClickListener signUpBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isOnline) {
                mDialog.show();
                checkStatusOfEditTexts();
//                } else
//                    Toast.makeText(SignUpActivity.this, "You are offline", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void checkStatusOfEditTexts() {

        // Getting strings from edit texts
        final String username = userNameEditText.getText().toString().trim().toLowerCase();
        final String password = passwordEditText.getText().toString().trim();
        final String confirmedPassword = confirmPasswordEditText.getText().toString().trim();
//
//        mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                // Checking if user exist
//                if (dataSnapshot.hasChild(username)) {
//
//                    mDialog.dismiss();
//                    userNameEditText.setError("Username already exist");
//                    userNameEditText.requestFocus();
//
//
//                } else {

        // Checking if Fields are empty or not
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmedPassword)) {

//            if (username.length() >= 3) {

            // Checking Length of password
//                if (password.length() >= 6) {

            // Checking if password is equal to confirmed Password
            if (password.equals(confirmedPassword)) {

                // Signing up user
                signUpUserWithNameAndPassword(username, password);

            } else {

                mDialog.dismiss();
                confirmPasswordEditText.setError("Password does not match!");
                confirmPasswordEditText.requestFocus();

            }

//                } else {
//
//                    mDialog.dismiss();
//                    passwordEditText.setError("Minimum length of password must be 6");
//                    passwordEditText.requestFocus();
//
//                }

//            } else {
//                mDialog.dismiss();
//                userNameEditText.setError("Minimum length of username must be 3");
//                userNameEditText.requestFocus();
//
//            }

            // User Name is Empty
        } else if (TextUtils.isEmpty(username)) {

            mDialog.dismiss();
            userNameEditText.setError("Please provide a username");
            userNameEditText.requestFocus();


            // Password is Empty
        } else if (TextUtils.isEmpty(password)) {

            mDialog.dismiss();
            passwordEditText.setError("Please provide a password");
            passwordEditText.requestFocus();


            // Confirm Password is Empty
        } else if (TextUtils.isEmpty(confirmedPassword)) {

            mDialog.dismiss();
            confirmPasswordEditText.setError("Please confirm your password");
            confirmPasswordEditText.requestFocus();


        }

    }


//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }

//        });


    private void signUpUserWithNameAndPassword(final String username, String password) {

//        if (isOnline) {

        String email = username;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //if Email Address is Invalid..

            mDialog.dismiss();
            userNameEditText.setError("Please enter a valid email with no spaces and special characters included");
            userNameEditText.requestFocus();
        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        mDialog.dismiss();

                        finish();
                        Intent intent = new Intent(ActivitySignUp.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(ActivitySignUp.this, "You are signed up!", Toast.LENGTH_SHORT).show();
//                            addUserDetailsToDatabase(username);

                    } else {

                        mDialog.dismiss();
                        Toast.makeText(ActivitySignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
//        } else {
//
//            mDialog.dismiss();
//            Toast.makeText(this, "You are not online", Toast.LENGTH_SHORT).show();
//        }
    }

    private void initViews() {

//        EditText userNameEditText = findViewById(R.id.user_name_edittext_activity_sign_up);

        userNameEditText = findViewById(R.id.email_edittext_activity_sign_up);
        passwordEditText = findViewById(R.id.password_edittext_activity_sign_up);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext_activity_sign_up);
//        maleBtnLayout = findViewById(R.id.maleBtnLayout);
//        femaleBtnLayout = findViewById(R.id.femaleBtnLayout);
//         = findViewById(R.id.userName_sign_up_editText);
//         = findViewById(R.id.password_sign_up_editText);
//         = findViewById(R.id.confirmPassword_sign_up_editText);
//        showPasswordBtn = findViewById(R.id.showPassword_btn_sign_up);
//        showConfirmPasswordBtn = findViewById(R.id.showConfirmPassword_btn_sign_up);
        signUpBtn = findViewById(R.id.create_btn_activity_sign_up);
        signUpBtn.setOnClickListener(signUpBtnListener());
//        goToLoginActivityBtn = findViewById(R.id.goTo_login_activity_textView);
    }

}