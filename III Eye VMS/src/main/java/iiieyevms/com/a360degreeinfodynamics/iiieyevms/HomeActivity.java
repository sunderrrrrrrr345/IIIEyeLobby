package iiieyevms.com.a360degreeinfodynamics.iiieyevms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.CommonMethod;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title, ed_registered_worker,pre, security, profile, logout, myguest;
    private UserPrefrences userPrefrences;
    private String name_value,mobile_value,newData;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        userPrefrences = new UserPrefrences(HomeActivity.this);
        pre = (TextView) findViewById(R.id.ed_preinforme_visitor);
        security = (TextView) findViewById(R.id.ed_secuirty_pre_informe);
        profile = (TextView) findViewById(R.id.ed_profile);
        logout = (TextView) findViewById(R.id.ed_logout);
        myguest = (TextView) findViewById(R.id.ed_my_guest);
        title= (TextView) findViewById(R.id.tv_title);
        ed_registered_worker= (TextView) findViewById(R.id.ed_registered_worker);
        pre.setOnClickListener(this);
        security.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
        myguest.setOnClickListener(this);
        ed_registered_worker.setOnClickListener(this);
        title.setText("III Eye Visitor Mangement System");

    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent();
        switch (view.getId()) {
            case R.id.ed_preinforme_visitor:
                i=new Intent(HomeActivity.this,PreInformeVisitorActivity.class);
                i.putExtra("Visitor",""+1);
                startActivity(i);
                finish();
                break;
            case R.id.ed_secuirty_pre_informe:
                showCustomDialog();
                break;
            case R.id.ed_profile:
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                finish();
                break;
            case R.id.ed_logout:
                Logout();
                break;
            case R.id.ed_my_guest:
                i=new Intent(HomeActivity.this,PreInformeVisitorActivity.class);
                i.putExtra("Visitor",""+2);
                startActivity(i);
                finish();
                break;
            case R.id.ed_registered_worker:
                startActivity(new Intent(HomeActivity.this,Registered_Worker.class));
                finish();
                break;
        }
    }

    private void showCustomDialog() {

        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);


        final EditText etName = (EditText) dialog.findViewById(R.id.editText_name);
        Button button = (Button) dialog.findViewById(R.id.button1);
        final EditText etMobile = (EditText) dialog.findViewById(R.id.editText_mobile);

        //       final String email = etEmail.getText().toString();


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name_value = etName.getText().toString();
                newData  = name_value.replaceAll(" ", "%20");
                mobile_value = etMobile.getText().toString();
                if (name_value.trim().length() == 0) {
                    CommonMethod.showAlert("Please enter name", HomeActivity.this);
                } else if (mobile_value.trim().length() < 10) {
                    CommonMethod.showAlert("Mobile number should not less than 10 character", HomeActivity.this);
                } else if(mobile_value.trim().length() > 13) {
                    CommonMethod.showAlert("Mobile number should not greater than 13 character", HomeActivity.this);
                }else{
                    System.out.println(userPrefrences.getUserId());
                    System.out.println(userPrefrences.getSocietyId());
                    System.out.println("Flat Number"+userPrefrences.getFlatNo());

                     forgotPW();
                    dialog.dismiss();
                }
            }

            private void forgotPW() {
                JsonObjectRequest postEmailrequest = new JsonObjectRequest(Url_Details.New_Visitor + "?user_id=" + userPrefrences.getUserId() + "&society_id=" + userPrefrences.getSocietyId()+"&flat_number="+userPrefrences.getFlatNo()+"&owner_mobile="+userPrefrences.getPrimaryNumber()+"&visitor_name="+newData+"&visitor_mobile="+mobile_value, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String outputMsg, code;
                            JSONObject responcedata = response.getJSONObject("response");
                            outputMsg = responcedata.getString("message");
                            code = responcedata.getString("code");
                            System.out.println(""+code);
                            System.out.println(outputMsg);




                            if (code.equals("200")) {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                }
                                AlertDialog.Builder build=new AlertDialog.Builder(HomeActivity.this);
                                build.setMessage("Thanks! Visitor is add to Pre Info Visitor List.");
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent i1=new Intent(HomeActivity.this,PreInformeVisitorActivity.class);
                                        i1.putExtra("Visitor",""+1);
                                        startActivity(i1);

                                        finish();
                                    }
                                });
                                AlertDialog alert=build.create();
                                alert.show();
                            }
                            if (code.equals("401")) {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                }
                                AlertDialog.Builder build=new AlertDialog.Builder(HomeActivity.this);
                                build.setMessage(outputMsg);
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog alert=build.create();
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
                                HomeActivity.this);
                        //new ErrorDialogFragment().show(getSupportFragmentManager(), ErrorDialogFragment.class.getName());

                    }

                });


                pDialog = ProgressDialog.show(HomeActivity.this, "", "please wait ...");
                postEmailrequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                NetworkHelper.getInstance(HomeActivity.this).addToRequestQueue(postEmailrequest);


            }
        });

        dialog.show();



    }

    private void Logout() {
        AlertDialog.Builder build=new AlertDialog.Builder(HomeActivity.this);
        build.setCancelable(true);
        build.setMessage("Are you sure?You want to Logout account.");
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userPrefrences.logout();
                Intent intent = new Intent(HomeActivity.this, SocietyCheckActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert=build.create();
        alert.show();
    }


    public void onBackPressed(){
      //  super.onBackPressed();
        AlertDialog.Builder build=new AlertDialog.Builder(HomeActivity.this);
        build.setTitle("Confirmation");
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
