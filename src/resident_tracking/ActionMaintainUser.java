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

public class ActionMaintainUser extends HttpServlet {

	private DataSource ds; 
	
	static Logger log = Logger.getLogger("ActionMaintainUser");
					 
	public ActionMaintainUser() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		return ds.getConnection();
		
	  }
	
	private List<String> doQuery(Connection c, String sql, String arg1, String arg2){
		
		List<String> l = new ArrayList<String>();
		ResultSet rs=null;
		PreparedStatement ps=null;
		
		try {
		
			ps = c.prepareStatement(sql);  
		
			if(arg1!=null){
				ps.setString(1,arg1);	
			}
			
			if (arg2!=null){
				ps.setString(2,arg2);
			}
		
			rs = ps.executeQuery();
  						
			if (rs.next()){
				l.add(0,rs.getString(1));	
				l.add(1,rs.getString(2));
				l.add(2,rs.getString(3));
			}			
		}
		catch (SQLException sqle){
			log.error(sqle.getMessage());
		}
		catch (Exception e){
	  		log.error("Exception in doQuery()");
	  		
	  	}
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
										
		} 
		return l;
		
	}
	
	private boolean verifyUserExists(Connection conn, FormBean fb, HttpServletRequest request){
	
		boolean exists=false;
		List<String> l = new ArrayList<String>();
		Iterator<String> itr;
		
		String SQL_EXISTS = "select u.username, u.firstname, u.lastname "+
							"from rt_user u " +
							"where u.username = ?";
		
		l = doQuery(conn,SQL_EXISTS,fb.getUserID(),null);
		itr=l.iterator();
		
		exists = (itr.hasNext()) ? true:false;
		
		l.clear();
		return exists;
		
	}
	
	private String verifyIdentity(Connection conn, FormBean fb, HttpServletRequest request){
		
		String match="unknown";
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
	
		String SQL_EXISTS1 = 	"select u.username, u.firstname, u.lastname "+
								"from rt_user u " +
								"where u.username = ?";
		
		String SQL_EXISTS2 = 	"select u.username, u.firstname, u.lastname "+
								"from rt_user u " +
								"where u.firstname = ? and u.lastname = ?";
		
		
		l1 = doQuery(conn,SQL_EXISTS1,fb.getUserID(),null);
		
		if (!l1.isEmpty()){
		
			String username  = (String)l1.get(0);
			String firstname = (String)l1.get(1);
			String lastname  = (String)l1.get(2);
			
			if ((username.trim().equalsIgnoreCase(fb.getUserID())) 			&&
					(firstname.trim().equalsIgnoreCase(fb.getFirstName())) 	&&
					(lastname.trim().equalsIgnoreCase(fb.getLastName()))){
					match="match";
					
			}
			else if ((username.trim().equalsIgnoreCase(fb.getUserID())) 	&&
					(!firstname.trim().equalsIgnoreCase(fb.getFirstName()))	||
					(!lastname.trim().equalsIgnoreCase(fb.getLastName()))){
				
					match="mismatch";
					fb.setMessage1("The user id and name you entered does not match with this existing USER record:");
					fb.setMessage2("USER ID:&nbsp;&nbsp;"+username+"&nbsp;&nbsp;LAST NAME: "+lastname+"&nbsp;&nbsp;FIRST NAME: "+firstname);
					fb.setMessage3("If this is the same person then correct the name you entered.");
					fb.setSqlStatus(false);
					
			}
		
		}
		
		if(match=="unknown"){
			
			l2 = doQuery(conn,SQL_EXISTS2,fb.getFirstName(),fb.getLastName());
			
			if (!l2.isEmpty()){
			
				String username  = (String)l2.get(0);
				String firstname = (String)l2.get(1);
				String lastname  = (String)l2.get(2);
				
				
				if((!username.trim().equalsIgnoreCase(fb.getUserID())) 			&&
						(firstname.trim().equalsIgnoreCase(fb.getFirstName())) 	&&
						(lastname.trim().equalsIgnoreCase(fb.getLastName()))){
					
						match="mismatch";
						fb.setMessage1("There is an existing user id for the person you entered: ");
						fb.setMessage2("USER ID:&nbsp;&nbsp;"+username+"&nbsp;&nbsp;LAST NAME: "+lastname+"&nbsp;&nbsp;FIRST NAME: "+firstname);
						fb.setMessage3("Please use the existing user id for this person.");
						fb.setSqlStatus(false);
						
				}
			
			}
			
		}
		
		l1.clear();
		l2.clear();
		return match;
		
	}
	
	private boolean checkUserQuery(FormBean fb, HttpServletRequest request){
			
		boolean rc=true;
											
		try {
	
			if(fb.getUserID()==null || fb.getUserID().trim().length()==0){
				fb.setMessage1("Please enter a value in the User ID field.");
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("maintainUsers_result.jsp");
		boolean expired=false;
		boolean user_exists=false;
		String matched_identity=null;
		ActionQueryMenus aqm = new ActionQueryMenus();
		
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
			
				fb.setUserID(request.getParameter("userID"));
				fb.setLastName(request.getParameter("lastName"));
				fb.setFirstName(request.getParameter("firstName"));
				fb.setDegree(request.getParameter("degree"));
				fb.setTitle(request.getParameter("positionList"));
//				fb.setDepartment(request.getParameter("departmentList"));
				fb.setProgram(request.getParameter("programList"));
				fb.setSite(request.getParameter("siteList"));
				fb.setSqlAction(request.getParameter("sqlAction"));
				fb.setSqlStatus(true);
				boolean VALID_MENU_CHOICES=false;
				String sql=null;
				String usr=hb.getUserName();
				int rows_updated=0;
				
				log.info(usr+" Q: U="+fb.getUserID()+";LN="+fb.getLastName()+";FN="+fb.getFirstName()+";D="+fb.getDegree()+";T="+fb.getTitle()+";DP="+fb.getDepartment()+"P="+fb.getProgram()+"S="+fb.getSite()+"CMD="+fb.getSqlAction());
								
				String SQL_NEW1 =	"insert into rt_user "+
									"(username,password,lastname,firstname) "+
									"values "+
									"(?,'password',?,?)";
				
				String SQL_NEW2 = 	"insert into rt_role " +
									"(username,rolename) " +
									"values " +
									"(?,'USER')";
				
				String SQL_ADD =   	"insert into rt_authorization "+
									"(username"+
									",rotation_key" +
									",auth_level_key" +
									",title_key" +
									",lastname" +
									",firstname" +
									",degree" +
									",update_date" +
									",update_username" +
									",site_key" +
									",academic_year_key) " +
									"values " +
									"(?" +
									",(select rotation_key from rt_rotation where rotation = ? and academic_year_key = ?)" +
									",(select auth_level_key from rt_authorization_level where upper(auth_level) = 'USER')" +
									",(select title_key from rt_title where title = ?)" +
									",(select lastname from rt_user where username = ?)" +
									",(select firstname from rt_user where username = ?)" +
									",?" +
									",(select sysdate from dual)" +
									",?" +
									",(select site_key from rt_site where upper(site) = ?)" +
									",?)";
				
				String SQL_DELETE = "delete from rt_authorization " +
									"where username = ? " +
									"and rotation_key = (select rotation_key from rt_rotation where rotation = ? and academic_year_key = ?) " +
									"and auth_level_key = (select auth_level_key from rt_authorization_level where upper(auth_level) = 'USER') " +
									"and title_key = (select title_key from rt_title where title = ?)" +
									"and academic_year_key = ?";
				
				try {	
					
					c = getConnection(); 
			
					VALID_MENU_CHOICES=checkUserQuery(fb,request);
								
					log.debug("valid menu choices set to: "+VALID_MENU_CHOICES);
					request.setAttribute("MAINTAINUSERPARM", fb);
					
					if (VALID_MENU_CHOICES){
					
						user_exists=verifyUserExists(c,fb,request);	
						matched_identity=verifyIdentity(c,fb,request);
						
						if (!user_exists && matched_identity.equalsIgnoreCase("unknown")){
						
							sql = SQL_NEW1;
							ps = c.prepareStatement(sql);
							//add user to rt_user
							ps.setString(1,fb.getUserID());
							ps.setString(2,fb.getLastName());
							ps.setString(3,fb.getFirstName());
							rows_updated = ps.executeUpdate();
							JDBCManager.closePreparedStatement(ps);
							//add user to rt_role
							sql = SQL_NEW2;
							ps = c.prepareStatement(sql);
							ps.setString(1,fb.getUserID());
							rows_updated = ps.executeUpdate();
							JDBCManager.closePreparedStatement(ps);
							matched_identity="match";
						}
						
						if (matched_identity.equalsIgnoreCase("match")){
						
							sql = (fb.getSqlAction().equalsIgnoreCase("add")) ? SQL_ADD:SQL_DELETE;
					
							log.debug("SQL: "+sql.replaceAll("'", ""));	
										
							ps = c.prepareStatement(sql);
					
							if (fb.getSqlAction().equalsIgnoreCase("add")){
							
								ps.setString(1, fb.getUserID());
								ps.setString(2, fb.getProgram());
								ps.setInt(3, hb.getAcademicYearKey());
								ps.setString(4, fb.getTitle());
								ps.setString(5, fb.getUserID());
								ps.setString(6, fb.getUserID());
								ps.setString(7, fb.getDegree());
								ps.setString(8, hb.getUserName());
								ps.setString(9, fb.getSite().toUpperCase());
								ps.setInt(10, hb.getAcademicYearKey());
								
							}
						
							if (fb.getSqlAction().equalsIgnoreCase("delete")){
			
								ps.setString(1, fb.getUserID());
								ps.setString(2, fb.getProgram());
								ps.setInt(3, hb.getAcademicYearKey());
								ps.setString(4, fb.getTitle());
								ps.setInt(5, hb.getAcademicYearKey());
							
							}
						
							rows_updated = ps.executeUpdate();
							log.info(usr+" rows updated by "+fb.getSqlAction()+" command: "+rows_updated);
							fb.setRowsUpdated(rows_updated);
							
							session.setAttribute("ALL_USERS", aqm.doQuery(c,"ALL_USERS_QUERY",null,"All Users",hb.getAcademicYearKey()));
			
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
					JDBCManager.closePreparedStatement(ps);
					JDBCManager.closeConnection(c);		
					dispatcher.forward(request, response);	
				} 
			
			}//administrator
			else{
//				fb.setMessage1("You do not have the administrative rights needed to use this function.");
//				request.setAttribute("MAINTAINUSERPARM", fb);
				dispatcher = request.getRequestDispatcher("maintainUsers.jsp");
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
