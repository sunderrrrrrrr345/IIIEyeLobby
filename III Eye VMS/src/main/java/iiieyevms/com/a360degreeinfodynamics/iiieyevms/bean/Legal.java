package iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Admin on 3/7/2017.
 */
public class Legal implements Serializable {
    public Context context;
    public String disclaimer;
    public String privacypolicy;
    public String id;
    public String vid;

    public Legal(){

    }

    public Legal(Context context,String id,String disclaimer,String privacypolicy,String vid){
        this.context=context;
        this.id=id;
        this.privacypolicy=privacypolicy;
        this.disclaimer=disclaimer;
        this.vid=vid;

    }


    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
    public void setDisclaimer(String disclaimer){
        this.disclaimer=disclaimer;
    }
    public String getDisclaimer(){
        return disclaimer;
    }
    public  void setPrivacy(String privacypolicy){
        this.privacypolicy=privacypolicy;
    }
    public String getPrivacypolicy()
    {
        return privacypolicy;
    }
   public  void setVID(String vid){
        this.vid=vid;
    }
    public String getVID()
    {
        return vid;
    }
}
