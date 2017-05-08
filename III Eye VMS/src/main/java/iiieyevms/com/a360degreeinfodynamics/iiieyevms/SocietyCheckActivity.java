package iiieyevms.com.a360degreeinfodynamics.iiieyevms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.robohorse.gpversionchecker.GPVersionChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.CommonMethod;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;

public class SocietyCheckActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView pre, security, profile, logout, myguest;
    private EditText society_value;
    private TextView btnSubmit;
    private UserPrefrences userPrefrences;
    private String name_value, mobile_value;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.society_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new GPVersionChecker.Builder(this).create();
        userPrefrences = new UserPrefrences(SocietyCheckActivity.this);
        society_value = (EditText) findViewById(R.id.ed_society);
        btnSubmit = (TextView) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            String newVersion = null;
            if (userPrefrences.getPrimaryNumber() == null && userPrefrences.getPassword() == null) {
                btnSubmit.setOnClickListener(this);
                Log.i("userPrefrences", ":" + userPrefrences.getPrimaryNumber());
                Log.i("userPrefrences1", ":" + userPrefrences.getPassword());

               try {
                    newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "a360degreeinfodynamics.com.vms1" + "&hl=en")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com").get()
                            .select("div[itemprop=softwareVersion]").first()
                            .ownText();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                Log.i("userPrefrences", ":" + userPrefrences.getPrimaryNumber());
                Log.i("userPrefrences1", ":" + userPrefrences.getPassword());

               try {
                    newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "a360degreeinfodynamics.com.vms1" + "&hl=en")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com").get()
                            .select("div[itemprop=softwareVersion]").first()
                            .ownText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




         //   Log.e("new Version", newVersion);



        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Internet();


        }

    }

    private void Internet() {
        AlertDialog.Builder build = new AlertDialog.Builder(SocietyCheckActivity.this);
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
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.btn_submit:
                societyVerification();
                break;
        }
    }

    private void societyVerification() {
        if(CheckValidation()) {


            JsonObjectRequest societyVerificationRequest = new JsonObjectRequest(Url_Details.society_check + "?society=" + society_value.getText().toString(), null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String outputMsg, code;
                        JSONObject responcedata = response.getJSONObject("response");

                        code = responcedata.getString("code");
                        System.out.println("" + code);

                        if (code.equals("200")) {
                            JSONObject detailobject = responcedata.getJSONObject("message");
                            JSONArray user_details = detailobject.getJSONArray("detail");
                            // final String soicityid = user_details.getJSONObject(0).getString("society_id");

                            if (pDialog != null) {
                                pDialog.dismiss();
                            }
                            userPrefrences.setSocietyId(user_details.getJSONObject(0).getString("society_id"));
                       /* AlertDialog.Builder build = new AlertDialog.Builder(SocietyCheckActivity.this);
                        build.setMessage("Welcome to VMS!");
                        build.setCancelable(true);
                        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {*/
                            Toast.makeText(SocietyCheckActivity.this, "Welcome to VMS!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SocietyCheckActivity.this, LoginActivity.class);
                            //       intent.putExtra("society_id", soicityid);
                            startActivity(intent);
                            finish();
                      /*      }
                        });
                        AlertDialog alert = build.create();
                        alert.show();*/
                        }
                        if (code.equals("401")) {
                            if (pDialog != null) {
                                pDialog.dismiss();
                            }
                            AlertDialog.Builder build = new AlertDialog.Builder(SocietyCheckActivity.this);
                            build.setMessage("Enter Valid Soceity Info!");
                            build.setCancelable(true);
                            build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    society_value.clearFocus();
                                    society_value.setText("");
                                }
                            });
                            AlertDialog alert = build.create();
                            alert.show();
                        }

                    } catch (JSONException e) {
                        System.out.println("CommentandLikeFragment.onActivityCreated(...).new Listener() {...}.onResponse()" + e.getMessage());
                        e.printStackTrace();

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        VolleyLog.d("Error Msg", "Error: " + error.getMessage());
                        pDialog.dismiss();
                        System.out.println("MainActivity.SendEmail()" + error.getMessage());
                        System.out.println("MainActivity.SendEmail()" + error.getLocalizedMessage());
                    } catch (Exception e) {
                        System.out.println("MainActivity.SendEmail()" + e.getMessage());
                    }
                    CommonMethod.showAlert("Please try again in a few minutes. Sorry for the inconvenience.",
                            SocietyCheckActivity.this);


                }

            });
            pDialog = ProgressDialog.show(SocietyCheckActivity.this, "", "please wait ...");
            societyVerificationRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            NetworkHelper.getInstance(SocietyCheckActivity.this).addToRequestQueue(societyVerificationRequest);
        }

    }

    private boolean CheckValidation() {
    if(society_value.getText().toString().length()==0){
        CommonMethod.showAlert("Please enter valid Society",SocietyCheckActivity.this);
        return false;
    }

    return true;
    }

   /* private void showCustomDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(SocietyCheckActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);


        final EditText etName = (EditText) dialog.findViewById(R.id.editText_name);
        Button button = (Button) dialog.findViewById(R.id.button1);
        final EditText etMobile = (EditText) dialog.findViewById(R.id.editText_mobile);

        // final String email = etEmail.getText().toString();


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name_value = etName.getText().toString();
                mobile_value = etMobile.getText().toString();
                if (name_value.trim().length() == 0) {
                    CommonMethod.showAlert("Please enter name", SocietyCheckActivity.this);
               *//* } else if (mobile_value.trim().length() < 10) {
                    CommonMethod.showAlert("Mobile number should not less than 10 character", SocietyCheckActivity.this);
                } else if(mobile_value.trim().length() > 13) {
                    CommonMethod.showAlert("Mobile number should not greater than 13 character", SocietyCheckActivity.this);
                *//*
                } else {
                    societyVerification();
                    dialog.dismiss();
                }
            }

            private void societyVerification() {
                JsonObjectRequest postEmailrequest = new JsonObjectRequest(Url_Details.New_Visitor + "?user_id=" + userPrefrences.getUserId() + "&society_id=" + userPrefrences.getSocietyId() + "&flat_number=" + userPrefrences.getFlatNo() + "&owner_mobile=" + userPrefrences.getPrimaryNumber() + "&visitor_name=" + name_value + "&visitor_mobile=" + mobile_value, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String outputMsg, code;
                            JSONObject responcedata = response.getJSONObject("response");
                            outputMsg = responcedata.getString("message");
                            code = responcedata.getString("code");
                            System.out.println("" + code);
                            System.out.println(outputMsg);
                            if (code.equals("200")) {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                }
                                AlertDialog.Builder build = new AlertDialog.Builder(SocietyCheckActivity.this);
                                build.setMessage(outputMsg);
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alert = build.create();
                                alert.show();
                            }
                            if (code.equals("401")) {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                }
                                AlertDialog.Builder build = new AlertDialog.Builder(SocietyCheckActivity.this);
                                build.setMessage(outputMsg);
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alert = build.create();
                                alert.show();
                            }

                        } catch (JSONException e) {
                            System.out.println("CommentandLikeFragment.onActivityCreated(...).new Listener() {...}.onResponse()" + e.getMessage());
                            e.printStackTrace();

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            VolleyLog.d("Error Msg", "Error: " + error.getMessage());
                            pDialog.dismiss();
                            System.out.println("MainActivity.SendEmail()" + error.getMessage());
                            System.out.println("MainActivity.SendEmail()" + error.getLocalizedMessage());
                        } catch (Exception e) {
                            System.out.println("MainActivity.SendEmail()" + e.getMessage());
                        }

                        CommonMethod.showAlert("Please try again in a few minutes. Sorry for the inconvenience.",
                                SocietyCheckActivity.this);
                        //new ErrorDialogFragment().show(getSupportFragmentManager(), ErrorDialogFragment.class.getName());

                    }

                });


                pDialog = ProgressDialog.show(SocietyCheckActivity.this, "", "please wait ...");
                postEmailrequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                NetworkHelper.getInstance(SocietyCheckActivity.this).addToRequestQueue(postEmailrequest);


            }
        });

        dialog.show();

    }*/



    public void onBackPressed(){
       // super.onBackPressed();
        AlertDialog.Builder build=new AlertDialog.Builder(SocietyCheckActivity.this);
        build.setTitle("Confirmation");
        build.setCancelable(false);
        build.setMessage("Kindly Rate it with 5 star to support our hard work.");
        build.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=a360degreeinfodynamics.com.vms1&hl=en")));
            }
        });

        build.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        build.setNeutralButton("Rate later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alert=build.create();
        alert.show();

    }

}
