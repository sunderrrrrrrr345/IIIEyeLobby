package iiieyevms.com.a360degreeinfodynamics.iiieyevms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.CommonMethod;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private UserPrefrences userPrefrence;
    private TextView name, alert_moble, email, flat_number;
    private TextView done, mobile;
    private ProgressDialog pDialog;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userPrefrence = new UserPrefrences(ProfileActivity.this);
        mobile = (TextView) findViewById(R.id.ed_mobile_number);
        name = (TextView) findViewById(R.id.ed_name);
        alert_moble = (TextView) findViewById(R.id.ed_alter_password);
        email = (TextView) findViewById(R.id.ed_email);
        flat_number = (TextView) findViewById(R.id.ed_flat_number);
     //   done = (TextView) findViewById(R.id.btn_login);
        back = (ImageView) findViewById(R.id.img_back);
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            mobile.setText(userPrefrence.getPrimaryNumber());
            alert_moble.setText(userPrefrence.getSecondryNumber());
            email.setText(userPrefrence.getEmailId());
            flat_number.setText(userPrefrence.getFlatNumber());
            name.setText(userPrefrence.getUserName());
//            done.setOnClickListener(this);
            back.setOnClickListener(this);
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED)
        {
            Internet();
        }


    }

    private void Internet() {
        AlertDialog.Builder build = new AlertDialog.Builder(ProfileActivity.this);
        build.setMessage("No Internet found.Check your connection or try again.");
        build.setTitle("Oh no!");
        build.setCancelable(true);
        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                    pDialog = new ProgressDialog(ProfileActivity.this);
                    pDialog.setMessage("Please Wait......");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    UpdateProfile();
                }
                break;
            case R.id.img_back:
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
                finish();
                break;
        }

    }

    private void Logout() {

        userPrefrence.logout();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void UpdateProfile() {
        if (Checkvalidation()) {
            mobile.clearFocus();
            alert_moble.clearFocus();
            email.clearFocus();
            flat_number.clearFocus();
            name.clearFocus();

            pDialog = new ProgressDialog(ProfileActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(true);
            pDialog.show();
       //     Update_Much_More();
        }

    }

   /* private void Update_Much_More() {



      JsonObjectRequest req = new JsonObjectRequest(Url_Details.Update_Profile+"?primary_number="+mobile.getText().toString().trim()+"&secondry_number="+alert_moble.getText().toString().trim()+"&email="+email.getText().toString().trim()+"&name="+name.getText().toString().trim(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject responcestr = response.getJSONObject("response");
                            String statuscode = responcestr.getString("code");
                            JSONArray responseSuccess = responcestr.getJSONArray("detail");
                            userPrefrence.setUserId(responseSuccess.getJSONObject(0).getString("user_id"));
                            userPrefrence.setEmailId(responseSuccess.getJSONObject(0).getString("email"));
                            userPrefrence.setUserName(responseSuccess.getJSONObject(0).getString("name"));
                            userPrefrence.setSecondryNumber(responseSuccess.getJSONObject(0).getString("secondry_number"));
                            userPrefrence.setPrimaryNumber(responseSuccess.getJSONObject(0).getString("primary_number"));
                            userPrefrence.setFlatNo(responseSuccess.getJSONObject(0).getString("flat_no"));
                            System.out.println(statuscode);
                            Toast.makeText(getApplicationContext(), "profile updated  successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            startActivity(intent);
                            pDialog.hide();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                //    Log.d("Send Query Response", error.toString() + "Error message " + error.getMessage());
                Toast.makeText(ProfileActivity.this, "Some Error Occured ", Toast.LENGTH_LONG).show();
                pDialog.hide();

            }
        });

        NetworkHelper.getInstance(ProfileActivity.this).addToRequestQueue(req);

    }
*/
    private boolean Checkvalidation() {
        if (mobile.getText().toString().trim().length() < 10) {
            CommonMethod.showAlert("Mobile Number should not be less than 10 character", ProfileActivity.this);
            return false;
        } else if (mobile.getText().toString().trim().length() > 13) {
            CommonMethod.showAlert("Mobile Number should not be greater than 13 character", ProfileActivity.this);
            return false;
        } else if (name.getText().toString().trim().length() == 0) {
            CommonMethod.showAlert("Please Enter valid Name", ProfileActivity.this);
            return false;
        } else if (alert_moble.getText().toString().trim().length() < 10) {
            CommonMethod.showAlert("Alter Mobile Number should not be less than 10 character", ProfileActivity.this);
            return false;
        } else if (alert_moble.getText().toString().trim().length() > 13) {
            CommonMethod.showAlert("Alter Mobile Number should not be greater than 13 character", ProfileActivity.this);
            return false;
        } else if (!CommonMethod.isEmailValid(email.getText().toString()
                .trim())) {
            CommonMethod.showAlert("Please Enter valid email", ProfileActivity.this);
            return false;
        } else if (flat_number.getText().toString().trim().length() == 0) {
            CommonMethod.showAlert("Please Enter FLat Number", ProfileActivity.this);
        }
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;

        }
    }


    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
        finish();
    }
}
