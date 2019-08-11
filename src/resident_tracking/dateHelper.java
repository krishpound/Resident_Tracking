package resident_tracking;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.Calendar;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class dateHelper {

	static Logger log = Logger.getLogger("dateHelper"); 
	
	public static boolean validateMonth(String m){
		
		boolean rc=true;
		int m_int=0;
		
		if (m.length() != 2){
			rc=false;		
		}
		
		if (rc){
			
			try{
			
				m_int = Integer.parseInt(m);
		
				if (m_int < 1 || m_int > 12){
					rc=false;
				}
			 			
			}
			catch (Exception e){
				rc=false;
			}
			
		}		
		
		return rc;
		
	}
	
	public static boolean validateDay(String d, int m, int y){
		
		GregorianCalendar g = new GregorianCalendar();
		boolean rc=true;
		boolean leapyear=g.isLeapYear(y);
		int d_int=0;
		
		if (d.length() != 2){
			rc=false;		
		}
		
		if (rc){
		
			try{
			
				d_int = Integer.parseInt(d);
		
				if (d_int < 1 || d_int > 31){
					rc=false;
				}
			 	
				//February
				if (m==2){
					
					if (leapyear){
						if (d_int > 29) rc=false;
					}
					else{
						if (d_int > 28) rc=false;
					}
				}
				
				if (m==4 || m==6 || m==9 || m==11){
					if (d_int > 30) rc=false;
				}
				
				if (m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12){
					if (d_int > 31) rc=false;
				}				
				
			}
			catch (Exception e){
				rc=false;
			}
		
		}		
		
		return rc;
		
	}
	
	public static boolean validateYear(String y){
		
		boolean rc=true;
		int y_int=0;
		
		if (y.length() != 4){
			rc=false;		
		}
		
		if (rc){
			
			try{
			
				y_int = Integer.parseInt(y);
		
				if (y_int > 2500 || y_int < 1900){
					rc=false;
				}
			 			
			}
			catch (Exception e){
				rc=false;
			}
		}
		
		return rc;
		
	}
		
	public static String checkDate(String arg1){
		
		String ruling=null;
		String MM=null;
		String DD=null;
		String YYYY=null;
		String[] tokens;
		boolean COND_CODE=true;
				
		if (null==arg1){
			ruling="void";
		}
		else{
			
			if (!arg1.contains("/")){
				ruling="format";
			}
			else{
				
				tokens=arg1.split("/");
				
				if (tokens.length != 3){
					ruling="format";					
				}
				else{
					MM=tokens[0];
					DD=tokens[1];
					YYYY=tokens[2];					
				}				
			}
		}
			
		if (null==ruling){
				
			COND_CODE=validateMonth(MM);
			
			if(COND_CODE){
				
				COND_CODE=validateDay(DD,Integer.parseInt(MM),Integer.parseInt(YYYY));	
				
				if(COND_CODE){
					
					COND_CODE=validateYear(YYYY);
				
					ruling = (COND_CODE) ? "ok":"invalid year";
									
				}
				else{
					ruling="invalid day";
				}
			}
			else{
				ruling="invalid month";
			}
		}
					
		return ruling;
		
	}
	
	public static long inBetweenDays(String startDate, String endDate){
		
		long days=0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		String a1[] = startDate.split("/");
		int m1 	= Integer.parseInt(a1[0]);
		int d1 	= Integer.parseInt(a1[1]);
		int y1 	= Integer.parseInt(a1[2]);
		String a2[] = endDate.split("/");
		int m2 	= Integer.parseInt(a2[0]);
		int d2 	= Integer.parseInt(a2[1]);
		int y2 	= Integer.parseInt(a2[2]);
		
		c1.set(y1,m1-1,d1);
		c2.set(y2,m2-1,d2);
		
		days = ((c2.getTime().getTime() - c1.getTime().getTime())	/ (24 * 3600 * 1000))+1;
		
		return days;
		
		
	}
		
	public static String rollDate (String dt, String rollFactor, int rollAmount){
		
		/*
		 * This method is called in 2 situations:
		 * 1) Graphical reports - verify the time line is no more than one month <== rollFactor MONTH
		 * 2) Graphical reports - dynamically generate the date series along the X-Axis <== rollFactor DAY
		 * 
		 */
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
	
		String tokens[] = dt.split("/");
		int mm 	 = Integer.parseInt(tokens[0]);
		int dd 	 = Integer.parseInt(tokens[1]);
		int yyyy = Integer.parseInt(tokens[2]);
		
		Calendar calendar = new GregorianCalendar(yyyy,mm-1,dd);
		
		if (rollFactor.equalsIgnoreCase("MONTH")){
			calendar.add(Calendar.MONTH,rollAmount);
			dt = sdf1.format(calendar.getTime());
		}
		else if(rollFactor.equalsIgnoreCase("DAY")){
			calendar.add(Calendar.DAY_OF_MONTH,rollAmount);
			dt = fixDate(sdf2.format(calendar.getTime()));
		}
		
		return dt;
	}

	public static Double fixDateDouble(String arg){
    	
    	String tokens[] = arg.split("/");
    	String mm=null;
    	String slash="/";
    	String dd=null;
    	Double retval;
    	
    	if (tokens[0].substring(0,1).equalsIgnoreCase("0")){
    		mm=tokens[0].substring(1,2);
    	}
    	else{
    		mm = tokens[0];
    	}
    	
    	if (tokens[1].substring(0,1).equalsIgnoreCase("0")){
    		dd=tokens[1].substring(1,2);
    	}
    	else{
    		dd = tokens[1];
    	}
    	
    	//arg = mm+slash+dd;
    	retval = Double.valueOf(dd);
   
    	return retval;
    	
    }	
	
	public static String fixDate(String arg){
    	
    	String tokens[] = arg.split("/");
    	String mm=null;
    	String slash="/";
    	String dd=null;
    	
    	if (tokens[0].substring(0,1).equalsIgnoreCase("0")){
    		mm=tokens[0].substring(1,2);
    	}
    	else{
    		mm = tokens[0];
    	}
    	
    	if (tokens[1].substring(0,1).equalsIgnoreCase("0")){
    		dd=tokens[1].substring(1,2);
    	}
    	else{
    		dd = tokens[1];
    	}
    	
    	//arg = mm+slash+dd;
    	arg = dd;
   
    	return arg;
    	
    }
	
	public static int parseDate(Date d, String factor){
		
		int ret_val=0;
		SimpleDateFormat year  = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day   = new SimpleDateFormat("dd");
		GregorianCalendar cal  = new GregorianCalendar();
		cal.setTime(d);
		
		String date = d.toString();
		
		if (factor.equalsIgnoreCase("day")){
			ret_val = Integer.parseInt(day.format(cal.getTime()));
		}
		else if (factor.equalsIgnoreCase("month")){
			ret_val = Integer.parseInt(month.format(cal.getTime()));
		}
		else if (factor.equalsIgnoreCase("year")){
			ret_val = Integer.parseInt(year.format(cal.getTime()));
		}
	
		return ret_val;
		
	}
	
	public static boolean checkMonth(String stDt, String enDt){
		
		boolean rc=true;
		String a1[] = stDt.split("/");
		int m1 	= Integer.parseInt(a1[0]);
		int d1 	= Integer.parseInt(a1[1]);
		int y1 	= Integer.parseInt(a1[2]);
		String a2[] = enDt.split("/");
		int m2 	= Integer.parseInt(a2[0]);
		int d2 	= Integer.parseInt(a2[1]);
		int y2 	= Integer.parseInt(a2[2]);
		
		rc = (m1 == m2) ? true:false; 
		
		return rc;
	}
		
	public static String fixTime(Date d, String factor){
		
		String return_value=null;
		SimpleDateFormat hh  = new SimpleDateFormat("HH");
		SimpleDateFormat mm  = new SimpleDateFormat("mm");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		
		if (factor.equalsIgnoreCase("time")){
	
			String hours24 = hh.format(cal.getTime());
			String minutes = mm.format(cal.getTime());
			int clocktime = Integer.parseInt(minutes);
			int decitime  = clocktime * 100 / 60;
			int Hours = Integer.parseInt(hours24);
			
			log.debug("fixTime hours24: "+hours24);
			log.debug("fixTime minutes: "+minutes);
			log.debug("fixTime clocktime: "+clocktime);
			log.debug("fixTime decitime: "+decitime);
			
			if (decitime < 10){
			
				return_value = Hours+".0"+decitime;
			}
			else{
				return_value = Hours+"."+decitime;
			}
				
			log.debug("fixTime return_value: "+return_value);	
				
				
		}
		else if (factor.equalsIgnoreCase("day")){
			
			int DD = Integer.parseInt(day.format(cal.getTime()));
			return_value = String.valueOf(DD);
			
		}
		
		return return_value;
		
	}
	
	public static int compareDate(String arg1, String arg2){
		
		int greater_date=0;
		String tokens[];
		int date1_mm,date1_dd,date1_yyyy,date2_mm,date2_dd,date2_yyyy;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		try {
		
			if (arg1 != null){
			
				tokens=arg1.split("/");
				date1_mm   = Integer.parseInt(tokens[0]);
				date1_dd   = Integer.parseInt(tokens[1]);
				date1_yyyy = Integer.parseInt(tokens[2]);
			
				c1.set(date1_yyyy, date1_mm-1, date1_dd);
								
			}	
		
			if (arg2 != null){
			
				tokens=arg2.split("/");
				date2_mm   = Integer.parseInt(tokens[0]);
				date2_dd   = Integer.parseInt(tokens[1]);
				date2_yyyy = Integer.parseInt(tokens[2]);
			
				c2.set(date2_yyyy, date2_mm-1, date2_dd);
							
			}	
		 
			if (arg1 != null && arg2 != null){
				
				
				if (c1.before(c2)) {
					greater_date = 2;
				}
				else if (c1.after(c2)) {
					greater_date = 1;
				}  
				else if(c1.equals(c2)) {
					 greater_date = 0;
				}
	    	
			}
	    
		}
		catch (Exception e){
			
			System.out.println("An exception occurred in compareDate method of dateHelper class");
			System.out.println("Arguments received were arg1="+arg1+" arg2="+arg2);
			greater_date = -1;
		}
		
		c1.clear();
		c2.clear();
		
		return greater_date;
		
	}

}		