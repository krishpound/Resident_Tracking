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

public class ActionMaintainProgram extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionMaintainProgram");
					 
	public ActionMaintainProgram() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		return ds.getConnection();
		
	  }
	
	private boolean checkUserQuery(Connection conn, FormBean fb, HttpServletRequest request){
			
		String prefix=null;
		
		boolean rc=true;
							
		try {
	
			if(fb.getProgram()==null || fb.getProgram().trim().length()==0){
				fb.setMessage1("Please enter a program name.");
				fb.setSqlStatus(false);
				rc=false;
			}	
				
			if (rc){
				
				if (fb.getProgram().length() < 14){
						fb.setMessage1("Program names should be prefixed with Mount Sinai - ");
						fb.setSqlStatus(false);
						log.debug("program name length < 14 characters");
						rc=false;
				}
				
			}
			
			if (rc){
				
				prefix = fb.getProgram().substring(0,14);
								
				if (!prefix.equals("Mount Sinai - ")){
					fb.setMessage1("Program names should be prefixed with Mount Sinai - ");
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
		ResultSet rs=null;
		FormBean fb = new FormBean();
		ActionQueryMenus aqm = new ActionQueryMenus();
		RequestDispatcher dispatcher = request.getRequestDispatcher("maintainPrograms_result.jsp");
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
			
				fb.setProgram(request.getParameter("program"));
				fb.setAccredited(request.getParameter("accredited"));
				fb.setSite(request.getParameter("siteList"));
				fb.setSqlAction(request.getParameter("sqlAction"));
				fb.setSqlStatus(true);
				boolean VALID_MENU_CHOICES=false;
				String sql=null;
				String usr=hb.getUserName();
				String resident = fb.getLastName()+", "+fb.getFirstName();
				int rows_updated=0;
				int program_sequence=0;
				boolean sql_ADD=true;
		
				String SQL_SEQ =		"select rt_program_seq.nextval from dual";
				
				String SQL_ADD_DPT =	"insert into rt_department "+
									 	"(department_key"+
									 	",department" +
									 	",academic_year_key) "+
									 	"values (?,?,?)";
				
				String SQL_ADD_RTN = 	"insert into rt_rotation "+
										"(rotation_key"+
										",parent_key"+
										",child_key"+
										",department_key"+
										",site_key"+
										",accredited"+
										",rotation" +
										",academic_year_key) "+
										"values(?,?,?,?,?,?,?,?)";
				
				String SQL_DEL_DPT =	"delete from rt_department where department = ? and academic_year_key = ?";
				String SQL_DEL_RTN = 	"delete from rt_rotation where rotation = ? and academic_year_key = ?";
				
				
				try {	
				
					c = getConnection(); 
			
					VALID_MENU_CHOICES=checkUserQuery(c,fb,request);
								
					log.debug("valid menu choices set to: "+VALID_MENU_CHOICES);
					request.setAttribute("MAINTAINPROGRAMPARM", fb);
					
					
					if (VALID_MENU_CHOICES){
					
						sql_ADD = (fb.getSqlAction().equalsIgnoreCase("add")) ? true:false;
					
						if(sql_ADD){
							
							ps = c.prepareStatement(SQL_SEQ);
							
							rs = ps.executeQuery();
							
							if (rs != null){
								rs.next();
								program_sequence = rs.getInt(1);
								
							}
							
							ps = c.prepareStatement(SQL_ADD_DPT);
							ps.setInt(1,program_sequence);
							ps.setString(2,fb.getProgram());
							ps.setInt(3, hb.getAcademicYearKey());
							ps.execute();
							
							ps = c.prepareStatement(SQL_ADD_RTN);
							ps.setInt(1,program_sequence);
							ps.setInt(2,1);
							ps.setInt(3,0);
							ps.setInt(4,program_sequence);
							ps.setInt(5,1);
							ps.setString(6,fb.getAccredited());
							ps.setString(7,fb.getProgram());
							ps.setInt(8,hb.getAcademicYearKey());
							rows_updated=ps.executeUpdate();
							fb.setRowsUpdated(rows_updated);
							
							session.setAttribute("ALL_PROGRAMS", aqm.doQuery(c,"ALL_PROGRAMS_QUERY",null,"Mount Sinai - All Programs",hb.getAcademicYearKey()));
							
							
						}
						else{
							
							ps = c.prepareStatement(SQL_DEL_DPT);
							ps.setString(1,fb.getProgram());
							ps.setInt(2, hb.getAcademicYearKey());
							ps.execute();
							
							ps = c.prepareStatement(SQL_DEL_RTN);
							ps.setString(1,fb.getProgram());
							ps.setInt(2, hb.getAcademicYearKey());
							rows_updated=ps.executeUpdate();
							fb.setRowsUpdated(rows_updated);
							
							session.setAttribute("ALL_PROGRAMS", aqm.doQuery(c,"ALL_PROGRAMS_QUERY",null,"Mount Sinai - All Programs",hb.getAcademicYearKey()));
										
						}
					
					}	
			
				}
				catch (SQLException sqle){
				
					log.error(sqle.getMessage());
					fb.setMessage1(sqle.getMessage());
					fb.setSqlStatus(false);
					
				}
				catch (Exception e){
					
					log.error("Exception in doPost()");	
				}
				finally {
					JDBCManager.closeResult(rs);
					JDBCManager.closePreparedStatement(ps);
					JDBCManager.closeConnection(c);		
					dispatcher.forward(request, response);	
				} 
			
			}//administrator
			else{
				dispatcher = request.getRequestDispatcher("maintainPrograms.jsp");
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