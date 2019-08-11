package resident_tracking;
 
import java.io.IOException;
import java.sql.*;
import javax.sql.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.security.auth.*;
import javax.security.auth.login.*;
import java.security.Principal;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;


public class ActionHeader extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionHeader");
		
	private String SALUTATION_QUERY 	= 	"select concat(a.firstname,' ',a.lastname),t.title "+
											"from rt_authorization a join rt_title t on (a.title_key = t.title_key) "+
											"where username = ?";
			
	private String ACADEMIC_YEAR        =	"select ay.year_desc,ay.year_key "+
											"from rt_academic_year ay join rt_default_academic_year day "+ 
											"on (ay.year_key=day.year_key)";
	
		
	public ActionHeader() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	
	private String[] doHeaderQuery(Connection conn, String sql, String username){
		
		String header_values[] = new String[4];  //store salutation,job title,academic year
		ResultSet rs=null;
		PreparedStatement ps=null;
		String init=null;
			
		try {
		
			ps = conn.prepareStatement(sql); 
		
			ps.setString(1,init);
			ps.setString(1,username);
			
			rs = ps.executeQuery();
			
			if (rs==null){
				header_values[0]=null;
				header_values[1]=null;
			}
			else{
				rs.next();
				header_values[0]=rs.getString(1);
				header_values[1]=rs.getString(2);
			}
			
			ps = conn.prepareStatement(ACADEMIC_YEAR);
			rs = ps.executeQuery();
			
			if (rs==null){
				header_values[2]=null;
				header_values[3]=null;
			}
			else{
				rs.next();
				header_values[2]=rs.getString(1);
				header_values[3]=Integer.toString(rs.getInt(2));
			}
						
		}
		catch (SQLException sqle){
			log.error("SQLException in doHeaderQuery()");
			log.error(sqle.getMessage());
		}
		catch (Exception e){
	  		log.error("Exception in doHeaderQuery()");
	  		
	  	}
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
										
		} 
		return header_values;
		
	}
		
	private Connection getConnection() throws SQLException { 
		
		Connection conn=null;
		
		try {
			conn =  ds.getConnection();
		}
		catch (SQLException sqle){
			log.error("SQL exception getting connection");
			log.error(sqle.getMessage());
			log.error(sqle.getLocalizedMessage());
			log.error("ERROR CODE: "+sqle.getErrorCode());
		}
		catch (Exception ex){
			log.error("Exception getting connection.");
		}
		finally{} 
		
		return conn;	
	  }
		   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request,response);
			
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true); 
		HeaderBean hb = new HeaderBean();
		String username=null;
		Connection c=null;
		boolean admin=false;
		String Header_Values[] = new String[2];
		String UserAgent = request.getHeader("User-Agent");
		String browser=null;
			
		if (UserAgent != null){ 
		
			if (UserAgent.contains("MSIE")){
				browser="MSIE";
			}
			else if(UserAgent.contains("Firefox")){
				browser="Firefox";
			}
			else{
				log.debug("Unknown browser type...defaulting to browser Firefox.");
				browser="Firefox";
			}
		}
		else{
			log.debug("User-Agent on HTTPHeader is null...defaulting browser to Firefox");
			browser="Firefox";
		}
			
		try {
			
			if (null == session.getAttribute("HEADER_SECTION")){
				
				hb.setLoginTime(resident_tracking.LoginTime.convEpochTime(session.getCreationTime()));
				hb.setBrowser(browser);
				
				c=getConnection();
				
				Principal principal = request.getUserPrincipal(); 
				if (principal != null){
					username = principal.getName();	
				}
		
				admin = request.isUserInRole("ADMIN");	
				
				if (admin){
					hb.setAdministrator(true);
					hb.setProgramList("ALL_PROGRAMS");
					hb.setResidentList("ALL_RESIDENTS");
				}
				else{
					hb.setAdministrator(false);
					hb.setProgramList("MY_PROGRAMS");
					hb.setResidentList("MY_RESIDENTS");
				}
				
				log.info(username+" login with "+browser+" browser");
					
				Header_Values = doHeaderQuery(c,SALUTATION_QUERY,username);
						
				if (null==Header_Values[0]){
					hb.setAuthorized(false);
				}
				else{
					hb.setWelcomeName(Header_Values[0]);
					hb.setTitle(Header_Values[1]);
					hb.setAcademicYear(Header_Values[2]);
					hb.setAcademicYearKey(Integer.parseInt(Header_Values[3]));
					hb.setUserName(username);
					hb.setAuthorized(true);
				}
				session.setAttribute("HEADER_SECTION", hb);
			}	
		}
		catch (SQLException sqle){
			log.error(sqle.getMessage());
			
			System.out.println("ActionHeader SQL exception");
		}
		catch (Exception e){
	  		log.error("Exception in doPost()");
	  		log.error(e.getMessage());	  		
	  		System.out.println("ActionHeader unspecified exception");
	  	}
		finally {
			
			JDBCManager.closeConnection(c);
							
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

