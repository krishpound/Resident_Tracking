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
import org.apache.log4j.Logger;

public class ActionMaintainResident extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionMaintainResident");
					 
	public ActionMaintainResident() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		return ds.getConnection();
		
	  }
	
	private boolean checkUserQuery(Connection conn, FormBean fb, HttpServletRequest request){
			
		boolean rc=true;
							
		try {
	
			if(fb.getDictationID()==null || fb.getDictationID().trim().length()==0){
				fb.setMessage1("Please enter a value in the Dictation ID field.");
				fb.setSqlStatus(false);
				rc=false;
			}	
					
			if(rc){
				
				if(fb.getLastName()==null || fb.getLastName().trim().length()==0){
					fb.setMessage1("Please enter a value in the Last Name field.");
					fb.setSqlStatus(false);
					rc=false;
				}
				
			}
			
			if(rc){
				
				if(fb.getFirstName()==null || fb.getFirstName().trim().length()==0){
					fb.setMessage1("Please enter a value in the First Name field.");
					fb.setSqlStatus(false);
					rc=false;
				}
				
			}
									
		}
		
		
		catch (Exception e){
	  		
			log.error("Exception in checkUserQuery()");
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
		Connection c=null; 
		PreparedStatement ps=null;
		FormBean fb = new FormBean();
		RequestDispatcher dispatcher = request.getRequestDispatcher("maintainResidents_result.jsp");
		boolean expired=false;
		
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
		
			if (hb.getAdministrator()){
			
				fb.setDictationID(request.getParameter("dictationID"));
				fb.setLastName(request.getParameter("lastName"));
				fb.setFirstName(request.getParameter("firstName"));
				fb.setProgram(request.getParameter("programList"));
				fb.setStatus(request.getParameter("statusList"));
				fb.setSite(request.getParameter("siteList"));
				fb.setSqlAction(request.getParameter("sqlAction"));
				fb.setSqlStatus(true);
				boolean VALID_MENU_CHOICES=false;
				String sql=null;
				String usr=hb.getUserName();
				String resident = fb.getLastName()+", "+fb.getFirstName();
				int rows_updated=0;
				ActionQueryMenus aqm = new ActionQueryMenus();
		
				log.info(usr+" Q: U="+fb.getDictationID()+";LN="+fb.getLastName()+";FN="+fb.getFirstName()+";PGM="+fb.getProgram()+";STS="+fb.getStatus()+"S="+fb.getSite()+"CMD="+fb.getSqlAction());
				
				String SQL_ADD =	"insert into rt_resident_master "+
									"(last_name" +
									",first_name" +
									",department" +
									",identifier" +
									",identifier_type"+
									",employer" +
									",status" +
									",resident" +
									",academic_year_key) "+
									"values (?,?,?,?,?,?,?,?,?)";
				
				String SQL_DELETE = "delete from rt_resident_master " +
									"where identifier = ? "+
									"and   last_name  = ? "+
									"and   first_name = ? "+
									"and   department = ? "+
									"and   employer   = ? " +
									"and   academic_year_key = ?";
				
				try {	
				
					c = getConnection(); 
			
					VALID_MENU_CHOICES=checkUserQuery(c,fb,request);
								
					log.debug("valid menu choices set to: "+VALID_MENU_CHOICES);
					request.setAttribute("MAINTAINRESIDENTPARM", fb);
					
					if (VALID_MENU_CHOICES){
					
						sql = (fb.getSqlAction().equalsIgnoreCase("add")) ? SQL_ADD:SQL_DELETE;
					
						log.debug("SQL: "+sql.replaceAll("'", ""));	
										
						ps = c.prepareStatement(sql);
					
						if (fb.getSqlAction().equalsIgnoreCase("add")){
						
							ps.setString(1, fb.getLastName());
							ps.setString(2, fb.getFirstName());
							ps.setString(3, fb.getProgram());
							ps.setString(4, fb.getDictationID());
							ps.setString(5, "Dictation Code");
							ps.setString(6, fb.getSite());
							ps.setString(7, fb.getStatus());
							ps.setString(8, resident);
							ps.setInt(9,hb.getAcademicYearKey());
							}
						
						if (fb.getSqlAction().equalsIgnoreCase("delete")){
							
							ps.setString(1, fb.getDictationID());
							ps.setString(2, fb.getLastName());
							ps.setString(3, fb.getFirstName());
							ps.setString(4, fb.getProgram());
							ps.setString(5, fb.getSite());
							ps.setInt(6, hb.getAcademicYearKey());
							
						}
													
						rows_updated = ps.executeUpdate();
						log.info(usr+" rows updated by "+fb.getSqlAction()+" command: "+rows_updated);
						fb.setRowsUpdated(rows_updated);
						
						session.setAttribute("ALL_RESIDENTS", aqm.doQuery(c,"ALL_RESIDENTS_QUERY",null,"All Residents",hb.getAcademicYearKey()));
			
					}	
			
				}
				catch (SQLException sqle){
					
					System.out.println("SQLException in ActionMaintainResident");
					
					log.error(sqle.getMessage());
					fb.setMessage1(sqle.getMessage());
					fb.setSqlStatus(false);
					
				}
				catch (Exception e){
					
					log.error("Exception in doPost()");	
				}
				finally {
					JDBCManager.closePreparedStatement(ps);
					JDBCManager.closeConnection(c);		
					dispatcher.forward(request, response);	
				} 
			
			}//administrator
			else{
				dispatcher = request.getRequestDispatcher("maintainResidents.jsp");
				dispatcher.forward(request, response);
			}	
		}//!expired
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
