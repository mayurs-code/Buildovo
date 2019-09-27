package com.InfinitySolutions.buildovo.ui.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.InfinitySolutions.buildovo.HomePage;
import com.InfinitySolutions.buildovo.R;
import com.InfinitySolutions.buildovo.SignupActivity;
import com.InfinitySolutions.buildovo.interfaces.ApiClient;
import com.InfinitySolutions.buildovo.postData.loginPost;
import com.InfinitySolutions.buildovo.responseData.loginUserData.UserData;
import com.InfinitySolutions.buildovo.interfaces.retrofitPost;
import com.InfinitySolutions.buildovo.userData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivitySecond extends AppCompatActivity {


    SignInButton signInButton;
    private LoginViewModel loginViewModel;
    private Context loginContext;
    GoogleSignInClient mgoogleSignInClient;
    userData userdata;
    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginContext=this;
        forgotPasswordClick();


//        {
//            signInButton = findViewById(R.id.google_signin);
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//            mgoogleSignInClient = GoogleSignIn.getClient(this, gso);
//            signInButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    google_signIn();
//                }
//            });
//        }//google signin


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText numberEditText = findViewById(R.id.mobNumber);
        final TextInputLayout userInputLayout= findViewById(R.id.usernameLayout);
        final TextInputLayout passinputayout= findViewById(R.id.passwordLayout);
        final TextInputLayout numberinputayout= findViewById(R.id.mobNumberLayout);
        final TextView alternate_numTopass= findViewById(R.id.login_using_number_text);
        final Button loginButton = findViewById(R.id.login);
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
                if (numberinputayout.getVisibility() == View.GONE) {

                    final String userEmail= usernameEditText.getText().toString().trim().toLowerCase();
                    String password=passwordEditText.getText().toString();
                    JSONObject user_cred= new JSONObject();
                    System.out.println("LOGIN CLICKED");

                    try {
                        user_cred.put("email",userEmail);
                        user_cred.put("password",password);
                        System.out.println("DATA JSON CREATED"+user_cred.get("email"));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    retrofitCalls();




                    /*{
                        System.out.println("WORK................");
                        RequestQueue queue = Volley.newRequestQueue(loginContext);

                        String url = "https://bldvtest.herokuapp.com/api/user/login/";

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, user_cred, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Log.d("Response Got",response.getJSONObject("user").toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Gson gson =new Gson();
                                        userdata=gson.fromJson(response.toString(),userData.class);
                                        System.out.println("fromgson"+userdata.getUser().getName());
                                        System.out.println("fromgson"+userdata);

                                        loginViewModel.login(userdata.getUser().getName(), passwordEditText.getText().toString());
                                        homeActivityGoSearilized(userdata);

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        System.out.println(error.networkResponse.toString());

                                    }
                                });

                        queue.add(jsonObjectRequest);



                    }*/ //request post json
                } else {


                }


            }
        });
    }

    public void retrofitCalls()
    {

        ApiClient apiClient=new ApiClient();
         retrofitPost fit= new retrofitPost(){

             @Override
             public Call<UserData> userDataResponse(loginPost post) {
                 return null;
             }
         };

        Call<UserData> data=fit.userDataResponse(new loginPost("",""));
        data.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful())
                {
                    Log.i("Tager", "post submitted to API." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.i("Tager", "Failure." );

            }
        });







    }


    void forgotPasswordClick()
    {
        TextView forgotpassText= findViewById(R.id.forgot_pass_text);
        forgotpassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(loginContext);
                {
                    dialog.setContentView(R.layout.forgot_password_dialog);

                }//create Dialog
                dialog.show();
            }
        });
    }


    public  void send_userDetails(String name,String email,String password,String contact, String address)
    {



    }

    public void homeActivityGo() {
        Intent i = new Intent(this, HomePage.class);
        i.putExtra("loggedin",false);
        startActivity(i);

    }
    public void homeActivityGoSearilized(userData userdata) {
        Intent i = new Intent(this, HomePage.class);
        i.putExtra("userData",userdata);
        i.putExtra("loggedin",true);
        userData ud=(userData) i.getSerializableExtra("userData");
        System.out.println(ud.getUser().getName());
        startActivity(i);
        finish();

    }
    public void homeActivity(View view) {

        homeActivityGo();

    }

    private void google_signIn() {

    }

    public void goSigninActivity(View view)
    {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);

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
