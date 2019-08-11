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

public class ActionUserQuery extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionUserQuery");
			
	public ActionUserQuery() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("users_result.jsp");
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
			PreparedStatement ps=null;
			ResultSet rs=null;
			String PREDICATE="";
			String sql=null;
			String job_title=null;
			String usr = hb.getUserName();
			int [][] args = new int [3][2];
			int idx=0;
			List<String> dataSet = new ArrayList<String>();
			FormBean fb = new FormBean();
			fb.setApplicationUser(request.getParameter("userList"));
			fb.setProgram(request.getParameter("programList"));
			fb.setFilter(request.getParameter("filterList"));
			fb.setSortOrder(request.getParameter("sortList"));
				  		
			log.debug(usr+" Q: U="+fb.getApplicationUser()+";D="+fb.getDepartment()+";P="+fb.getProgram()+";F="+fb.getFilter()+";S="+fb.getSortOrder());
				
			/*** Oracle Query 
			String USER_QUERY = 	"select a.lastname||', '||a.firstname, t.title, r.rotation "+
									"from rt_authorization a, "+
									"rt_title t, "+
									"rt_rotation r "+
									"where a.rotation_key = r.rotation_key "+
									"and   a.title_key = t.title_key " +
									"and   t.title <> 'Administrator' " +
									"and   a.academic_year_key = ? " +
									"and   r.academic_year_key = ? ";
			***/
			
			/*** MySql Query ***/
			
			String USER_QUERY = 	"select concat(a.lastname,', ',a.firstname), t.title, r.rotation "+
									"from rt_authorization a join rt_rotation r on (a.rotation_key = r.rotation_key) "+
									"join rt_title t on (t.title_key = a.title_key) "+
									"where   t.title <> 'Administrator' " +
									"and   a.academic_year_key = ? " +
									"and   r.academic_year_key = ? ";
			
			String ORDER_BY	= null;
					
			request.setAttribute("USERQUERYPARM", fb);
			
			//initializing table used to map form selections into PreparedStatement parameters
			
			for (int j=0;j<3;j++){
				args[j][0]=0;
				args[j][1]=0;		
			}
					
			if (!fb.getApplicationUser().equalsIgnoreCase("all users")){
//				PREDICATE = " and trim(a.lastname)||', '||trim(a.firstname) = ? ";
				PREDICATE = " and concat(trim(a.lastname),', ',trim(firstname)) = ?";
				
				args[idx][1] = 1;
				idx++;
			}
			
			if (!fb.getFilter().equalsIgnoreCase("all positions")){
				PREDICATE = PREDICATE+" and trim(t.title) = ? ";
				args[idx][1] = 2;
				idx++;
			}

			if (!fb.getProgram().equalsIgnoreCase("mount sinai - all programs")){
				PREDICATE = PREDICATE+" and trim(r.rotation) = ? ";
				args[idx][1] = 3;
			}
			
			if (fb.getSortOrder().equalsIgnoreCase("name asc")){
				ORDER_BY = " order by 1 asc ";
			}
			else if (fb.getSortOrder().equalsIgnoreCase("name desc")){
				ORDER_BY = " order by 1 desc ";
			}
			else if (fb.getSortOrder().equalsIgnoreCase("program asc")){
				ORDER_BY = " order by 3 asc ";
			}
			else if (fb.getSortOrder().equalsIgnoreCase("program desc")){
				ORDER_BY = " order by 3 desc ";
			}
			
			sql = USER_QUERY+PREDICATE+ORDER_BY;
			log.debug("SQL: "+sql.replaceAll("'", ""));
			
			System.out.println(sql);
					
			try {
				
				c = getConnection();		
								
				System.out.println("preparing statement");
				
				ps = c.prepareStatement(sql);
				
				System.out.println("setting parameters");
				
				ps.setInt(1, hb.getAcademicYearKey());
				ps.setInt(2, hb.getAcademicYearKey());
				
				for (int k=0;k<3;k++){
					
					switch (args[k][1]){
					
						case 0: {break;} 
					
						case 1:	{
								ps.setString(k+3, fb.getApplicationUser());
								break;
								}
						case 2: {
								if (fb.getFilter().equalsIgnoreCase("program directors")){
									job_title="Program Director";	
								}
								else if (fb.getFilter().equalsIgnoreCase("program coordinators")){
									job_title="Program Coordinator";	
								}
								else if (fb.getFilter().equalsIgnoreCase("administrators")){
									job_title="Administrator";	
								}
								ps.setString(k+3, job_title);
								break;
								}
						case 3: {
								ps.setString(k+3, fb.getProgram());
								break;
								}
					}	
						
				}
				
				System.out.println("about to run query");
				
				rs = ps.executeQuery();
	  		
				System.out.println("query returned");
				
				while (rs.next()){
							
					System.out.println("loop thru rs");
					
					dataSet.add(rs.getString(1));
					dataSet.add(rs.getString(2));
					dataSet.add(rs.getString(3));
									
				}
 			 
				request.setAttribute("APPLICATION_USERS",dataSet);
				dispatcher.forward(request, response);		
	  		
			}
			catch (SQLException sqle){
				log.error(sqle.getMessage());
			}
			catch (Exception e){
				log.error("Exception in doPost() method.");
			}
			finally {
			
				JDBCManager.closePreparedStatement(ps);
				JDBCManager.closeResult(rs);
				JDBCManager.closeConnection(c);
							
			} 
		
		}//!expired
		
	}// doPost
   
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
