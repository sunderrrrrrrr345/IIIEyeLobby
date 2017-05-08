package iiieyevms.com.a360degreeinfodynamics.iiieyevms.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManagerCheck {
	Context mcontext = null;
	
	public NetworkManagerCheck(Context context) {
		
		mcontext = context;
		
	}

	
	 public boolean isConnectToInternet() {
			ConnectivityManager connectivity = (ConnectivityManager) mcontext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}

			}
			return false;
		}
	
}
