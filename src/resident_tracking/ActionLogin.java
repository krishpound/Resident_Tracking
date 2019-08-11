package resident_tracking;


import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.naming.*; 
import javax.sql.*;
//import oracle.jdbc.pool.*;


public class ActionLogin extends HttpServlet { 
	
	private DataSource ds;
	
	String verify_credentials = 	"select resident,tds_exception,hsm_exception,tds_exception+hsm_exception as TOTAL " +
							"from rt_temp_summary_cfp " +
							"where resident = ?";
				
	public void init(ServletConfig c)throws ServletException{
		
		try{
			Context envCtx = (Context) new InitialContext().lookup("java:/comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/resident");
									
		}
		catch (NamingException ne){
			ne.printStackTrace();
		}		
	}
	
	private Connection getConnection() throws SQLException {
	    return ds.getConnection();
	  }
	
	public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    
    	doPost(request,response);
   
    }
  
	public void doPost(HttpServletRequest request,
		  			 HttpServletResponse response)
	  	throws ServletException, IOException{
	  	  
	  	Connection c=null;
	  
	  	try {
	  		
	  		RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
	  		loginBean loginBean = new loginBean();
	  		String lt=null;
	  		
	  		System.out.println("loginTime="+request.getParameter("loginTime"));
	  		
//	  		loginBean.setUID(request.getParameter("username"));
//	  		loginBean.setPW(request.getParameter("password"));
//	  		request.setAttribute("loginBean", loginBean);
//	  		String uid = request.getParameter("username");
//	  		String pw = request.getParameter("password");
	  		  		
	  		c = getConnection();
	  		Statement stmt = c.createStatement();
	  		PreparedStatement login = c.prepareStatement(verify_credentials);
	  		
	  		login.setString(1, request.getParameter("username"));
	  		login.setString(2, request.getParameter("password"));
	
	  		ResultSet rs = login.executeQuery();
	
	  		if (rs.next()){
	  			System.out.println("Login successful");
	  			HttpSession session = request.getSession();
	  			
	  			
	  			
	  			lt = LoginTime.getLoginTime();
	  			session.setAttribute("User", request.getParameter("username"));
	  			session.setAttribute("loginTime", lt);
	  			session.setAttribute("securityTime",request.getParameter("securityTime"));
	  			  			
	  		}
	  		else{
	  			System.out.println("Login failed");
	  			request.setAttribute("Error", "Invalid password.");
	  			rd=request.getRequestDispatcher("/loginError.jsp");
	  			
	  		}
	  		
	  		rd.forward(request, response);		

	  		if (rs != null){
	  			rs.close();
	  		}
	  		
	  		if (stmt != null){
	  			stmt.close();
	  		}
	  			 	  		 		
	  	}
	  	catch (SQLException sqle){
	  		sqle.printStackTrace();
	  	}
	  		
	  	catch (Exception e){
	  		System.out.println("Exception in doPost() method.");
	  		e.printStackTrace();	
	  	}
	  	finally {
	  			  		
	  		if (c != null){
	  			
	  			try {
	  				c.close();
	  				System.out.println("Connection returned to pool.");
	  			}
	  			catch (SQLException e){
	  				System.out.println("Exception when closing connection.");
	  				e.printStackTrace();
	  			}
	  		}
	  		else {
	  			System.out.println("Connection was not open.");
	  		}
	  		
	  	}
	  
	  	
	  }
	 
  }
  
