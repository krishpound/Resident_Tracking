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

public class ActionChangeYear extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionChangeYear");
					 
	public ActionChangeYear() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
		   
	private Connection getConnection() throws SQLException {
		 
		return ds.getConnection();
		
	  }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request,response);
			
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		HeaderBean hb=null;
		FormBean fb = new FormBean();
		RequestDispatcher dispatcher = request.getRequestDispatcher("changeYear_result.jsp");
		boolean expired=false;
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ActionQueryMenus aqm = new ActionQueryMenus();
		
		String SQL_YEAR_KEY = "select year_key from rt_academic_year where year_desc = ?"; 
		
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
		
			request.setAttribute("CHANGEYEARPARM", fb);
			
			try{
				
				hb.setAcademicYear(request.getParameter("yearSelect"));
				fb.setAcademicYear(hb.getAcademicYear());
				
				c=getConnection();
				
				ps = c.prepareStatement(SQL_YEAR_KEY);
				ps.setString(1,hb.getAcademicYear());
				rs = ps.executeQuery();
				
				if (rs !=null){
					rs.next();
					hb.setAcademicYearKey(rs.getInt(1));
				}
				
				session.setAttribute("ALL_RESIDENTS", aqm.doQuery(c,"ALL_RESIDENTS_QUERY",null,"All Residents",hb.getAcademicYearKey()));
				session.setAttribute("ALL_PROGRAMS", aqm.doQuery(c,"ALL_PROGRAMS_QUERY",null,"Mount Sinai - All Programs",hb.getAcademicYearKey()));
				session.setAttribute("MY_RESIDENTS", aqm.doQuery(c,"MY_RESIDENTS_QUERY",hb.getUserName(),"All My Residents",hb.getAcademicYearKey()));
				session.setAttribute("MY_ADMIN_DEPARTMENTS", aqm.doQuery(c,"MY_ADMIN_DEPT_QUERY",hb.getUserName(),null,hb.getAcademicYearKey()));
				session.setAttribute("MY_ADMIN_PROGRAMS", aqm.doQuery(c,"MY_ADMIN_PGM_QUERY",hb.getUserName(),null,hb.getAcademicYearKey()));
				session.setAttribute("MY_PROGRAMS", aqm.doQuery(c,"MY_PROGRAMS_QUERY",hb.getUserName(),"All My Programs",hb.getAcademicYearKey()));
				session.setAttribute("MY_RESIDENT_STATUS", aqm.doQuery(c,"MY_STATUS_QUERY",hb.getUserName(),"All",hb.getAcademicYearKey()));
				session.setAttribute("ALL_POSITIONS", aqm.doQuery(c,"ALL_POSITIONS_QUERY",null,"All Positions",hb.getAcademicYearKey()));
				session.setAttribute("ALL_DEPARTMENTS", aqm.doQuery(c,"ALL_DEPARTMENTS_QUERY",null,"All Departments",hb.getAcademicYearKey()));
				session.setAttribute("ALL_USERS", aqm.doQuery(c,"ALL_USERS_QUERY",null,"All Users",hb.getAcademicYearKey()));
				session.setAttribute("ALL_SITES", aqm.doQuery(c,"ALL_SITES_QUERY",null,null,hb.getAcademicYearKey()));  
				session.setAttribute("ALL_ACADEMIC_YEARS", aqm.doQuery(c,"ALL_ACADEMIC_YEARS_QUERY",null,null,hb.getAcademicYearKey()));  
				
				fb.setSqlStatus(true);
				fb.setMessage1("You have changed the Academic Year to "+hb.getAcademicYear()+".");
				fb.setMessage2("All reports you create will only contain data from this Academic Year.");
				
				dispatcher.forward(request, response);
				
				
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
				
			} 			
		}
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

