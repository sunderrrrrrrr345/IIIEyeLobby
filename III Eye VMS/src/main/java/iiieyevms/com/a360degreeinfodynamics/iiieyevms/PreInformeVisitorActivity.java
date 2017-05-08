package iiieyevms.com.a360degreeinfodynamics.iiieyevms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.adapter.PreAdapter;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.adapter.PreAdapter_gust;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.CommonMethod;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;

public class PreInformeVisitorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView add_plus, back;
    private ProgressDialog pDialog;
    private String name_value, mobile_value;
    private ListView disclaimer_list_view;
    private ArrayList<Legal> list, list_guest;
    private PreAdapter adapter;
    private PreAdapter_gust adapter_guest;
    private UserPrefrences userPrefrence;
    private String get_data_from_Home;
    private TextView title;
    private LinearLayout four, three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_informe_visitor);
        userPrefrence = new UserPrefrences(PreInformeVisitorActivity.this);
        back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.tv_title);
        back.setOnClickListener(this);
        four = (LinearLayout) findViewById(R.id.linear);
        three = (LinearLayout) findViewById(R.id.linear1);
        disclaimer_list_view = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        list_guest = new ArrayList<>();
        get_data_from_Home = getIntent().getExtras().getString("Visitor");
        Pre(get_data_from_Home);
        if (Integer.parseInt(get_data_from_Home) == 1) {
            adapter = new PreAdapter(PreInformeVisitorActivity.this, list);
            disclaimer_list_view.setAdapter(adapter);
            four.setVisibility(View.VISIBLE);
            three.setVisibility(View.GONE);
            title.setText("Pre Inform Visitor");
        } else {
            adapter_guest = new PreAdapter_gust(PreInformeVisitorActivity.this, list_guest);
            disclaimer_list_view.setAdapter(adapter_guest);
            four.setVisibility(View.GONE);
            three.setVisibility(View.VISIBLE);
            title.setText("My Guest");
        }
        Log.i("get_data_from_Home", get_data_from_Home);

    }

    private void Pre(String get_data_from_Home1) {
        if (Integer.parseInt(get_data_from_Home1) == 1) {
            pDialog = new ProgressDialog(PreInformeVisitorActivity.this);
            pDialog.setMessage("Please wait......");
            Log.i("get_data_from_Home1234", get_data_from_Home1);
            pDialog.setCancelable(true);
            pDialog.show();
            JsonObjectRequest json_object_request = new JsonObjectRequest(Url_Details.List + "?user_id=" + userPrefrence.getUserId() + "&society_id=" + userPrefrence.getSocietyId() + "&status=" + get_data_from_Home1, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject json_object = response.getJSONObject("response");
                                int code = json_object.getInt("code");
                                // String message = json_object.getString("message");
                                Log.i("Released", "" + code);

                                if (code == 200) {
                                    JSONObject detailobject = json_object.getJSONObject("message");
                                    Log.i("Released", "" + detailobject);
                                    JSONArray user_details = detailobject.getJSONArray("detail");
                                    for (int j = 0; j < user_details.length(); j++) {
                                        Legal legal = new Legal();
                                        JSONObject json_object_message = user_details.getJSONObject(j);
                                        String id = json_object_message.getString("id");
                                        Log.i("Visti", "" + id);
                                        String disclaimer = json_object_message.getString("name");
                                        String privacypolicy = json_object_message.getString("mobile");
                                        int visit = json_object_message.getInt("visit_status");
                                        //int visitor_id = json_object_message.getInt("id");
                                        Log.i("Released", "" + id);
                                    /*if(visit==1 ){*/
                                        //if(get_data_from_Home.equals("1")) {
                                        int add = j + 1;
                                        legal.setId(String.valueOf(add));
                                        legal.setDisclaimer(disclaimer);
                                        legal.setPrivacy(privacypolicy);
                                        legal.setVID(id);
                                        list.add(legal);


                                    }
                                    adapter.notifyDataSetChanged();

                                } else {
                                    String message = json_object.getString("message");
                                    CommonMethod.showAlert("No Record Found", PreInformeVisitorActivity.this);

                                }
                                pDialog.hide();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PreInformeVisitorActivity.this, "Server side error", Toast.LENGTH_SHORT).show();
                    pDialog.hide();
                }
            });
            NetworkHelper.getInstance(PreInformeVisitorActivity.this).addToRequestQueue(json_object_request);


        }
        if (Integer.parseInt(get_data_from_Home1) == 2) {
            pDialog = new ProgressDialog(PreInformeVisitorActivity.this);
            pDialog.setMessage("Please wait......");
            pDialog.setCancelable(true);
            pDialog.show();
            JsonObjectRequest json_object_request = new JsonObjectRequest(Url_Details.List + "?user_id=" + userPrefrence.getUserId() + "&society_id=" + userPrefrence.getSocietyId() + "&status=" + get_data_from_Home1, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject json_object = response.getJSONObject("response");
                                int code = json_object.getInt("code");
                                // String message = json_object.getString("message");
                                Log.i("Released", "" + code);

                                if (code == 200) {
                                    JSONObject detailobject = json_object.getJSONObject("message");
                                    Log.i("Released", "" + detailobject);
                                    JSONArray user_details = detailobject.getJSONArray("detail");
                                    for (int j = 0; j < user_details.length(); j++) {
                                        Legal legal = new Legal();
                                        JSONObject json_object_message = user_details.getJSONObject(j);
                                        String id = json_object_message.getString("id");
                                        Log.i("Visti", "" + id);
                                        String disclaimer = json_object_message.getString("name");
                                        String privacypolicy = json_object_message.getString("mobile");
                                        int visit = json_object_message.getInt("visit_status");
                                        //int visitor_id = json_object_message.getInt("id");
                                        Log.i("Released", "" + id);
                                    /*if(visit==1 ){*/
                                        //if(get_data_from_Home.equals("1")) {
                                        int add = j + 1;
                                        legal.setId(String.valueOf(add));
                                        legal.setDisclaimer(disclaimer);
                                        legal.setPrivacy(privacypolicy);
                                        list_guest.add(legal);


                                    }
                                    adapter_guest.notifyDataSetChanged();

                                } else {
                                    String message = json_object.getString("message");
                                    CommonMethod.showAlert(message, PreInformeVisitorActivity.this);

                                }
                                pDialog.hide();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PreInformeVisitorActivity.this, "Server side error", Toast.LENGTH_SHORT).show();
                    pDialog.hide();
                }
            });
            NetworkHelper.getInstance(PreInformeVisitorActivity.this).addToRequestQueue(json_object_request);

        }

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.img_back:
                startActivity(new Intent(PreInformeVisitorActivity.this, HomeActivity.class));
                finish();
                break;
        }
    }


    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;

        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PreInformeVisitorActivity.this, HomeActivity.class));
        finish();
    }
}
