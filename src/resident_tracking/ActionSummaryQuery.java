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
import org.apache.log4j.Logger;

public class ActionSummaryQuery extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionSummaryQuery");
				 
	public ActionSummaryQuery() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		return ds.getConnection();
		
	  }
	
	private List<String> doQuery(Connection c, String sql, String arg1, String arg2, int academic_year){
		
		List<String> l = new ArrayList<String>();
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		try {
		
			ps = c.prepareStatement(sql);  
		
			if(arg1!=null){
				ps.setString(1, arg1);	
				ps.setInt(2, academic_year);
			}
			
			if (arg2!=null){
				l.add(arg2);
			}
		
			rs = ps.executeQuery();
  						
			while (rs.next()){								
				l.add(rs.getString(1));					
			}			
		}
		catch (SQLException sqle){
			log.error(sqle.getMessage());
		}
		catch (Exception e){
	  		log.error("Exception in doQuery() method of ActionUserMenus.");
	  		
	  	}
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
										
		} 
		return l;
		
	}
		
	private boolean checkUserQuery(Connection conn, FormBean fb, HttpServletRequest request, HeaderBean hb){
			
		String resident=null;
		List<String> dataSet1 = new ArrayList<String>();
		List<String> dataSet2 = new ArrayList<String>();
		List<String> dataSet3 = new ArrayList<String>();
		String refresh=null;
		Iterator<String> itr;
		String valid_program=null;
		boolean validate_name = false;
		boolean resident_found=false;
		boolean rc=true;
		String start_date_check=null;
		String end_date_check=null;
		int greater_date=0;
		String all_residents=null;
		int academic_year = hb.getAcademicYearKey();
		
		String valid_resident_sql 	= 	"select resident "+
										"from rt_resident_master "+
										"where department = ? " +
										"and   academic_year_key = ? "+
										"order by 1 asc";
		
		String valid_program_sql 	= 	"select department " +
										"from rt_resident_master " +
										"where resident = ? " +
										"and   academic_year_key = ?";
		
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
			
			if (hb.getAdministrator()==true){
				
				all_residents = "All Residents";
			}
			else{
				all_residents = "All My Residents";
			}
					
			if ((fb.getProgram().equalsIgnoreCase("all my programs")) ||
				(fb.getProgram().equalsIgnoreCase("Mount Sinai - All Programs"))){
				
					validate_name=false;
					
			}
			else{
				
				if ((fb.getResident().equalsIgnoreCase("all my residents"))  ||
					(fb.getResident().equalsIgnoreCase("All Residents"))){
					
						validate_name=false;
				}
				else{
						validate_name=true;
				}
			}
			
			dataSet1=doQuery(conn,valid_resident_sql,fb.getProgram(),all_residents,academic_year);
			
			itr=dataSet1.iterator();
			
			while (itr.hasNext()){

				resident=itr.next().toString();
						
				if (resident.equalsIgnoreCase(fb.getResident())){
					resident_found=true;
				}
			}
			
			request.setAttribute("VALID_RESIDENTS",dataSet1);
				
			if(rc){	
				
				
				if (validate_name && !resident_found){
				
					fb.setMessage1("The resident you selected does not belong to the "+fb.getProgram()+" program.");
					fb.setMessage2("Please choose a valid resident from the list now, and submit again.");	
				
				
					dataSet2=doQuery(conn,valid_program_sql,fb.getResident(),null,academic_year);
					itr=dataSet2.iterator();
				
					if (itr.hasNext()){
						valid_program=itr.next().toString();
						fb.setMessage3(fb.getResident()+" is doing a rotation in the "+valid_program+" program.");
					}
					else{
						fb.setMessage3(null);
					}
								
					rc=false;
				}
				else{
					
					fb.setMessage1(null);
					fb.setMessage2(null);
					fb.setMessage3(null);
					
				}
				
			}
			
			if(rc){
				
				dataSet3=doQuery(conn,refresh_sql,null,null,academic_year);
				itr=dataSet3.iterator();
				
				if (itr.hasNext()){
					refresh=itr.next().toString();
				}
				
				if (refresh.equalsIgnoreCase("t")){
							
					fb.setMessage1("Resident Tracking is being refreshed with new data.");
					fb.setMessage2("Please try your query again after 9 AM.");
					rc=false;
								
				}	
					
			}
	
		}
		catch (Exception e){
	  		
			log.error("Exception in checkUserQuery().");
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
				
		HttpSession session = request.getSession(true);
		HeaderBean hb=null;
		RequestDispatcher dispatcher = request.getRequestDispatcher("summary_result.jsp");
		boolean normal_mode=false;
		boolean expired=false;
		boolean drilldown_mode=false;
		System.out.println("Hello - ActionSummaryQuery");
		
		if (null==session.getAttribute("HEADER_SECTION")){
			expired=true;
			log.info("redirecting an expired session.");	
			dispatcher = request.getRequestDispatcher("timeout.jsp");
			dispatcher.forward(request, response);
		}
		else{
			hb = (HeaderBean)session.getAttribute("HEADER_SECTION");
		}
		
		if (!expired){
		
			if (request.getParameter("DrillDownSelect")==null){
				normal_mode=true;
			}
			else{
				drilldown_mode=true;
				hb.setResident(request.getParameter("DrillDownSelect"));
				dispatcher = request.getRequestDispatcher("detail.jsp");
				dispatcher.forward(request, response);	
			}
		
		}
		
		if (normal_mode){
		
			Connection c=null; 
			PreparedStatement ps=null;
			FormBean fb = new FormBean();
			fb.setStartDate(request.getParameter("startDate"));
			fb.setEndDate(request.getParameter("endDate"));
			fb.setResident(request.getParameter("menuResident"));
			fb.setFilter(request.getParameter("menuFilter"));
			fb.setSortOrder(request.getParameter("menuSort"));
			fb.setProgram(request.getParameter("menuProgram"));
			String usr=hb.getUserName();
			boolean VALID_MENU_CHOICES=false;
			boolean set_rotation=false;
			String sql=null;
			ResultSet rs=null;
			String SORT_ORDER=null;
			String ROTATION_PREDICATE=null;
			List dataSet = new ArrayList();
			String RESIDENT_PREDICATE=null;
			String[] sql_parameters = new String[20];
			ResidentDuty rd = new ResidentDuty();
			int record_count=0;
			List <ResidentDuty> dutyList = new ArrayList <ResidentDuty>();
			Iterator iterator=null;
			String academic_year=null;
			tdsComparatorAsc tdsSortAsc = new tdsComparatorAsc(); 
			tdsComparatorDesc tdsSortDesc = new tdsComparatorDesc();
			tdsComparatorDaysAsc tdsDaysAsc = new tdsComparatorDaysAsc();
			tdsComparatorDaysDesc tdsDaysDesc = new tdsComparatorDaysDesc();
			orComparatorAsc orSortAsc = new orComparatorAsc();
			orComparatorDesc orSortDesc = new orComparatorDesc();
			orComparatorDaysAsc orDaysAsc = new orComparatorDaysAsc();
			orComparatorDaysDesc orDaysDesc = new orComparatorDaysDesc();
			allComparatorAsc allSortAsc = new allComparatorAsc();
			allComparatorDesc allSortDesc = new allComparatorDesc();
			allComparatorDaysAsc allDaysAsc = new allComparatorDaysAsc();
			allComparatorDaysDesc allDaysDesc = new allComparatorDaysDesc();
			residentComparator residentSort = new residentComparator();
		
			log.debug(usr+" Q: SD="+fb.getStartDate()+";ED="+fb.getEndDate()+";R="+fb.getResident()+";F="+fb.getFilter()+";S="+fb.getSortOrder()+";P="+fb.getProgram());
		
			/** Oracle Queries
			
		String SQL_PART_A = 		"select rm.resident,rpt.application,rm.department,"+
				       				"sum(rpt.tds_exception),"+ 
				       				"sum(rpt.hsm_exception),"+ 
				       				"decode(sum(rpt.ni_count),null,0,sum(rpt.ni_count)),"+
				       				"count(distinct to_char(event_date,'mm/dd/yyyy')),"+
				       				"count(*) "+
				       				"from   rt_report_table rpt,"+     
				       				"rt_resident_master rm,"+     
				       				"rt_rotation ro,"+     
				       				"rt_authorization a "+ 
				       				"where  rpt.dictation_code = rm.identifier "+ 
				       				"and    rm.department = ro.rotation "+
				       				"and    ro.rotation_key = a.rotation_key "+ 
				       				"and    a.username = ? "+
				       				"and    rpt.event_date between to_date(?,'MM/DD/YYYY') "+
				       				"and    to_date(?,'MM/DD/YYYY hh24:mi:ss') "+  
				       				"and   (exception_flag = 'Y' or (ni_count = 1)) " +
				       				"and    rpt.academic_year_key = ? " +
				       				"and    rm.academic_year_key  = ? " +
				       				"and    ro.academic_year_key  = ? " +
				       				"and    a.academic_year_key   = ? ";
		
		String SQL_PART_B = 		"select rm.resident,'TOTAL',rm.department,0,0,0,"+ 
				       				"count(distinct to_char(event_date,'mm/dd/yyyy')),count(*) "+
				       				"from   rt_report_table rpt,"+     
				       				"rt_resident_master rm,"+     
				       				"rt_rotation ro,"+     
				       				"rt_authorization a "+ 
				       				"where  rpt.dictation_code = rm.identifier "+ 
				       				"and    rm.department = ro.rotation "+ 
				       				"and    ro.rotation_key = a.rotation_key "+ 
				       				"and    a.username = ? "+
				       				"and    rpt.event_date between to_date(?,'MM/DD/YYYY') "+
				       				"and    to_date(?,'MM/DD/YYYY hh24:mi:ss') "+ 
				       				"and    rpt.application in ('TDS','HSM') "+
				       				"and    exception_flag = 'Y' "+
				       				"and    rpt.academic_year_key = ? " +
				       				"and    rm.academic_year_key  = ? " +
				       				"and    ro.academic_year_key  = ? " +
				       				"and    a.academic_year_key   = ? ";
			
		**/
			
		/** MySQL queries **/	
		String SQL_PART_A =			"select rm.resident"+
									",rpt.application"+
									",rm.department"+
									",sum(rpt.tds_exception)"+
									",sum(rpt.hsm_exception)"+
									",sum(rpt.ni_count)"+
									",count(distinct(left(event_date,9)))"+
									",count(*) "+ 
									"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
									"join rt_rotation ro on (ro.rotation = rm.department) "+
									"join rt_authorization a on (a.rotation_key = ro.rotation_key) "+
									"where a.username = ? "+ 
									"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y %H:%i') and str_to_date(?,'%m/%d/%Y %H:%i') "+
									"and (exception_flag = 'Y' or (ni_count = 1)) "+ 
									"and rpt.academic_year_key = ? "+ 
									"and rm.academic_year_key  = ? "+ 
									"and ro.academic_year_key  = ? "+
									"and a.academic_year_key   = ? "; 
//									"and rm.resident = '?' "+
//									"and ro.rotation = '?' "+
//									"group by rm.resident,rpt.application,rm.department";
		
		String SQL_PART_B =			"select rm.resident,'TOTAL',rm.department,0,0,0,count(distinct(left(event_date,9))),count(*) "+ 
									"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
									"join rt_rotation ro on (ro.rotation = rm.department) "+
									"join rt_authorization a on (a.rotation_key = ro.rotation_key) "+
									"where  a.username = ? "+
									"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y %H:%i') and str_to_date(?,'%m/%d/%Y %H:%i') "+
									"and rpt.application in ('TDS','HSM') "+ 
									"and exception_flag = 'Y' "+
									"and rpt.academic_year_key = ? "+ 
									"and rm.academic_year_key  = ? "+
									"and ro.academic_year_key  = ? "+
									"and a.academic_year_key   = ? ";
//									"and rm.resident = '?' "+ 
//									"and ro.rotation = '?' "+
//									"group by rm.resident,rm.department order by 1,2";
	
			if (hb.getAdministrator()==true){
				
				RESIDENT_PREDICATE = (fb.getResident().equalsIgnoreCase("all residents")) ? "":"and rm.resident = ? ";
		
				if (fb.getProgram().equalsIgnoreCase("mount sinai - all programs")){
					ROTATION_PREDICATE="";
				}
				else{
					ROTATION_PREDICATE = "and ro.rotation = ? ";
					set_rotation=true;
				}
			}
			else{
			
				RESIDENT_PREDICATE = (fb.getResident().equalsIgnoreCase("all my residents")) ? "":"and rm.resident = ? ";
			
				if (fb.getProgram().equalsIgnoreCase("all my programs")){
					ROTATION_PREDICATE="";
				}
				else{
					ROTATION_PREDICATE = "and ro.rotation = ? ";
					set_rotation=true;
				}
			}
			
			String PART_A_GROUP_BY = "group by rm.resident,rpt.application,rm.department ";
			String PART_B_GROUP_BY = "group by rm.resident,rm.department ";
			SORT_ORDER      	   = "order by 1,2";
	
			sql = 	SQL_PART_A+RESIDENT_PREDICATE+ROTATION_PREDICATE+PART_A_GROUP_BY+
					"UNION ALL "+
					SQL_PART_B+RESIDENT_PREDICATE+ROTATION_PREDICATE+PART_B_GROUP_BY+SORT_ORDER;
						
			log.debug("SQL: "+sql.replaceAll("'", ""));	
			
			try {	
			
				c = getConnection(); 
			
				VALID_MENU_CHOICES=checkUserQuery(c,fb,request,hb);
									
				request.setAttribute("QUERYPARM", fb);
					
				if (VALID_MENU_CHOICES){
					
					log.debug("Query is valid");
							
					String endDate=fb.getEndDate()+" 23:59:59";
				
					//save parameters to session for use in drilldown function
					hb.setStartDate(fb.getStartDate());
					hb.setEndDate(fb.getEndDate());
					hb.setFilter(fb.getFilter());
					
					ps = c.prepareStatement(sql);
					
					System.out.println(sql);
					
					for (int z=0;z<sql_parameters.length;z++){
						sql_parameters[z]=null;
					}
					
					academic_year = Integer.toString(hb.getAcademicYearKey());
					
					sql_parameters[0]=hb.getUserName();
					sql_parameters[1]=hb.getStartDate();
					sql_parameters[2]=endDate;
					sql_parameters[3]=academic_year;
					sql_parameters[4]=academic_year;
					sql_parameters[5]=academic_year;
					sql_parameters[6]=academic_year;
							
					if ((!fb.getResident().equalsIgnoreCase("all my residents")) &&
						(!fb.getResident().equalsIgnoreCase("All Residents"))){
						
						sql_parameters[7]=fb.getResident();
						
						if (set_rotation){
							sql_parameters[8]=fb.getProgram();
							sql_parameters[9]=hb.getUserName();
							sql_parameters[10]=hb.getStartDate();
							sql_parameters[11]=endDate;
							sql_parameters[12]=academic_year;
							sql_parameters[13]=academic_year;
							sql_parameters[14]=academic_year;
							sql_parameters[15]=academic_year;
							sql_parameters[16]=fb.getResident();
							sql_parameters[17]=fb.getProgram();
							
						}
						else{
							sql_parameters[8]=hb.getUserName();
							sql_parameters[9]=hb.getStartDate();
							sql_parameters[10]=endDate;
							sql_parameters[11]=academic_year;
							sql_parameters[12]=academic_year;
							sql_parameters[13]=academic_year;
							sql_parameters[14]=academic_year;
							sql_parameters[15]=fb.getResident();
						}
					}
					else if (set_rotation){
						sql_parameters[7]=fb.getProgram();
						sql_parameters[8]=hb.getUserName();
						sql_parameters[9]=hb.getStartDate();
						sql_parameters[10]=endDate;
						sql_parameters[11]=academic_year;
						sql_parameters[12]=academic_year;
						sql_parameters[13]=academic_year;
						sql_parameters[14]=academic_year;
						sql_parameters[15]=fb.getProgram();
					}
					else{
						sql_parameters[7]=hb.getUserName();
						sql_parameters[8]=hb.getStartDate();
						sql_parameters[9]=endDate;
						sql_parameters[10]=academic_year;
						sql_parameters[11]=academic_year;
						sql_parameters[12]=academic_year;
						sql_parameters[13]=academic_year;
					}
			
					
					for (int z=0;z<sql_parameters.length;z++){
						
						if (sql_parameters[z] != null){
							ps.setString(z+1, sql_parameters[z]);							
						}	
					}
					
					rs = ps.executeQuery();
					
					String resident_break=null;
					
					while (rs.next()){
						
						String resident = rs.getString(1);
						String application = rs.getString(2);
						String program = rs.getString(3);
						record_count++;
						
						if (!resident.equalsIgnoreCase(resident_break)){
							
							if (resident_break != null){
								dutyList.add(rd);
							}
							
							rd = new ResidentDuty();
							rd.setResident(resident);
							rd.setProgram(program);
							resident_break=resident;
							
						}
				
						if (application.equalsIgnoreCase("tds")){
							
							rd.setTtlTdsEx(rs.getInt(4));
							rd.setTtlTdsDays(rs.getInt(7));
								
						}
						
						if (application.equalsIgnoreCase("hsm")){
							
							rd.setTtlOrEx(rs.getInt(5));
							rd.setTtlOrDays(rs.getInt(7));
								
						}
						
						if (application.equalsIgnoreCase("new innovations")){
							
							rd.setReportedTime(true);
								
						}
						
						if (application.equalsIgnoreCase("total")){
							
							rd.setTtlEx(rs.getInt(8));
							rd.setTtlExDays(rs.getInt(7));
								
						}
						
					}
					
					if (record_count > 0){	
						dutyList.add(rd);
					}
											
					if (fb.getSortOrder().equalsIgnoreCase("tds exceptions asc")){			
						Collections.sort(dutyList,tdsSortAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("tds exceptions desc")){
						Collections.sort(dutyList,tdsSortDesc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("or exceptions asc")){			
						Collections.sort(dutyList,orSortAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("or exceptions desc")){
						Collections.sort(dutyList,orSortDesc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("total exceptions asc")){			
						Collections.sort(dutyList,allSortAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("total exceptions desc")){
						Collections.sort(dutyList,allSortDesc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("total exceptions desc")){
						Collections.sort(dutyList,allSortDesc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("resident name")){
						Collections.sort(dutyList,residentSort);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("tds exception days asc")){
						Collections.sort(dutyList,tdsDaysAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("tds exception days desc")){
						Collections.sort(dutyList,tdsDaysDesc);
					} 
					else if (fb.getSortOrder().equalsIgnoreCase("or exception days asc")){
						Collections.sort(dutyList,orDaysAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("or exception days desc")){
						Collections.sort(dutyList,orDaysDesc);
					} 		
					else if (fb.getSortOrder().equalsIgnoreCase("total exception days asc")){
						Collections.sort(dutyList,allDaysAsc);
					}
					else if (fb.getSortOrder().equalsIgnoreCase("total exception days desc")){
						Collections.sort(dutyList,allDaysDesc);
					} 		
					
					for (iterator=dutyList.iterator(); iterator.hasNext();){
					
						ResidentDuty record  = (ResidentDuty)iterator.next();
						
						if (fb.getFilter().equalsIgnoreCase("residents with exceptions")){
						
							if (record.getTtlEx() > 0){
						
								dataSet.add(record.getResident());
								dataSet.add(record.getTDSDisplay());
								dataSet.add(record.getORDisplay());
								dataSet.add(record.getReportedTime());
								dataSet.add(record.getTOTALDisplay());
								dataSet.add(record.getProgram());
							}
						}
						else if (fb.getFilter().equalsIgnoreCase("residents with tds exceptions")){
							
							if (record.getTtlTdsEx() > 0){
						
								dataSet.add(record.getResident());
								dataSet.add(record.getTDSDisplay());
								dataSet.add(record.getORDisplay());
								dataSet.add(record.getReportedTime());
								dataSet.add(record.getTOTALDisplay());
								dataSet.add(record.getProgram());
							}
						}
						else if (fb.getFilter().equalsIgnoreCase("residents with or exceptions")){
							
							if (record.getTtlOrEx() > 0){
						
								dataSet.add(record.getResident());
								dataSet.add(record.getTDSDisplay());
								dataSet.add(record.getORDisplay());
								dataSet.add(record.getReportedTime());
								dataSet.add(record.getTOTALDisplay());
								dataSet.add(record.getProgram());
							}
						}
						else if (fb.getFilter().equalsIgnoreCase("residents with reported time")){
							
							if (record.getReportedTime()){
						
								dataSet.add(record.getResident());
								dataSet.add(record.getTDSDisplay());
								dataSet.add(record.getORDisplay());
								dataSet.add(record.getReportedTime());
								dataSet.add(record.getTOTALDisplay());
								dataSet.add(record.getProgram());
							}
						}
						else{
							
							dataSet.add(record.getResident());
							dataSet.add(record.getTDSDisplay());
							dataSet.add(record.getORDisplay());
							dataSet.add(record.getReportedTime());
							dataSet.add(record.getTOTALDisplay());
							dataSet.add(record.getProgram());
												
						}
						
					}
						
						session.setAttribute("DATASET", dataSet);
					
				}
					
				dispatcher.forward(request, response);		 
			
			}
			catch (SQLException sqle){
				log.error(sqle.getMessage());
				System.out.println(sqle.getMessage());
			}
			catch (Exception e){
				log.error("Exception in doPost().");
			} 
			finally {
				JDBCManager.closePreparedStatement(ps);
				JDBCManager.closeResult(rs);
				JDBCManager.closeConnection(c);			
			} 	
		
		}// end-if normal mode
	}
   
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
