package resident_tracking;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class JDBCManager {

	private static javax.sql.DataSource ds;  
	
	static Logger log = Logger.getLogger("JDBCManager");
		
	private JDBCManager(){};
	
	public static javax.sql.DataSource init() throws ServletException {
		
		try{
			
			/********* JBOSS 5.0.1 DataSource configuration ******/
			InitialContext ic = new InitialContext();
			ds = (DataSource)ic.lookup( "java:/resident_tracking-DS" );
			
			/********* TOMCAT DataSouce Configuration ******************
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/resident");
			ds = (DataSource)ic.lookup( "java:/PostgresDS" );
			ds = (DataSource)ic.lookup( "java:/mysql-DS" );
			************************************************************/
														
		}
		catch (NamingException ne){
			ne.printStackTrace();
		}		
		catch (Exception e){
			e.printStackTrace();
		}		
	
		return ds;
	}
	
	public static void closePreparedStatement(PreparedStatement ps){
		
		if (ps != null){
			
			try {
				ps.close();
				log.debug("prepared statement was closed");
			}
			catch (Exception e){
				log.error("prepared statement failed to close.");
				e.printStackTrace();
			}		
		} 
	}	
		
	public static void closeStatement(Statement stmt){
		
		if (stmt != null){
			
			try {
				stmt.close();
				log.debug("statement was closed");
			}
			catch (Exception e){
				log.error("statement failed to close");
				e.printStackTrace();
			}
			
		} 
	}
	
	public static void closeResult(ResultSet result){
		
		if (result != null){
			
			try {
				result.close();
				log.debug("resultset was closed");
			}
			catch (Exception e){
				log.error("resultset failed to close");
				e.printStackTrace();
			}
			
		}
	}
	
	public static void closeConnection(Connection connection){
		
		if (connection != null){
			
			try {
				connection.close();
				log.debug("connection returned to pool");
			}
			catch (Exception e){
				log.error("connection failed to close");
				e.printStackTrace();
			}
			
		}
	}
	
}
