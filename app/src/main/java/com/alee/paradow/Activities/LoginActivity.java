package com.alee.paradow.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alee.paradow.R;
import com.alee.paradow.databinding.ActivityLoginBinding;
import com.alee.paradow.model.LoginRes;
import com.alee.paradow.model.UserData;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    ActivityLoginBinding binding;
    Animation anim;

    UserData user = new UserData();
    String token ;
    Long userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);

        token = preferences.getString("token", null);
        userID = preferences.getLong("userID", -1);

        if (token != null){
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
            finish();
        }

//        animateImageView();
//    }
//
//    private void animateImageView() {
//        AnimationSet set = new AnimationSet(true);
//        set.setFillAfter(true);
//
//        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
//        TranslateAnimation moveLefttoRight = new TranslateAnimation(0, 200, 0, 0);
//        moveLefttoRight.setDuration(1000);
//        moveLefttoRight.setStartOffset(1000);
//
//        set.addAnimation(rotation);
//        set.addAnimation(moveLefttoRight);
//
//        image.startAnimation( set );
//    }

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move); // Create the animation.
        binding.loginFrm.startAnimation(anim);

        binding.loginBack.setOnClickListener(v -> hideSoftKeyboard());
        binding.btnLogin.setOnClickListener(v -> login());

        binding.linkSignup.setOnClickListener(v -> {
//                 Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        });

    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        else if (!isNetworkConnected()) {
            Toast.makeText(LoginActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
            return;
        }

        hideSoftKeyboard();

        binding.btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        //TODO:(3) if some edit needed to back press not close the dialog
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();


        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<LoginRes> call = service.login(email , password);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {

                progressDialog.dismiss();

                if (response.code()==400)
                {
                    Log.i("Response:","Incorrect email or password");
                    Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
                    binding.btnLogin.setEnabled(true);
                }
                else if(response.code() == 200 & response.body()!=null){
                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);

                    preferences.edit().putString("token", response.body().getMessage().getRememberToken()).apply();
                    preferences.edit().putLong("userID", response.body().getMessage().getUserData().getId()).apply();

                    Log.i("Response:",response.body().getMessage().getRememberToken());

                    user.setName(response.body().getMessage().getUserData().getName());
                    user.setPhoto(response.body().getMessage().getUserData().getPhoto());
                    user.setFavProduct(response.body().getMessage().getUserData().getFavProduct());
                    user.setId(response.body().getMessage().getUserData().getId());

                    onLoginSuccess();

                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("Failure:", Objects.requireNonNull(t.getMessage()));
                onLoginFailed();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(LoginActivity.this,"Signed Up, You are welcome to login",Toast.LENGTH_SHORT).show();
            }
        }
    }


    long backLastPressed = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backLastPressed < 2000) {
            // TODO: Register double-tapped BACK button, put your "exit" code here
            super.onBackPressed();
            backLastPressed = 0;
            return;
        }
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        backLastPressed = System.currentTimeMillis();
        // Otherwise, ignore this BACK press
    }

    public void onLoginSuccess() {

        binding.btnLogin.setEnabled(true);

        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("user", user);

        startActivity(intent);
        finish();
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed, please check your Data", Toast.LENGTH_LONG).show();
        binding.btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (email.isEmpty()) {
            binding.inputEmail.setError("enter a valid userName address");
            valid = false;
        } else {
            binding.inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 7) {
            binding.inputPassword.setError("Password needs to be more than 7 characters");
            valid = false;
        } else {
            binding.inputPassword.setError(null);
        }

        return valid;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() ;
    }

    public void hideSoftKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
//    private boolean isOnTheInternet() {
//        try {
//            URLConnection urlConnection = new URL("https://www.google.com/").openConnection();
//            urlConnection.setConnectTimeout(400);
//            urlConnection.connect();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//    public boolean isInternetAvailable() {
//        try {
//            InetAddress ipAddr = InetAddress.getByName("google.com");
//            //You can replace it with your name
//            return true;
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
