package resident_tracking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.sql.*;
import javax.sql.DataSource;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class ProducerNI implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, 
Serializable {

	private DataSource ds; 
    
    static Logger log = Logger.getLogger("ProducerNI");
    
    private final String[] categories =    {};
    private final String[] seriesNames =   {};

	
    private Connection getConnection() throws SQLException {
   	 
		return ds.getConnection();
		
	}
 
    
    public static String[] defineCategories(String sd, String ed){
		
		String [] temp = new String[1];	
	
		return temp;
		
	}
    
    public Object produceDataset(Map params) throws DatasetProduceException {
    	
    	log.debug("producing data.");
    	System.out.println("Hello from ProducerNI!");
   
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
    	Vector Xaxis = new Vector();
    	
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset(){
			
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
        	String Series="New Innovations";
        	String Category=null;
        	int plotValue=0;
        	long DAYS=dateHelper.inBetweenDays(STARTDATE, ENDDATE);
        	
        	for (int k=0;k<DAYS;k++){
        		Xaxis.addElement(dateHelper.rollDate(STARTDATE,"DAY",k));	
        	}
        	
        	for (int ch=0;ch<Xaxis.size();ch++){
        		dataset.addValue(0, Series, (String)Xaxis.elementAt(ch));
        		
        	}
        	
        	/*** Oracle Query
        	String sql_select = 	"select to_char(event_date,'mm/dd'),count(*) "+ 
        							"from rt_report_table rpt, rt_resident_master rm "+ 
        							"where rm.resident = ? "+
        							"and   rm.identifier = rpt.dictation_code "+ 
        							"and   rpt.application = 'New Innovations' "+
        							"and   event_date between to_date(?,'mm/dd/yyyy') and to_date(?,'mm/dd/yyyy hh24:mi:ss')" +
        							"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "; 
        	***/				
        	
        	/*** MySql Query ***/
        	
        	String sql_select = 	"select left(event_date,5),count(*) "+
        							"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
        							"where rm.resident = ? "+
        							"and   rpt.application = 'New Innovations' "+
        							"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+
        							"and   rpt.academic_year_key = ? and rm.academic_year_key = ? ";
        	
        	String sql_filter =		"and   rpt.exception_flag = 'Y' ";						
        							
//        	String sql_grouping = 	"group by to_char(event_date,'mm/dd') "+
//        							"order by to_char(event_date,'mm/dd') asc ";
        	
        	String sql_grouping = 	"group by left(event_date,5) "+
									"order by left(event_date,5) asc ";
        
        	sql = sql_select+sql_grouping;
        	
        	String endDate = ENDDATE+" 23:59:59";
        	
        	System.out.println("preparing the sql statement now");
        	
        	ps = c.prepareStatement(sql);
			ps.setString(1, RESIDENT);
			ps.setString(2, STARTDATE);
			ps.setString(3, endDate);
			ps.setInt(4, academic_year);
			ps.setInt(5, academic_year);
		
			System.out.println("executing the query now");
			
			rs = ps.executeQuery();
			
			System.out.println("query returned");
		 
			while (rs.next()){
			
				System.out.println("Category == "+rs.getString(1));
				System.out.println("plotValue == "+rs.getInt(2));
				
				
				Category  = rs.getString(1);
				plotValue = rs.getInt(2);
//				plotValue = Integer.parseInt(rs.getString(2));
				
				Category=dateHelper.fixDate(Category);
				
				dataset.addValue(plotValue, Series, Category);	
				
			}
        	
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
		return "ProducerTDS DatasetProducer";
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

	/**
	 * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return seriesNames[series];
	}

}

