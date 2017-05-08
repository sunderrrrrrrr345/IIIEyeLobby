package iiieyevms.com.a360degreeinfodynamics.iiieyevms.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;


/**
 * Created by Admin on 3/7/2017.
 */
public class PreAdapter extends BaseAdapter {
    private Context context;
    private List<Legal> legal;
    private ProgressDialog pDialog;
    LayoutInflater inflater;
    private UserPrefrences userPrefrences;
    public PreAdapter(Context mContext, List<Legal> legal) {
        this.context = mContext;
        this.legal = legal;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return legal.size();
    }

    @Override
    public Legal getItem(int i) {
        return legal.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.pre_adapter, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
       // Log.i("id","id:"+viewHolder.id.length());
        final Legal currentListData = getItem(i);
        userPrefrences=new UserPrefrences(context);
        pDialog=new ProgressDialog(context);
        viewHolder.name.setText(currentListData.getDisclaimer());
        viewHolder.mobile.setText(currentListData.getPrivacypolicy());
        viewHolder.id.setText(currentListData.getId());
     //   viewHolder.vid.setText(currentListData.getVID());
        viewHolder.vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(context, "CLICK : " +currentListData.getVID()+":"+userPrefrences.getUserId()+":"+userPrefrences.getSocietyId(), Toast.LENGTH_SHORT).show();

                JsonObjectRequest societyVerificationRequest = new JsonObjectRequest(Url_Details.Delete_New_Visitor + "?user_id=" + userPrefrences.getUserId()+"&society_id="+userPrefrences.getSocietyId()+"&visitor_id="+currentListData.getVID(), null, new Response.Listener<JSONObject>() {

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
                               // userPrefrences.setSocietyId(user_details.getJSONObject(0).getString("society_id"));
                                AlertDialog.Builder build = new AlertDialog.Builder(context);
                                build.setMessage("Success");
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(context, PreInformeVisitorActivity.class);
                                        intent.putExtra("Visitor",""+1);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                                    }
                                });
                                AlertDialog alert = build.create();
                                alert.show();
                            }
                            if (code.equals("401")) {
                                if (pDialog != null) {
                                    pDialog.dismiss();
                                }
                                AlertDialog.Builder build = new AlertDialog.Builder(context);
                                build.setMessage("Success");
                                build.setCancelable(true);
                                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(context, PreInformeVisitorActivity.class);
                                        intent.putExtra("Visitor",""+1);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
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
                        //CommonMethod.showAlert("Please try again in a few minutes. Sorry for the inconvenience.",);
                        //new ErrorDialogFragment().show(getSupportFragmentManager(), ErrorDialogFragment.class.getName());

                    }

                });


                pDialog = ProgressDialog.show(context, "", "please wait ...");
                societyVerificationRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                NetworkHelper.getInstance(context).addToRequestQueue(societyVerificationRequest);



            }
        });

        return view;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, mobile, id, vid;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            id = (TextView) itemView.findViewById(R.id.id);
            vid = (TextView) itemView.findViewById(R.id.vid);

        }

      /*  public MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.name);
            mobile = (TextView) item.findViewById(R.id.mobile);
            id = (TextView) item.findViewById(R.id.id);
            vid = (TextView) item.findViewById(R.id.vid);

        }*/
    }

}
