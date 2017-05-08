package iiieyevms.com.a360degreeinfodynamics.iiieyevms.network;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import iiieyevms.com.a360degreeinfodynamics.iiieyevms.PreInformeVisitorActivity;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.R;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.bean.Legal;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.network.NetworkHelper;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.Url_Details;
import iiieyevms.com.a360degreeinfodynamics.iiieyevms.util.UserPrefrences;


@SuppressWarnings("deprecation")
public class JsonParserNew {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String result = "";
	static String json = "";
	private Context context=null;

	// constructor
	public JsonParserNew(Context context) {
		
		this.context=context;

	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public JSONObject getJSONFromUrl(String url) throws Exception {

		// Making HTTP request
		try {

			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 50000;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 120000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Accept", "JSON");

			// httpPost.setHeader("Cache-Control", "no-cache");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			try {
				jObj = new JSONObject(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}catch (ConnectTimeoutException e) {
			   e.printStackTrace();
			   Toast.makeText(context, context.getString(R.string.Alert_Timeout_Msg), Toast.LENGTH_SHORT).show();
			  // throw new SocketTimeoutException(context.getString(R.string.Alert_Timeout_Msg));
			     }catch (SocketTimeoutException e) {
			   e.printStackTrace();
			   Toast.makeText(context, context.getString(R.string.Alert_Timeout_Msg), Toast.LENGTH_SHORT).show();

			   throw new SocketTimeoutException(context.getString(R.string.Alert_Timeout_Msg));    
			  }catch (UnknownHostException e) {
			   e.printStackTrace();
			   Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();

			  // throw new UnknownHostException(context.getString(R.string.Network_Connection_Error_Msg));
			  }catch (SocketException e) {
			   e.printStackTrace();
			   Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();

			  // throw new SocketException(context.getString(R.string.Network_Connection_Error_Msg));
			  }catch(Exception e){
			   e.printStackTrace();
			   Toast.makeText(context, context.getString(R.string.Network_Connection_Error_Msg), Toast.LENGTH_SHORT).show();

			   throw new Exception(context.getString(R.string.Network_Connection_Error_Msg));   
			  }

		// return JSON String
		return jObj;

	}

}
