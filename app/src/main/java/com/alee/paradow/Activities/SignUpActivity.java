package com.alee.paradow.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alee.paradow.R;
import com.alee.paradow.databinding.ActivitySignUpBinding;
import com.alee.paradow.model.SignupRes;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";


    ActivitySignUpBinding binding;
    Animation anim;

    String name,email,password,Cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move); // Create the animation.

        binding.signFrm.startAnimation(anim);
        binding.btnSign.setOnClickListener(v -> signup());

        binding.linkLogin.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            finish();
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        hideSoftKeyboard();

        binding.btnSign.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        //not needed it initialized in validation method
        // name = _userNameText.getText().toString();
        //        email = _emailText.getText().toString();
        //        password = _passwordText.getText().toString();
        //        Cpassword = _conpasswordText.getText().toString();

        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<SignupRes> call = service.signUp(name, email, password);
        call.enqueue(new Callback<SignupRes>() {
            @Override
            public void onResponse(Call<SignupRes> call, Response<SignupRes> response) {

                progressDialog.dismiss();

                if(response.code()==400) {

                    binding.btnSign.setEnabled(true);
                    Log.i(TAG,"Response: Email Duplicated");

//                    Toast.makeText(getBaseContext(), "this email already used"+response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    Gson gson = new GsonBuilder().create();
                    SignupRes mError=new SignupRes();
                    try {
                        mError= gson.fromJson(response.errorBody().string(),SignupRes .class);
                        if(mError.getMessage().getRememberToken().contains("Duplicated"))
                        Toast.makeText(getApplicationContext(), "this email already used", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }
                else if(response.code() == 200 & response.body()!=null){

                    onSignupSuccess();
                    Log.i("Response:", "Sign UP Done" + response.body().getMessage().getRememberToken());

                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(Call<SignupRes> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("Failure:", t.getMessage());
                onSignupFailed();
            }
        });


    }

    public void onSignupSuccess() {
        binding.btnSign.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        binding.btnSign.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name = binding.inputName.getText().toString();
        email = binding.inputEmail.getText().toString();
        password = binding.inputPass.getText().toString();
        Cpassword = binding.inputCpass.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            binding.inputName.setError("at least 3 characters");
            valid = false;
        } else {
            binding.inputName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputEmail.setError("enter a valid email address");
            valid = false;
        } else {
            binding.inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 7 ) {
            binding.inputPass.setError("Password needs to be more than 7 characters");
            valid = false;
        } else {
            binding.inputPass.setError(null);
        }
        if (!password.equals(Cpassword)){
            binding.inputPass.setError("Password and Confirm Password doesn't match");
        } else {
            binding.inputPass.setError(null);
        }

        return valid;
    }
    public void hideSoftKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}