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

public class ProducerEX implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, 
Serializable {

	private DataSource ds; 
    
    static Logger log = Logger.getLogger("ProducerEX");
    
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
    	System.out.println("Hello from ProducerEX!");
   
    	Connection c = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String STARTDATE=null;
    	String ENDDATE=null;
    	String RESIDENT=null;
    	String FILTER=null;
    	String ACADEMIC_YEAR=null;
    	int    academic_year=0;
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
        	String [] Series = {"OR","TDS"};
        	String Category=null;
        	int plotValue=0;
        	String Application=null;
        	long DAYS=dateHelper.inBetweenDays(STARTDATE, ENDDATE);
        	
        	for (int k=0;k<DAYS;k++){
        		Xaxis.addElement(dateHelper.rollDate(STARTDATE,"DAY",k));	
        	}
        	
        	for (int ch=0;ch<Xaxis.size();ch++){
        		dataset.addValue(0, Series[0], (String)Xaxis.elementAt(ch));
        		dataset.addValue(0, Series[1], (String)Xaxis.elementAt(ch));
        	}
      
        	/*** Oracle Query 
        	
        	sql = 	"select to_char(event_date,'mm/dd'),application,count(*) "+ 
        			"from rt_report_table rpt, rt_resident_master rm "+
        			"where rm.resident = ? "+
        			"and   rm.identifier = rpt.dictation_code "+ 
        			"and   event_date between to_date(?,'mm/dd/yyyy') and to_date(?,'mm/dd/yyyy hh24:mi:ss') "+ 
        			"and   rpt.exception_flag = 'Y' " +
        			"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+				
        			"group by to_char(event_date,'mm/dd'),application "+ 
        			"order by to_char(event_date,'mm/dd') asc";
        	
        	***/
        	
        	/*** MySql Query ***/
        	
        	sql = 	"select left(event_date,5),application,count(*) "+ 
        			"from rt_report_table rpt join rt_resident_master rm on (rpt.dictation_code = rm.identifier) "+
        			"where rm.resident = ? "+
        			"and str_to_date(event_date,'%m/%d/%Y %H:%i') between str_to_date(?,'%m/%d/%Y') and str_to_date(?,'%m/%d/%Y %H:%i') "+
					"and   rpt.exception_flag = 'Y' " +
        			"and   rpt.academic_year_key = ? and rm.academic_year_key = ? "+				
        			"group by left(event_date,5),application "+ 
        			"order by left(event_date,5) asc";
        	
        	String endDate = ENDDATE+" 23:59:59";
        	
        	ps = c.prepareStatement(sql);
			ps.setString(1, RESIDENT);
			ps.setString(2, STARTDATE);
			ps.setString(3, endDate);
			ps.setInt(4, academic_year);
			ps.setInt(5, academic_year);
		
			rs = ps.executeQuery();
		
			while (rs.next()){
			
				Category  = rs.getString(1);
				Application = rs.getString(2);
				plotValue = rs.getInt(3);
				Category=dateHelper.fixDate(Category);
			
				if (Application.equalsIgnoreCase("hsm")){
					dataset.addValue(plotValue, Series[0], Category);	
				}
				else{
					dataset.addValue(plotValue, Series[1], Category);	
				}
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

	/**
	 * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return seriesNames[series];
	}

}

