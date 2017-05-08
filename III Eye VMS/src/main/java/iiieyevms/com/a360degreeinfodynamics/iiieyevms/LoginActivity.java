package iiieyevms.com.a360degreeinfodynamics.iiieyevms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.CommonMethod;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mobile, password;
    private TextView login;
    private UserPrefrences userPrefrences;
    private ProgressDialog pDialog;
    private String msg;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPrefrences = new UserPrefrences(LoginActivity.this);
        mobile = (EditText) findViewById(R.id.ed_mobile_number);
        password = (EditText) findViewById(R.id.ed_password);
        login = (TextView) findViewById(R.id.btn_login);
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            if (userPrefrences.getPrimaryNumber() == null && userPrefrences.getPassword() == null) {
                login.setOnClickListener(this);
                Log.i("userPrefrences",":"+userPrefrences.getPrimaryNumber());
                Log.i("userPrefrences1",":"+userPrefrences.getPassword());

            } else {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                Log.i("userPrefrences",":"+userPrefrences.getPrimaryNumber());
                Log.i("userPrefrences1",":"+userPrefrences.getPassword());
            }


        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Internet();


        }


    }

    private void Internet() {
        AlertDialog.Builder build = new AlertDialog.Builder(LoginActivity.this);
        build.setMessage("No Internet found.Check your connection or try again.");
        build.setTitle("Oh no!");
        build.setCancelable(true);
        build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alert = build.create();
        alert.show();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (Checkvalidation()) {
                    pDialog = new ProgressDialog(LoginActivity.this);
                    pDialog.setMessage("Please wait..........");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    DoLogin();
                }

        }
    }

    private void DoLogin() {

        JsonObjectRequest req = new JsonObjectRequest(Url_Details.Login + "?mobile=" + mobile.getText().toString().trim() + "&password=" + password.getText().toString()+"&society_id="+userPrefrences.getSocietyId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject json_object = response.getJSONObject("response");
                    msg = json_object.getString("message");
                    code = json_object.getInt("code");
                    //Log.d("Sign Up Response", msg.toString());
                    Log.d("Sign Up Response", "" + code);
                   //
                    if (code == 200) {
                        JSONObject detailobject = json_object.getJSONObject("message");
                        JSONArray user_details = detailobject.getJSONArray("detail");
                        userPrefrences.setUserId(user_details.getJSONObject(0).getString("user_id"));
                        userPrefrences.setSocietyId(user_details.getJSONObject(0).getString("society_id"));
                        userPrefrences.setFlatNo(user_details.getJSONObject(0).getString("flat_no"));
                        userPrefrences.setEmailId(user_details.getJSONObject(0).getString("email"));
                        userPrefrences.setPrimaryNumber(user_details.getJSONObject(0).getString("primary_number"));
                        userPrefrences.setSecondryNumber(user_details.getJSONObject(0).getString("secondry_number"));
                        userPrefrences.setUserName(user_details.getJSONObject(0).getString("name"));
                        userPrefrences.setFlatNumber(user_details.getJSONObject(0).getString("flat_number"));
                        userPrefrences.setUserType(user_details.getJSONObject(0).getString("user_type"));
                        userPrefrences.setPassword(user_details.getJSONObject(0).getString("password"));
                        Log.d("Sign Up Response", user_details.getJSONObject(0).getString("primary_number"));
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        pDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                //     Log.d("Sign Up Response", error.toString() + "Error message " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Server Side Error Occured", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });


        // Add the request to the RequestQueue.
        NetworkHelper.getInstance(LoginActivity.this).addToRequestQueue(req);
    }

    private boolean Checkvalidation() {
        if (mobile.getText().toString().trim().length() < 10) {
            CommonMethod.showAlert("Mobile number should not less than 10 character", LoginActivity.this);
            return false;
        } else if (mobile.getText().toString().trim().length() > 13) {
            CommonMethod.showAlert("Mobile number should not greater  than 13 character", LoginActivity.this);
            return false;
        } else if (password.getText().toString().trim().length() < 5) {
            CommonMethod.showAlert("Password should not less than 5 character", LoginActivity.this);
            return false;
        }

        return true;
    }

    public void onDestroy(){
        super.onDestroy();
        if(pDialog!=null){
            pDialog.dismiss();
            pDialog=null;
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
