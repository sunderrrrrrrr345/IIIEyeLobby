package iiieyevms.com.a360degreeinfodynamics.iiieyevms.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefrences {

	SharedPreferences preferences;
	
	static UserPrefrences instance;
	Context _context;
	String device_id,userId, sessionId, userName,userlastName, emailId,wifi,tone,
			address, aboutMe, mobile,password,designation, gender,societyId,flat_no,primary_number,secondry_number,user_type,flat_number;

	int badgeCount = 0;
	Boolean rememberStatus;
	private static final String IS_LOGIN = "IsLoggedIn";
	public UserPrefrences(Context context) {
		if (context != null) {
			preferences = context.getSharedPreferences(
					"Drafting_prefrences7", Context.MODE_PRIVATE);
		}

	}
	
	public static synchronized UserPrefrences getInstance(Context context) {
		instance=instance==null?new UserPrefrences(context):instance;
		
		return instance;
	}

	public synchronized String getDevice_id() {
		device_id = preferences.getString(Constants.ANDROID_ID, null);
		return device_id;
	}


	public synchronized void setDevice_id(String device_id) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.ANDROID_ID, device_id);
		editor.commit();
	}
	public synchronized String getDesignation() {
		designation = preferences.getString(Constants.DESIGNATION, null);
		return designation;
	}


	public synchronized void setDesignation(String designation) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.DESIGNATION, designation);
		editor.commit();
	}

	public synchronized String getPassword() {
		password = preferences.getString(Constants.PASSWORD, null);
		return password;
	}

	

	public synchronized void setPassword(String password) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.PASSWORD, password);
		editor.commit();
	}

	public synchronized String getUserId() {
		userId = preferences.getString(Constants.USER_ID, null);
		return userId;
	}

	public synchronized void setUserId(String userId) {

		System.out.println("UserPrefrences.setUserId()"+ userId);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.USER_ID, userId);
		editor.commit();
	}


	public synchronized String getSessionId() {
		sessionId = preferences.getString(Constants.SESSION_ID, null);
		return sessionId;
	}

	public synchronized void setSessionId(String sessionId) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.SESSION_ID, sessionId);
		editor.commit();
	}

	public synchronized String getUserName() {
		userName = preferences.getString(Constants.USER_NAME, null);
		return userName;
	}

	public synchronized void setUserName(String userName) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.USER_NAME, userName);
		editor.commit();
	}
	public synchronized String getUserLastName() {
		userlastName = preferences.getString(Constants.USER_LAST_NAME, null);
		return userlastName;
	}

	public synchronized void setUserLastName(String userlastName) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.USER_LAST_NAME, userlastName);
		editor.commit();
	}

	public synchronized String getEmailId() {
		emailId = preferences.getString(Constants.EMAIL_ID, null);
		return emailId;
	}

	public synchronized void setEmailId(String emailId) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.EMAIL_ID, emailId);
		editor.commit();
	}
	
	public synchronized String getAddress() {
		address = preferences.getString(Constants.ADDRESS, null);
		return address;
	}

	public synchronized void setAddress(String address) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.ADDRESS, address);
		editor.commit();
	}
	
	
	public synchronized String getMobile() {
		mobile = preferences.getString(Constants.MOBILE, null);
		return mobile;
	}

	public synchronized void setMobile(String mobile) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.MOBILE, mobile);
		editor.commit();
	}

	public synchronized String getGender() {
		gender = preferences.getString(Constants.GENDER, null);
		return gender;
	}

	public synchronized void setGender(String gender) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.GENDER, gender);
		editor.commit();
	}

	public synchronized void  logout(){
	    
	      SharedPreferences.Editor editor = preferences.edit();
	      editor.clear();
	      editor.commit();
	   }
	
	public synchronized void createLoginSession(String name, String email, String password){
		// Storing login value as TRUE
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(Constants.USER_NAME, name);
		
		// Storing email in pref
		editor.putString(Constants.EMAIL_ID, email);
		editor.putString(Constants.PASSWORD, password);
		
		// commit changes
		editor.commit();
	}
	
	public void setLogIn(boolean value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(IS_LOGIN, value);
		editor.commit();
	}

	public boolean isLogIn() {
		boolean value = preferences.getBoolean(IS_LOGIN, false);
		return value;
	}



	public synchronized String getSocietyId() {
		societyId = preferences.getString(Constants.SOCIETYID, null);
		return societyId;
	}

	public synchronized void setSocietyId(String societyId) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(Constants.SOCIETYID, societyId);
		editor.commit();
	}

	public synchronized String getFlatNo(){
		flat_no=preferences.getString(Constants.FLATNO,null);
		return flat_no;
	}
	public synchronized void setFlatNo(String flat_no){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(Constants.FLATNO,flat_no);
		editor.commit();
	}

	public synchronized String getFlatNumber(){
		flat_number=preferences.getString(Constants.FLATNUMBER,null);
		return flat_number;
	}
	public synchronized void setFlatNumber(String flat_number){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(Constants.FLATNUMBER,flat_number);
		editor.commit();
	}

	public synchronized String getPrimaryNumber(){
		primary_number=preferences.getString(Constants.PRIMARYNUMBER,null);
		return primary_number;
	}
	public synchronized void setPrimaryNumber(String primary_number){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(Constants.PRIMARYNUMBER,primary_number);
		editor.commit();
	}


	public synchronized String getSecondryNumber(){
		secondry_number=preferences.getString(Constants.SECONDRYNUMBER,null);
		return secondry_number;
	}
	public synchronized void setSecondryNumber(String secondry_number){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(Constants.SECONDRYNUMBER,secondry_number);
		editor.commit();
	}

	public synchronized String getUserType(){
		user_type=preferences.getString(Constants.USERTYPE,null);
		return user_type;
	}
	public synchronized void setUserType(String user_type){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(Constants.USERTYPE,user_type);
		editor.commit();
	}
}