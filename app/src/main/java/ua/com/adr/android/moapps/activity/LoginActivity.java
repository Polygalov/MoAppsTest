package ua.com.adr.android.moapps.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.adr.android.moapps.api.APIService;
import ua.com.adr.android.moapps.api.ApiUtils;
import ua.com.adr.android.moapps.model.Post;
import ua.com.adr.android.moapps.R;
import ua.com.adr.android.moapps.model.UserAccount;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private APIService mAPIService;
    SharedPreferences sPref;
    EditText mailEditText;
    EditText passEditText;
    private String mail;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailEditText = (EditText) findViewById(R.id.login_email);
        passEditText = (EditText) findViewById(R.id.login_pwd);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        Button infoButton = (Button) findViewById(R.id.btn_info);
        ImageView icDelEmail = (ImageView) findViewById(R.id.icDelEmail);
        ImageView icDelPass = (ImageView) findViewById(R.id.icDelPass);

        loginButton.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        icDelEmail.setOnClickListener(this);
        icDelPass.setOnClickListener(this);

        mAPIService = ApiUtils.getAPIService();

        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        mail = sPref.getString("login", null);
        pass = sPref.getString("password", null);
        if(mail != null && pass != null && !mail.equals("") && !pass.equals("")) {
            sendPost(mail, pass);
        }

    }

    public void sendPost(final String mail, final String pass) {
        final ProgressDialog dialog;
        // Progress Dialog for User Interaction
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle(getString(R.string.text_wait));
        dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.show();

        UserAccount mUserAccount = new UserAccount(mail, pass);
        mAPIService.savePost(mUserAccount).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                //Dismiss Dialog
                dialog.dismiss();
                Post mPost = response.body();
                if(response.isSuccessful()) {
                    String returnedData = mPost.getData();
                    boolean returnedError = mPost.isErr();
                    if(returnedError) {
                        Toast.makeText(LoginActivity.this, R.string.err_sign_in, Toast.LENGTH_LONG).show();
                    } else
                        setDefaults("token", returnedData, mail, pass, LoginActivity.this);
                        Intent userTokenIntent = new Intent(LoginActivity.this, UserAppActivity.class);
                        startActivity(userTokenIntent);

                } else
                    Toast.makeText(LoginActivity.this, R.string.err_sign_in, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //Dismiss Dialog
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, R.string.err_service_unavailable, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    // Save account preferences
    public static void setDefaults(String key, String value, String sMail, String sPass, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.putString("login", sMail);
        editor.putString("password", sPass);
        editor.commit();
    }

    public static String getTokenDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mail = mailEditText.getText().toString().trim();
                pass = passEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)) {
                    sendPost(mail, pass);
                } else if(TextUtils.isEmpty(mail)) {
                    Toast.makeText(LoginActivity.this, R.string.toast_email_empty, Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, R.string.toast_password_empty, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_info:
                showInfoDialog();
                break;
            case R.id.icDelEmail:
                mailEditText.setText("");
                break;
            case R.id.icDelPass:
                passEditText.setText("");
                break;
        }
    }

    private void showInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("")
                .setMessage(R.string.alert_info)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}