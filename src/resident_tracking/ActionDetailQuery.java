package resident_tracking;

import java.io.IOException;
import java.sql.*;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class ActionDetailQuery extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionDetailQuery");
		
	public ActionDetailQuery() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		return ds.getConnection();
		
	  }
	
	public String fixTime(String dt){
		

		String date = dt.substring(0,10);
		String time = dt.substring(10,16);
		String MM=null;
		String DD=null;
		String YYYY=null;
		String separator="/";
		String tokens[];
		
		tokens = date.split("-");
		YYYY=tokens[0];
		MM=tokens[1];
		DD=tokens[2];
		
		date = MM+separator+DD+separator+YYYY+" "+time;
				
		return date;
		
	}
	
	private boolean doQuery(Connection c, String sql, String name, String startdate, String enddate, boolean refresh,int academic_year){
		
		int ni_count=0;
		boolean return_value=false;
		ResultSet rs=null;
		PreparedStatement ps=null;
		String init=null;
		String refreshing=null;
		
		try {
		
			ps = c.prepareStatement(sql);  
		
			if (!refresh){
				if(name!=null){
					ps.setString(1, name);	
				}
				if (startdate!=null){
					ps.setString(2, startdate);
				}
				if (enddate!=null){
					ps.setString(3, enddate);
				}
				ps.setInt(4, academic_year);
				ps.setInt(5, academic_year);
				
			}
			
			rs = ps.executeQuery();
			
			if (refresh){
			
				if (rs.next()){
					refreshing = rs.getString(1);
				}
				return_value = (refreshing.equalsIgnoreCase("t")) ? true:false; 
			}
			else {
				
				if (rs.next()){
					ni_count = rs.getInt(1);
				}
				return_value = (ni_count > 0) ? true:false; 
			}		
		}
		catch (SQLException sqle){
			log.error(sqle.getMessage());
		}
		catch (Exception e){
	  		log.error("Exception in doQuery().");
	  		
	  	}
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
										
		} 
		
		return return_value;
		
	}
		
	private boolean checkUserQuery(Connection conn, FormBean fb, HttpServletRequest request,boolean tabular,int academic_year){
		
		boolean rc=true;
		String start_date_check=null;
		String end_date_check=null;
		int greater_date=0;
		boolean reported_duty=false;
		boolean checkMonth=true;
		String endDate=null;
		boolean refreshing=false;
			
		String reported_duty_sql =  "select sum(rpt.ni_count) "+
									"from   rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+ 
									"where  rm.resident = ? "+
									"and    str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+
									"and    rpt.academic_year_key = ? "+ 
									"and    rm.academic_year_key = ?";
									
		String refresh_sql			=	"select status from rt_refresh";
		
		try {
	
			if(fb.getStartDate()==null || fb.getStartDate().trim().length()==0){
				fb.setMessage1("You must enter a value in the Start Date field.");
				rc=false;
			}	
			else{
				start_date_check=dateHelper.checkDate(fb.getStartDate());
				if(!start_date_check.equalsIgnoreCase("ok")){
					fb.setMessage1("The value entered for Start Date is invalid.");
					fb.setMessage2("Please use the calendar icon, or enter dates manually in this format: MM/DD/YYYY");
					fb.setMessage3("Example: 04/01/2008");
					rc=false;
				}
			}
			
			if(rc){
				if(fb.getEndDate()==null || fb.getEndDate().trim().length()==0){
					fb.setMessage1("You must enter a value in the End Date field.");
					rc=false;
				}	
				else{
					end_date_check=dateHelper.checkDate(fb.getEndDate());
					if(!end_date_check.equalsIgnoreCase("ok")){
						fb.setMessage1("The value entered in the End Date field is invalid.");
						fb.setMessage2("Please use the calendar icon, or enter dates manually in this format: MM/DD/YYYY");
						fb.setMessage3("Example: 04/01/2008");
						rc=false;		
					}
				}
			}
			
			
			if(rc){
					greater_date=dateHelper.compareDate(fb.getStartDate(), fb.getEndDate());
				
					/*
					 * 	compareDate method:
					 *  1) takes two date strings and tells you which one is chronologically greater
					 *  2) returns 0 if dates are the same
					 *  3) returns 1 if first date is greater than second date
					 *  4) returns 2 if second date is greater than first date
					 *  5) returns -1 if any kind of error happened in the method.
					 */
					
					
					switch (greater_date){
				
						case  0:  {break;} //dates are the same
						case  1:  {fb.setMessage1("The Start Date selection must be less than or equal to the End Date selection.");
							   		rc=false;break;}
						case  2:  {break;} //end date is greater than start date
						case -1:  {break;}
			
					}	
			
			}
			
			//allow maximum one month interval for graphics
			if(rc && !(tabular)){
				
				String rollDate = dateHelper.rollDate(fb.getStartDate(),"MONTH", 1);
				
				greater_date=dateHelper.compareDate(fb.getEndDate(), rollDate);
				
				switch (greater_date){
			
					case  0:  {break;} //dates are the same
					case  1:  {fb.setMessage1("Please limit the time interval to one month or less for graphical reports.");
						   		rc=false;break;}
					case  2:  {break;} //rollDate is greater than endDate
					case -1:  {break;}
		
				}	
				
			}
			
			if (rc && !(tabular)){
				
				if ((fb.getFilter().equalsIgnoreCase("approved duty hours and exceptions")) ||
					(fb.getFilter().equalsIgnoreCase("approved duty hours and all or and tds events"))){
					
					checkMonth = dateHelper.checkMonth(fb.getStartDate(), fb.getEndDate());
					
					if (!checkMonth){
						
						fb.setMessage1("The start and end dates for this graph must be within the same calendar month.");
						rc=false;
					}
					
				}
				
			}
			
			if(rc){
				
				refreshing=doQuery(conn,refresh_sql,null,null,null,true,academic_year);
				
				
				if (refreshing){
							
					fb.setMessage1("Resident Tracking is being refreshed with new data.");
					fb.setMessage2("Please try your query again after 9 AM.");
					rc=false;
								
				}	
					
			}
			
			if(rc){	
				
				endDate = fb.getEndDate()+" 23:59:59";
				reported_duty = doQuery(conn,reported_duty_sql,fb.getResident(),fb.getStartDate(),endDate,false,academic_year);
				fb.setReportedDuty(reported_duty);
			}
			
		}
		catch (Exception e){
	  		
			log.error("Exception in checkProgram().");
	  		rc=false;  	
	  		
	  	}
			
		return rc;
	
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request,response);
			
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		System.out.println("Hello from doPost method of ActionDetailQuery");
				
		HttpSession session = request.getSession(true);
		HeaderBean hb=null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("detail_result.jsp");
		boolean expired=false;
		int academic_year=0;
		
		if (null==session.getAttribute("HEADER_SECTION")){
			expired=true;
			log.info("redirecting an expired session.");	
			dispatcher = request.getRequestDispatcher("timeout.jsp");
			dispatcher.forward(request, response);
		}
		else{
			hb = (HeaderBean)session.getAttribute("HEADER_SECTION");
			academic_year = hb.getAcademicYearKey();
		}
		
		if (!expired){
		
			Connection c=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			String formatted_time=null;
			String tds_screen=null;
			String sql=null;
			List<String> dataSet = new ArrayList<String>();
			FormBean fb = new FormBean();
			fb.setStartDate(request.getParameter("startDate"));
			fb.setEndDate(request.getParameter("endDate"));
			fb.setResident(request.getParameter("menuResident"));
			fb.setFilter(request.getParameter("menuFilter"));
			fb.setSortOrder(request.getParameter("menuSort"));
			fb.setReportStyle(request.getParameter("menuReportStyle"));
			String usr=hb.getUserName();
			boolean VALID_MENU_CHOICES=false;
			String SORT_ORDER=null;
			String FILTER_PREDICATE=null;
			String endDate=null;
			boolean tabular=true;
			
			if (fb.getReportStyle().equalsIgnoreCase("graphical")){
				
				dispatcher = request.getRequestDispatcher("detail_graph.jsp");
				
				tabular=false;
				
				if (fb.getFilter().equalsIgnoreCase("exceptions only")){	
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("Exception Count");
					fb.setChartProducer("dataEX");
					fb.setChartHeight(400);
					fb.setChartTitle("All Exceptions for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("tds - all events")){
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("Screen Hits");
					fb.setChartProducer("dataTDS");
					fb.setChartHeight(400);
					fb.setChartTitle("TDS Usage for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("tds - exceptions")){
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("Screen Hits");
					fb.setChartProducer("dataTDS");
					fb.setChartHeight(400);
					fb.setChartTitle("TDS Exceptions for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("or - all events")){	
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("OR Event Count");
					fb.setChartProducer("dataHSM");
					fb.setChartHeight(400);
					fb.setChartTitle("OR Events for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("or - exceptions")){	
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("OR Exception Count");
					fb.setChartProducer("dataHSM");
					fb.setChartHeight(400);
					fb.setChartTitle("OR Exceptions for "+fb.getResident());
				}	
				else if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours only")){
					fb.setChartID("verticalbar");
					fb.setChartType("verticalbar");
					fb.setChartYLabel("Event Count");
					fb.setChartProducer("dataNI");
					fb.setChartHeight(400);
					fb.setChartTitle("Approved Duty Hours only for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and all OR and TDS events")){
					fb.setChartID("scatterAll");
					fb.setChartType("scatter");
					fb.setChartYLabel("24-Hour Time");
					fb.setChartProducer("dataALL");
					fb.setChartHeight(600);
					fb.setChartTitle("Approved Duty Hours and all OR and TDS events for "+fb.getResident());
				}
				
				else if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and Exceptions")){
					fb.setChartID("scatterEx");
					fb.setChartType("scatter");
					fb.setChartYLabel("24-Hour Time");
					fb.setChartProducer("dataALL");
					fb.setChartHeight(600);
					fb.setChartTitle("Approved Duty Hours and Exceptions for "+fb.getResident());
				}
								
			}
			
			if (fb.getReportStyle().equalsIgnoreCase("tabular")){
				
				if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and all OR and TDS events")){	
					fb.setChartTitle("Approved Duty Hours and all OR and TDS events for "+fb.getResident());
				}
				if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and Exceptions")){	
					fb.setChartTitle("Approved Duty Hours and Exceptions for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours only")){	
					fb.setChartTitle("Approved Duty Hours only for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("tds - all events")){	
					fb.setChartTitle("TDS Usage for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("tds - exceptions")){	
					fb.setChartTitle("TDS Exceptions for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("or - all events")){	
					fb.setChartTitle("OR Events for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("or - exceptions")){	
					fb.setChartTitle("OR Exceptions for "+fb.getResident());
				}
				else if (fb.getFilter().equalsIgnoreCase("exceptions only")){	
					fb.setChartTitle("All Exceptions for "+fb.getResident());
				}
			}
			
			/*** Oracle Query
			
			String DETAIL_QUERY =			"select " +
											"rpt.application," +
											"rpt.event," +
											"rpt.event_date," +
											"rpt.tds_screen," +
											"rpt.duty_type," +
											"rpt.status," +
											"rpt.exception_flag "+
											"from rt_report_table rpt,"+ 
											"rt_resident_master rm "+
											"where rpt.dictation_code = rm.identifier "+
											"and   rm.resident = ? " +
											"and   rpt.event_date between to_date(?,'MM/DD/YYYY') " +
											"and   to_date(?,'MM/DD/YYYY hh24:mi:ss') " +
											"and   rpt.academic_year_key = ? " +
											"and   rm.academic_year_key  = ? ";	
			
			***/
			
			/*** MySQL Query ***/
			
			String DETAIL_QUERY =			"select "+ 
											"rpt.application,"+
											"rpt.event,"+
											"str_to_date(rpt.event_date,'%m/%d/%Y %H:%i'),"+
											"rpt.tds_screen,"+
											"rpt.duty_type,"+
											"rpt.status,"+ 
											"rpt.exception_flag "+ 
											"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
											"where rm.resident = ? "+
											"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+ 
											"and   rpt.academic_year_key = ? "+ 
											"and   rm.academic_year_key  = ? ";
					
			String ORDER_CLAUSE	= 			" order by 3 ";
		
			log.debug(usr+" Q: SD="+fb.getStartDate()+";ED="+fb.getEndDate()+";R="+fb.getResident()+";F="+fb.getFilter()+";S="+fb.getSortOrder());
				
			if (fb.getSortOrder().equalsIgnoreCase("date ascending")){
				SORT_ORDER="asc, 2";			
			}
			else{
				SORT_ORDER="desc, 2";
			} 
			if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and all OR and TDS events")){
				FILTER_PREDICATE="";
			}
			if (fb.getFilter().equalsIgnoreCase("Approved Duty Hours and Exceptions")){
				FILTER_PREDICATE=	"and   ((application in ('TDS','HSM') and rpt.exception_flag = 'Y') "+
									"or application = 'New Innovations') ";
			}
			else if (fb.getFilter().equalsIgnoreCase("approved duty hours only")){
				FILTER_PREDICATE=" and application = 'New Innovations' ";
			}
			else if (fb.getFilter().equalsIgnoreCase("tds - exceptions")){
				FILTER_PREDICATE=" and application='TDS' and exception_flag = 'Y' ";
			}
			else if (fb.getFilter().equalsIgnoreCase("tds - all events")){
				FILTER_PREDICATE=" and application='TDS' ";
			}
			else if (fb.getFilter().equalsIgnoreCase("or - all events")){
				FILTER_PREDICATE=" and application='HSM' ";
			}
			else if (fb.getFilter().equalsIgnoreCase("or - exceptions")){
				FILTER_PREDICATE=" and application='HSM' and exception_flag = 'Y' ";
			}
			else if (fb.getFilter().equalsIgnoreCase("exceptions only")){
						FILTER_PREDICATE=" and exception_flag='Y' ";
			}
			
			try {
			
				c = getConnection();
			
				VALID_MENU_CHOICES=checkUserQuery(c,fb,request,tabular,academic_year);
		
				request.setAttribute("QUERYPARM", fb);
		
				if(VALID_MENU_CHOICES && tabular){
					
					
//					System.out.println("Valid menu choices for a tabular detail report");
					
		
					sql = DETAIL_QUERY+FILTER_PREDICATE+ORDER_CLAUSE+SORT_ORDER;
					
					
					
					
					log.debug("SQL: "+sql.replaceAll("'", ""));	
					
					endDate=fb.getEndDate()+" 23:59:59";
		
//					System.out.println("tabular detail query: "+sql);
					
					ps = c.prepareStatement(sql);
					ps.setString(1, fb.getResident());
					ps.setString(2, fb.getStartDate());
					ps.setString(3, endDate);
					ps.setInt(4, academic_year);
					ps.setInt(5, academic_year);
					
					ps.setMaxRows(10000);
					rs = ps.executeQuery();
					
					while (rs.next()){
	
						formatted_time = fixTime(rs.getString(3));
						
						tds_screen = rs.getString(4);
						tds_screen = (null==tds_screen || 0==tds_screen.trim().length()) ? "&nbsp;":tds_screen;
						dataSet.add(rs.getString(1));
						dataSet.add(rs.getString(2));
						dataSet.add(formatted_time);
//						dataSet.add(rs.getString(3));
						dataSet.add(tds_screen);
						dataSet.add(rs.getString(5));
						dataSet.add(rs.getString(6));
						dataSet.add(rs.getString(7));
		       
					}
 				
					session.setAttribute("DETAILSET", dataSet);
											  		
				}
			}
			catch (SQLException sqle){
				log.error(sqle.getMessage());
			}
			catch (Exception e){
				log.error("Exception in doPost().");	
			}
			finally {
			
				JDBCManager.closePreparedStatement(ps);
				JDBCManager.closeResult(rs);
				JDBCManager.closeConnection(c);
							
			} 
		}//!expired	
		
		if (response.isCommitted()){
		
			log.debug("the response was committed, so flushing buffer before forward.");
			response.flushBuffer();
			
		}
		else{
			
			log.debug("response has not yet been committed.");
		}
		dispatcher.forward(request, response);
		
	}//doPost()
		

   
	public void init() throws ServletException {
		
		try {
			ds=JDBCManager.init();
		}
		catch (ServletException se){
			se.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
