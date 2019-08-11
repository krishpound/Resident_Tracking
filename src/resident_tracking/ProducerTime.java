package resident_tracking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.sql.*;
import javax.sql.DataSource;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class ProducerTime implements DatasetProducer,Serializable {

	private DataSource ds; 
    
    static Logger log = Logger.getLogger("ProducerTime");
    
    private final String[] categories =    {};
    private final String[] seriesNames =   {};

	
    private Connection getConnection() throws SQLException {
   	 
		return ds.getConnection();
		
	}
 
    public Object produceDataset(Map params) throws DatasetProduceException {
    	
    	log.debug("producing data.");
   
    	System.out.println("Hello from ProducerTime!!!");
    	
    	Connection c = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String STARTDATE=null;
    	String ENDDATE=null;
    	String RESIDENT=null;
    	String FILTER=null;
    	String sql=null;
    	int fetch_count=0;
    	int fetch_count_hx=0;
    	Vector Xaxis = new Vector();
        	
    	
    	TimeSeriesCollection dataset = new TimeSeriesCollection(){	
			
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
        
        
        try{
        	    
        	System.out.println("In try block of ProducerTime!");
        	
        	ds=JDBCManager.init();
        	c = getConnection();
        	int Category=0;
        	int x_val=0;
        	Date plotValue;
        	int X=0;
        	int Xhx=0;
        	int compare=0;
        	int day=0;
        	int month=0;
        	int year=0;
        	Double Y=0.0;
        	Double Yhx=0.0;
        	String plotTime=null;
        	String Application=null;
        	String Event=null;
        	TimeSeriesCollection timeColl = new TimeSeriesCollection();
        	XYSeriesCollection XYcoll = new XYSeriesCollection();
        	TimeSeries ShiftStart = new TimeSeries("Shift Start",Day.class);
        	TimeSeries ShiftEnd = new TimeSeries("Shift End",Day.class);
        	TimeSeries TDSEx = new TimeSeries("TDS Exception",Day.class);
        	TimeSeries HSMEx = new TimeSeries("HSM Exception",Day.class);
        	long DAYS=dateHelper.inBetweenDays(STARTDATE, ENDDATE);
        	int ni_start=0;
        	int ni_end=0;
        	int tds=0;
        	int hsm=0;
        	        	      	
        	sql = 	"select event_date,application,event "+
        			"from rt_report_table rpt, rt_resident_master rm "+ 
        			"where rm.resident = ? "+
        			"and   rm.identifier = rpt.dictation_code "+
        			"and   event_date between to_date(?,'mm/dd/yyyy') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "+
        			"and   ((application in ('TDS','HSM') and rpt.exception_flag = 'Y') "+ 
        			"or application = 'New Innovations') "+ 
        			"order by event_date, event asc";
        	
        	String endDate = ENDDATE+" 23:59:59";
        	
        	ps = c.prepareStatement(sql);
			ps.setString(1, RESIDENT);
			ps.setString(2, STARTDATE);
			ps.setString(3, endDate);
		
			rs = ps.executeQuery();
			
			while (rs.next()){
	
				System.out.println("fetching record in ProducerTime");
				
				
				plotValue   = rs.getTimestamp(1);	
				Application = rs.getString(2);  
				Event       = rs.getString(3);												
				day   = dateHelper.parseDate(plotValue, "day");
				month = dateHelper.parseDate(plotValue, "month");
				year  = dateHelper.parseDate(plotValue, "year");
				Y=Double.parseDouble(dateHelper.fixTime(plotValue,"time"));
				
				System.out.println("event="+Event);
				System.out.println("day="+day+" month="+month+" year="+year);
				
				
											
				if (Application.equalsIgnoreCase("New Innovations")){
					
					System.out.println("New Innovations event");
					
					if (Event.equalsIgnoreCase("Shift Start")){
						System.out.println("shift start event");
						
						ShiftStart.addOrUpdate(new Day(day,month,year),Y);
						
						ni_start++;
						System.out.println("shift start count = "+ni_start);
						
					}
					else{
						System.out.println("shift end event");
						ShiftEnd.add(new Day(day,month,year),Y);
						ni_end++;
						System.out.println("shift end count = "+ni_end);
					}
			
				}
				else if (Application.equalsIgnoreCase("TDS")){
					System.out.println("tds event");
					TDSEx.add(new Day(day,month,year),Y);
					tds++;
				}
				else if (Application.equalsIgnoreCase("HSM")){
					System.out.println("hsm event");
					HSMEx.add(new Day(day,month,year),Y);
					hsm++;
				}
				
				fetch_count++;
				System.out.println("fetch count="+fetch_count);
			}
        	
			
       	 	timeColl.addSeries(ShiftEnd);
       	 	timeColl.addSeries(HSMEx);
       	 	timeColl.addSeries(ShiftStart);
       	 	timeColl.addSeries(TDSEx);
       	 	
            dataset = timeColl;
            
            System.out.println("Series have been added to TimeSeriesCollection dataset.");
            System.out.println("shift starts = "+ni_start);
            System.out.println("shift ends   = "+ni_end);
            System.out.println("tds series   = "+tds);
            System.out.println("hsm series   = "+hsm);
            
        }
        
		catch (SQLException sqle){
			log.error(sqle.getMessage());
			System.out.println("SQLException in ProducerTime");
		}
		catch (Exception e){
		  	log.error("Exception in produceDataset()");
		  	System.out.println("ProducerTime: Here is the exception\n");
		  	e.printStackTrace();
		  	
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

