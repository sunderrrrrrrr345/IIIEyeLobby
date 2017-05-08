package iiieyevms.com.a360degreeinfodynamics.iiieyevms.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
public class PreAdapter_gust extends BaseAdapter {
    private Context context;
    private List<Legal> legal;
    private ProgressDialog pDialog;
    LayoutInflater inflater;
    private UserPrefrences  userPrefrences;
    public PreAdapter_gust(Context mContext, List<Legal> legal) {
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
            view = inflater.inflate(R.layout.pre_adapter_gust, viewGroup, false);
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


        return view;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, mobile, id;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            id = (TextView) itemView.findViewById(R.id.id);

        }

    }

}
