package resident_tracking;

import java.io.IOException;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.apache.log4j.Logger;

public class ActionQueryMenus extends HttpServlet {

	private DataSource ds;   
	
	static Logger log = Logger.getLogger("ActionQueryMenus"); 
		
	private String MY_RESIDENTS_QUERY =		"select m.resident " +
											"from rt_resident_master m, rt_rotation r, rt_authorization a "+
											"where m.department = r.rotation "+
											"and   r.rotation_key = a.rotation_key "+
											"and   a.username = ? "+
											"and   m.academic_year_key = ? "+
											"and   r.academic_year_key = ? "+
											"and   a.academic_year_key = ? "+
											"order by resident asc";
	
	private String ALL_RESIDENTS_QUERY =	"select distinct(trim(m.resident)) "+
											"from rt_resident_master m "+
											"where m.academic_year_key = ? "+
											"order by 1 asc";
	
	private String ALL_STATUS_QUERY =		"select distinct(trim(m.status)) "+
											"from rt_resident_master m "+
											"where m.academic_year_key = ? "+
											"order by 1 asc";

	private String MY_PROGRAMS_QUERY =		"select trim(ro.rotation) "+
											"from rt_rotation ro, rt_authorization au "+
											"where au.rotation_key = ro.rotation_key "+
											"and   au.username = ? "+
											"and   ro.academic_year_key = ? "+
											"and   au.academic_year_key = ? "+
											"order by ro.rotation asc";
	
	private String MY_STATUS_QUERY = 		"select distinct(m.status)" +
											"from rt_resident_master m,"+ 
											"rt_rotation r, "+
											"rt_authorization a "+
											"where m.department = r.rotation "+
											"and   r.rotation_key = a.rotation_key "+ 
											"and   a.username = ? "+
											"and   m.academic_year_key = ? "+
											"and   r.academic_year_key = ? "+
											"and   a.academic_year_key = ? "+
											"order by 1 asc";
	
	private String MY_ADMIN_DEPT_QUERY =	"select distinct(d.department) "+
											"from rt_department d, rt_rotation r, rt_authorization a "+
											"where a.username = ? "+
											"and   a.rotation_key = r.rotation_key "+
											"and   r.department_key = d.department_key " +
											"and   a.auth_level_key = 2 " + //privileged
											"and   d.academic_year_key = ? "+
											"and   r.academic_year_key = ? "+
											"and   a.academic_year_key = ? "+
											"order by 1 asc";  
	
	private String MY_ADMIN_PGM_QUERY =		"select distinct(r.rotation) "+ 
											"from rt_rotation r, rt_authorization a "+ 
											"where a.username = ? "+
											"and   a.rotation_key = r.rotation_key "+ 
											"and   a.auth_level_key = 2 " + //privileged
											"and   r.academic_year_key = ? "+
											"and   a.academic_year_key = ? "+
											"order by 1 asc";

	private String ALL_PROGRAMS_QUERY = 	"select distinct(trim(rotation)) " +
											"from rt_rotation r " +
											"where r.academic_year_key = ? "+
											"order by 1 asc";
	
	private String ALL_POSITIONS_QUERY   =  "select distinct(title) " +
											"from rt_title " +
											"order by 1 asc";
	
	private String ALL_DEPARTMENTS_QUERY = 	"select distinct(trim(department)) " +
											"from rt_department d " +
											"where d.academic_year_key = ? " +
											"order by 1 asc";
	
	/*** Oracle Query
	private String ALL_USERS_QUERY       =  "select distinct(lastname||', '||firstname) " +
											"from rt_authorization a " +
											"where a.academic_year_key = ? " +
											"order by lastname||', '||firstname asc";
	***/
	
	/*** MySql Query ***/
	private String ALL_USERS_QUERY 		=	"select distinct(concat(lastname,', ',firstname)) "+
											"from rt_authorization a "+
											"where a.academic_year_key = ? "+
											"order by concat(lastname,', ',firstname) ";
	
	private String ALL_SITES_QUERY		=	"select site from rt_site order by 1 asc";
	
	private String ALL_ACADEMIC_YEARS_QUERY= "select year_desc from rt_academic_year";
	
	public ActionQueryMenus() {
		super();
	} 
 
	public void destroy() {
		super.destroy(); 
	}
	
	private Connection getConnection() throws SQLException {
	 
		log.debug("opening connection");
		return ds.getConnection();
		
	  }
		   
	public List<String> doQuery(Connection c, String query, String arg1, String arg2,int academic_year){
		
		List <String> l = new ArrayList<String>();
		ResultSet rs=null;
		PreparedStatement ps=null;	
		int rowcount=0;
		String sql=null;
		
		if (query.equalsIgnoreCase("ALL_RESIDENTS_QUERY")){
			sql = ALL_RESIDENTS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_PROGRAMS_QUERY")){
			sql = ALL_PROGRAMS_QUERY;	
		}
		if (query.equalsIgnoreCase("MY_ADMIN_DEPT_QUERY")){
			sql = MY_ADMIN_DEPT_QUERY;
		}
		if (query.equalsIgnoreCase("MY_ADMIN_PGM_QUERY")){
			sql = MY_ADMIN_PGM_QUERY;
		}
		if (query.equalsIgnoreCase("MY_PROGRAMS_QUERY")){
			sql = MY_PROGRAMS_QUERY;
		}
		if (query.equalsIgnoreCase("MY_STATUS_QUERY")){
			sql = MY_STATUS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_POSITIONS_QUERY")){
			sql = ALL_POSITIONS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_DEPARTMENTS_QUERY")){
			sql = ALL_DEPARTMENTS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_USERS_QUERY")){
			sql = ALL_USERS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_SITES_QUERY")){
			sql = ALL_SITES_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_ACADEMIC_YEARS_QUERY")){
			sql = ALL_ACADEMIC_YEARS_QUERY;
		}
		if (query.equalsIgnoreCase("MY_RESIDENTS_QUERY")){
			sql = MY_RESIDENTS_QUERY;
		}
		if (query.equalsIgnoreCase("ALL_STATUS_QUERY")){
			sql = ALL_STATUS_QUERY;
		}
		
		
		try {
		
			ps = c.prepareStatement(sql);  
		
			if(arg1!=null){
				ps.setString(1, arg1);	
			}
			
			if (arg2!=null){
				l.add(arg2);	
			}
			
			if (query.equalsIgnoreCase("my_residents_query")){
				ps.setInt(2, academic_year);
				ps.setInt(3, academic_year);
				ps.setInt(4, academic_year);
			}
			if (query.equalsIgnoreCase("all_residents_query")){
				ps.setInt(1, academic_year);
			}
			if (query.equalsIgnoreCase("all_status_query")){	
				ps.setInt(1, academic_year);
			}
			if (query.equalsIgnoreCase("my_programs_query")){	
				ps.setInt(2, academic_year);
				ps.setInt(3, academic_year);
			}
			if (query.equalsIgnoreCase("my_status_query")){
				ps.setInt(2, academic_year);
				ps.setInt(3, academic_year);
				ps.setInt(4, academic_year);
			}
			if (query.equalsIgnoreCase("my_admin_dept_query")){
				ps.setInt(2, academic_year);
				ps.setInt(3, academic_year);
				ps.setInt(4, academic_year);
			}
			if (query.equalsIgnoreCase("my_admin_pgm_query")){	
				ps.setInt(2, academic_year);
				ps.setInt(3, academic_year);
			}
			if (query.equalsIgnoreCase("all_programs_query")){
				ps.setInt(1, academic_year);
			}
			if (query.equalsIgnoreCase("all_departments_query")){
				ps.setInt(1, academic_year);
			}
			if (query.equalsIgnoreCase("all_users_query")){
				ps.setInt(1, academic_year);
			}
				
			rs = ps.executeQuery();
						
			while (rs.next()){								
				l.add(rs.getString(1).trim());
				rowcount++;
			}			
	
		}
		catch (SQLException sqle){
			log.error(sqle.getMessage());
		}
		catch (Exception e){
			log.error("Exception in doQuery() method of ActionQueryMenus.");
	  		
	  	}
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
										
		} 

		return l;
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request,response);
			
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection c=null;
		HttpSession session = request.getSession(true);
		HeaderBean hb = (HeaderBean)session.getAttribute("HEADER_SECTION");
		String usr=hb.getUserName();
		
		if(hb.getAuthorized()){
			
			try {
	
				
				
				/***
				if (null == session.getAttribute("MY_RESIDENTS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting my residents");
					session.setAttribute("MY_RESIDENTS", doQuery(c,"MY_RESIDENTS_QUERY",hb.getUserName(),"All My Residents",hb.getAcademicYearKey()));
				}
				***/
				
				if (null == session.getAttribute("RESIDENT_LIST")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting the resident list attribute");
					
					if(hb.getResidentList().equalsIgnoreCase("ALL_RESIDENTS")){
						session.setAttribute("RESIDENT_LIST", doQuery(c,"ALL_RESIDENTS_QUERY",null,"All Residents",hb.getAcademicYearKey()));
					}	
					else if(hb.getResidentList().equalsIgnoreCase("MY_RESIDENTS")){
						session.setAttribute("RESIDENT_LIST", doQuery(c,"MY_RESIDENTS_QUERY",hb.getUserName(),"All My Residents",hb.getAcademicYearKey()));
					}	
				}
				
				if (null == session.getAttribute("MY_ADMIN_DEPARTMENTS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting admin departments");
					session.setAttribute("MY_ADMIN_DEPARTMENTS", doQuery(c,"MY_ADMIN_DEPT_QUERY",hb.getUserName(),null,hb.getAcademicYearKey()));
				}
				
				if (null == session.getAttribute("MY_ADMIN_PROGRAMS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting admin pgms");
					session.setAttribute("MY_ADMIN_PROGRAMS", doQuery(c,"MY_ADMIN_PGM_QUERY",hb.getUserName(),null,hb.getAcademicYearKey()));
				}
				
				/***
				if (null == session.getAttribute("MY_PROGRAMS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting my programs");
					session.setAttribute("MY_PROGRAMS", doQuery(c,"MY_PROGRAMS_QUERY",hb.getUserName(),"All My Programs",hb.getAcademicYearKey()));
				}
					
				if (null == session.getAttribute("ALL_PROGRAMS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all programs");	
					session.setAttribute("ALL_PROGRAMS", doQuery(c,"ALL_PROGRAMS_QUERY",null,"Mount Sinai - All Programs",hb.getAcademicYearKey()));
				}
				***/
				
				if (null == session.getAttribute("PROGRAM_LIST")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting the program list attribute");
					
					if(hb.getProgramList().equalsIgnoreCase("ALL_PROGRAMS")){
						System.out.println("ActionQueryMenus: setting PROGRAM_LIST attribute to ALL_PROGRAMS_QUERY");
						session.setAttribute("PROGRAM_LIST", doQuery(c,"ALL_PROGRAMS_QUERY",null,"Mount Sinai - All Programs",hb.getAcademicYearKey()));
					}	
					else if(hb.getProgramList().equalsIgnoreCase("MY_PROGRAMS")){
						System.out.println("ActionQueryMenus: setting PROGRAM_LIST attribute to MY_PROGRAMS_QUERY");
						session.setAttribute("PROGRAM_LIST", doQuery(c,"MY_PROGRAMS_QUERY",hb.getUserName(),"All My Programs",hb.getAcademicYearKey()));
					}	
				}
				
				if (null == session.getAttribute("MY_RESIDENT_STATUS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting my status");
					session.setAttribute("MY_RESIDENT_STATUS", doQuery(c,"MY_STATUS_QUERY",hb.getUserName(),"All",hb.getAcademicYearKey()));
				}			
			
				if (null == session.getAttribute("ALL_POSITIONS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all positions");
					session.setAttribute("ALL_POSITIONS", doQuery(c,"ALL_POSITIONS_QUERY",null,"All Positions",hb.getAcademicYearKey()));
				}
			
				if (null == session.getAttribute("ALL_DEPARTMENTS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all departments");
					session.setAttribute("ALL_DEPARTMENTS", doQuery(c,"ALL_DEPARTMENTS_QUERY",null,"All Departments",hb.getAcademicYearKey()));
				}
			
				if (null == session.getAttribute("ALL_USERS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all users");
					session.setAttribute("ALL_USERS", doQuery(c,"ALL_USERS_QUERY",null,"All Users",hb.getAcademicYearKey()));
				}
					
				if (null == session.getAttribute("ALL_SITES")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all sites");
					session.setAttribute("ALL_SITES", doQuery(c,"ALL_SITES_QUERY",null,null,hb.getAcademicYearKey()));  
				}
							
				if (null == session.getAttribute("ALL_ACADEMIC_YEARS")){
					if (c==null)c = getConnection();
					log.debug(usr+" setting all academic years");
					session.setAttribute("ALL_ACADEMIC_YEARS", doQuery(c,"ALL_ACADEMIC_YEARS_QUERY",null,null,hb.getAcademicYearKey()));  
				}
				
				if (hb.getAdministrator()==true){
					
					/***
					if (null == session.getAttribute("ALL_RESIDENTS")){
						if (c==null)c = getConnection();
						log.debug(usr+" setting all residents");
						session.setAttribute("ALL_RESIDENTS", doQuery(c,"ALL_RESIDENTS_QUERY",null,"All Residents",hb.getAcademicYearKey()));
					}
					***/
					
					if (null == session.getAttribute("ALL_STATUS")){
						if (c==null)c = getConnection();
						log.debug(usr+" setting all status");
						session.setAttribute("ALL_STATUS", doQuery(c,"ALL_STATUS_QUERY",null,null,hb.getAcademicYearKey()));  
					}
					
				}
			
			}
		
			catch (Exception e){
				log.error("Exception in doPost()");
			}
			finally {
				JDBCManager.closeConnection(c);
			} 
		}//authorized
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
