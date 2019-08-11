package resident_tracking;

import java.util.*;

public class LoginTime {

public static String getLoginTime(){    
		
	String time=null;
	
	Calendar c = Calendar.getInstance();
	int hour = c.get(Calendar.HOUR_OF_DAY);
	int minute = c.get(Calendar.MINUTE);
	String hour_string=null;   
	String min_string=null;			

	if (hour < 10){
		hour_string = "0"+Integer.toString(hour);
	}
	else {
		hour_string = Integer.toString(hour);
	}
			
	if (minute < 10){
		min_string = "0"+Integer.toString(minute);
	}
	else {
		min_string = Integer.toString(minute);
	}
			
	time=hour_string+":"+min_string+" est";

	return time;

}

public static String convEpochTime(long sessionTime){
	
	String login_time=null;
		
	Calendar c = Calendar.getInstance();
	c.setTimeInMillis(sessionTime);
	
	int hour = c.get(Calendar.HOUR_OF_DAY);
	int minute = c.get(Calendar.MINUTE);
	String hour_string=null;   
	String min_string=null;			
	
	if (hour < 10){
		hour_string = "0"+Integer.toString(hour);
	}
	else {
		hour_string = Integer.toString(hour);
	}
			
	if (minute < 10){
		min_string = "0"+Integer.toString(minute);
	}
	else {
		min_string = Integer.toString(minute);
	}
			
	login_time=hour_string+":"+min_string+" est";

	return login_time;
}







}