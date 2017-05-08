package iiieyevms.com.a360degreeinfodynamics.iiieyevms.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class CommonMethod {
	
	
	public static void showAlert(String message, Activity context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	 public static boolean copyStream(InputStream input, OutputStream output)
	            throws IOException {
		 	boolean isCopied = false;
	        byte[] buffer = new byte[1024];
	        int bytesRead;
	        try {
				while ((bytesRead = input.read(buffer)) != -1) {
				    output.write(buffer, 0, bytesRead);
				}
				isCopied = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isCopied = false;
			}
	        return isCopied;
	    }
	 
	
	/** Called for checking Email Validation */
	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

}
