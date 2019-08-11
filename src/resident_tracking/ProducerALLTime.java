package resident_tracking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class ProducerALLTime implements DatasetProducer,Serializable {

	private DataSource ds; 
    
    static Logger log = Logger.getLogger("ProducerALLTime"); 
    
    private final String[] categories =    {};
    private final String[] seriesNames =   {};

	
    private Connection getConnection() throws SQLException {
   	 
		return ds.getConnection();
		
	} 
 
    public Object produceDataset(Map params) throws DatasetProduceException {
    	
    	log.debug("producing data.");
//    	System.out.println("Hello from ProducerALLTime!");
   
    	Connection c = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String STARTDATE=null;
    	String ENDDATE=null;
    	String RESIDENT=null;
    	String FILTER=null;
    	String ACADEMIC_YEAR=null;
    	int	   academic_year=0;
    	String sql=null;
    	int fetch_count=0;
    	int fetch_count_hx=0;
    	Vector Xaxis = new Vector();
    	DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        	
		XYDataset dataset = new DefaultXYDataset(){	
			
			protected void finalize() throws Throwable {
				super.finalize();
				log.debug(this +" finalized.");
			}
        };
         
        if (params.containsKey("startDate")){
        	STARTDATE = (String)params.get("startDate");
        }
        if (params.containsKey("endDate")){
        	ENDDATE = (String)params.get("endDate");
        }
        if (params.containsKey("resident")){
        	RESIDENT = (String)params.get("resident");
        }
        if (params.containsKey("filter")){
        	FILTER = (String)params.get("filter");
        }
        if (params.containsKey("academic_year")){
        	ACADEMIC_YEAR = (String)params.get("academic_year");
        	academic_year = Integer.parseInt(ACADEMIC_YEAR);
        }
        
        try{
        	    	
        	ds=JDBCManager.init();
        	c = getConnection();
        	int Category=0;
        	int x_val=0;
        	Date plotValue;
        	int X=0;
        	int Xhx=0;
        	int compare=0;
        	Double Y=0.0;
        	Double Yhx=0.0;
        	String plotTime=null;
        	String Application=null;
        	String Event=null;
        	XYSeriesCollection XYcoll = new XYSeriesCollection();
        	XYSeries ShiftStart = new XYSeries("Shift Start");
        	XYSeries ShiftEnd = new XYSeries("Shift End");
        	XYSeries TDSEx = null;
        	
        	if (FILTER.equalsIgnoreCase("Approved Duty Hours and Exceptions")){
        		TDSEx = new XYSeries("TDS Exception");
        	}
        	else if (FILTER.equalsIgnoreCase("Approved Duty Hours and all OR and TDS events")){
        		TDSEx = new XYSeries("TDS Screen Hit");
        	}
        	
        	XYSeries ORStart	= new XYSeries("OR Start");
        	XYSeries OREnd		= new XYSeries("OR End");
        	        	
        	XYSeries Init  = new XYSeries ("");
        	long DAYS=dateHelper.inBetweenDays(STARTDATE, ENDDATE);
        	        	      	
        	if (FILTER.equalsIgnoreCase("Approved Duty Hours and all OR and TDS events")){
      
        		/*** Oracle Query 
        		
        		sql = 	"select event_date,application,event "+
        				"from rt_report_table rpt, rt_resident_master rm "+ 
        				"where rm.resident = ? "+
        				"and   rm.identifier = rpt.dictation_code "+
        				"and   event_date between to_date(?,'mm/dd/yyyy') and to_date(?,'mm/dd/yyyy hh24:mi:ss') " +
        				"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+
        				"order by application desc";
        		
        		***/
        		
        		/*** MySQL Query ***/
        		
        		sql =	"select event_date,application,event "+
        				"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
        				"where rm.resident = ? "+
        				"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+
        				"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+
        				"order by application desc";
        	}
        	else {
        		
        		/*** Oracle Query
        		
        		
        		sql = 	"select event_date,application,event "+
						"from rt_report_table rpt, rt_resident_master rm "+ 
						"where rm.resident = ? "+
						"and   rm.identifier = rpt.dictation_code "+
						"and   event_date between to_date(?,'mm/dd/yyyy') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "+
						"and   ((application in ('TDS','HSM') and rpt.exception_flag = 'Y') " +
						"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+ 
						"or application = 'New Innovations') "+ 
						"order by application desc";
        		
        		***/
        		
        		/*** MySQL Query ***/
        		
        		sql = 	"select event_date,application,event "+ 
        				"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+ 
        				"where rm.resident = ? "+
        				"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+ 
        				"and   ((application in ('TDS','HSM') and rpt.exception_flag = 'Y') "+
        						"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+ 
        						"or application = 'New Innovations') "+
        				"order by application desc";
        		
        	}
        	
        	String endDate = ENDDATE+" 23:59:59";
        	
        	ps = c.prepareStatement(sql);
			ps.setString(1, RESIDENT);
			ps.setString(2, STARTDATE);
			ps.setString(3, endDate);
			ps.setInt(4, academic_year);
			ps.setInt(5, academic_year);
			
//			System.out.println(sql);
//			System.out.println("1: "+RESIDENT);
//			System.out.println("2: "+STARTDATE);
//			System.out.println("3: "+endDate);
//			System.out.println("4: "+academic_year);
//			System.out.println("5: "+academic_year);
			
//			System.out.println("about to execute the query");
			rs = ps.executeQuery();
//			System.out.println("query returned");
			
			while (rs.next()){
				
//				System.out.println("looping through result set");
	
//				plotValue   = rs.getTimestamp(1);	
				plotValue 	= dfm.parse(rs.getString(1));
				Application = rs.getString(2);  
				Event       = rs.getString(3);
								
				X=Integer.parseInt(dateHelper.fixTime(plotValue,"day"));
				Y=Double.parseDouble(dateHelper.fixTime(plotValue,"time"));
											
				if (Application.equalsIgnoreCase("New Innovations")){
					
					if (Event.equalsIgnoreCase("Shift Start")){

  						compare = Double.compare(Y, Yhx);
  						
						if ((X==Xhx) && (compare==0) && (fetch_count==(fetch_count_hx + 1))){
							
//							System.out.println("nudging Shift Start symbol");
//							Y=Y+0.35;
							ShiftStart.add(X,Y);
							
						}
						else{
							ShiftStart.add(X,Y);
						}
						
					}
					else{
						ShiftEnd.add(X,Y);
						fetch_count_hx=fetch_count;
						Xhx=X;
						Yhx=Y;
					}
			
				}
				else if (Application.equalsIgnoreCase("TDS")){
					
					TDSEx.add(X,Y);
				}
				else if (Application.equalsIgnoreCase("HSM")){
					
					
					if (Event.equalsIgnoreCase("Caregiver In")){

						ORStart.add(X,Y);
						log.debug("HSM in plotValue: "+plotValue);
						log.debug("HSM in x: "+X);
						log.debug("HSM in y: "+Y);
						
		
					}
					else{
						OREnd.add(X,Y);
						log.debug("HSM out plotValue: "+plotValue);
						log.debug("HSM out x: "+X);
						log.debug("HSM out y: "+Y);
						
					}
							
//					HSMEx.add(X,Y);
				}
				
				fetch_count++;
			}
 
			XYcoll.addSeries(ShiftStart);
    	 	XYcoll.addSeries(ShiftEnd);
     	 	XYcoll.addSeries(TDSEx);
     	 	XYcoll.addSeries(ORStart);
     	 	XYcoll.addSeries(OREnd);    	 	
//     	 	XYcoll.addSeries(HSMEx);
 
            dataset = XYcoll;
				
        }
        
		catch (SQLException sqle){
			log.error(sqle.getMessage());
		}
		catch (Exception e){
		  	log.error("Exception in produceDataset()");
		}	
		finally {
			
			JDBCManager.closePreparedStatement(ps);
			JDBCManager.closeResult(rs);
			JDBCManager.closeConnection(c);
						
		} 
         
        return dataset;
    }

    /**
     * This producer's data is invalidated after 5 seconds. By this method the
     * producer can influence Cewolf's caching behaviour the way it wants to.
     */
	public boolean hasExpired(Map params, Date since) {		
        log.debug(getClass().getName() + "hasExpired()");
		return (System.currentTimeMillis() - since.getTime())  > 20000;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	public String getProducerId() {
		return "ProducerEX DatasetProducer";
	}

    /**
     * Returns a link target for a special data item.
     */
    public String generateLink(Object data, int series, Object category) {
        return seriesNames[series];
    }
    
	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		log.debug(this + " finalized.");
	}

}

