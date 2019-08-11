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

public class ActionResidentQuery extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionResidentQuery");
		
	public ActionResidentQuery() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("residents_result.jsp");
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
		
			Connection c=null;
			ResultSet rs=null;
			PreparedStatement ps=null;
			List<String> dataSet = new ArrayList<String>();
			FormBean fb = new FormBean();
			fb.setProgram(request.getParameter("programList"));
			fb.setStatus(request.getParameter("statusList"));
			String site=null;
			String SQL_PREDICATE=null;
			String PROGRAM_PREDICATE=null;
			String STATUS_PREDICATE=null;
			String sql=null;
			String usr=hb.getUserName();
			int parm_count=0;
		 		
			String RESIDENT_QUERY = 	"select m.resident, " +
										"m.identifier," +
										"m.status, " +
										"m.employer, " +
										"m.department "+
										"from rt_resident_master m, rt_rotation r, rt_authorization a "+
										"where m.department = r.rotation "+
										"and   r.rotation_key = a.rotation_key "+ 
										"and   a.username = ? " +
										"and   m.academic_year_key = ? "+
										"and   r.academic_year_key = ? "+
										"and   a.academic_year_key = ? ";
			
			String ORDER_BY	= 			"order by 1 asc";
			
			log.debug(usr+" Q: P="+fb.getProgram()+";S="+fb.getStatus());
									  		
			request.setAttribute("RESIDENTQUERYPARM", fb);
	  		
			if ((fb.getProgram().equalsIgnoreCase("all my programs")) || 
			    (fb.getProgram().equalsIgnoreCase("mount sinai - all programs"))){			
					PROGRAM_PREDICATE = null;	
			}
			else {
				PROGRAM_PREDICATE = " and m.department = ? ";
				parm_count++;
			}
			
			if (fb.getStatus().equalsIgnoreCase("all")){
				STATUS_PREDICATE = null;	
			}
			else{
				STATUS_PREDICATE = fb.getStatus();
				parm_count++;
			}
			
			if (PROGRAM_PREDICATE != null){
				SQL_PREDICATE = PROGRAM_PREDICATE;
				if (STATUS_PREDICATE != null){
					SQL_PREDICATE=SQL_PREDICATE+" and m.status = ? ";
				}
			} 
			else if (STATUS_PREDICATE != null){
				SQL_PREDICATE = " and m.status = ? ";
			}
			
			if (null==SQL_PREDICATE){
				sql = RESIDENT_QUERY+ORDER_BY;		
			}
			else{
				sql = RESIDENT_QUERY+SQL_PREDICATE+ORDER_BY;	
			}
			
			log.debug("SQL: "+sql.replaceAll("'", ""));
			
			try {
			
				c = getConnection();
				ps = c.prepareStatement(sql); 
				ps.setString(1,hb.getUserName());
				ps.setInt(2,hb.getAcademicYearKey());
				ps.setInt(3,hb.getAcademicYearKey());
				ps.setInt(4,hb.getAcademicYearKey());
				
				if (parm_count==1){
				
					if (PROGRAM_PREDICATE == null){
						ps.setString(5, STATUS_PREDICATE);
					}
					else{
						ps.setString(5, fb.getProgram());
					}
				}
				else if (parm_count==2){
					ps.setString(5, fb.getProgram());
					ps.setString(6, STATUS_PREDICATE);
				}
				
				rs = ps.executeQuery();
	  		
				while (rs.next()){
	
					site = rs.getString(4);
				
					dataSet.add(rs.getString(1));
					dataSet.add(rs.getString(2));
					dataSet.add(rs.getString(3));
		       
					if (site.equalsIgnoreCase("mount sinai medical center")){
						dataSet.add("MSMC");
					}
					else{
						dataSet.add(site);
					}
		        	        
					dataSet.add(rs.getString(5));
		       
				}
 			
				request.setAttribute("RESIDENT_TABLE",dataSet);
				dispatcher.forward(request, response);		 
	  		
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
		}
	}//doPost
   
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
