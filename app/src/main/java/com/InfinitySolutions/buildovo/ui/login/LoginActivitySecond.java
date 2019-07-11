package com.InfinitySolutions.buildovo.ui.login;

import android.app.Activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.InfinitySolutions.buildovo.HomePage;
import com.InfinitySolutions.buildovo.R;
import com.InfinitySolutions.buildovo.ui.login.LoginViewModel;
import com.InfinitySolutions.buildovo.ui.login.LoginViewModelFactory;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivitySecond extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    SignInButton signInButton;
    private Context loginContext;
    GoogleSignInClient mgoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginContext=this;


        {
            signInButton = findViewById(R.id.google_signin);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            mgoogleSignInClient = GoogleSignIn.getClient(this, gso);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    google_signIn();
                }
            });
        }//google signin


        final EditText usernameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        final EditText numberEditText = (EditText) findViewById(R.id.mobNumber);
        final TextInputLayout userInputLayout=(TextInputLayout)findViewById(R.id.usernameLayout);
        final TextInputLayout passinputayout=(TextInputLayout)findViewById(R.id.passwordLayout);
        final TextInputLayout numberinputayout=(TextInputLayout)findViewById(R.id.mobNumberLayout);
        final TextView alternate_numTopass=(TextView) findViewById(R.id.login_using_number_text);
        final Button loginButton = (Button) findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        // Client Id           13039847697-1t3sgb100im8m3d3hcnae0sbis4hulg4.apps.googleusercontent.com
        // Client Secret       O5hRz7G9hX9GDZTMviREMurv

        {
            alternate_numTopass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(numberinputayout.getVisibility()==View.GONE){
                        userInputLayout.setVisibility(View.INVISIBLE);
                        passinputayout.setVisibility(View.INVISIBLE);
                        numberinputayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        userInputLayout.setVisibility(View.VISIBLE);
                        passinputayout.setVisibility(View.VISIBLE);
                        numberinputayout.setVisibility(View.GONE);
                    }

                }
            });
        }//on alternate number and login


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                if (numberEditText.getVisibility() == View.GONE) {
                    loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    String userEmail= usernameEditText.getText().toString().trim().toLowerCase();
                    String password=passwordEditText.getText().toString();
                    JSONObject user_cred= new JSONObject();
                    try {
                        user_cred.put("email",userEmail);
                        user_cred.put("password",password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    {
                        System.out.println("WORK................");
                        RequestQueue queue = Volley.newRequestQueue(loginContext);

                        String url = "https://bldvtest.herokuapp.com/api/user/login/";

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, user_cred, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("Response Got",response.toString());
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        System.out.println(error.networkResponse.toString());

                                    }
                                });

                        queue.add(jsonObjectRequest);



                    } //request post json
                } else {


                }


            }
        });
    }

    public void homeActivity(View view) {
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }

    private void google_signIn() {

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
