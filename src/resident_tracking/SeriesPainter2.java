package resident_tracking;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import de.laures.cewolf.ChartPostProcessor;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RectangleInsets; 
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryTick;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.DateAxis;
import java.text.ParseException;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class SeriesPainter2 implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -2290498142826058256L;
	
	static Logger log = Logger.getLogger("SeriesPainter");
	
	public static final Paint DEFAULT_AXIS_LABEL_PAINT = Color.BLUE;
	public static final Font  DEFAULT_TITLE_FONT = new Font("Verdana", Font.BOLD, 14);

	public void processChart (Object chart, Map params) {
		
		System.out.println("\nPROCESSING IN SERIESPAINTER2\n");
				
    	JFreeChart localChart = (JFreeChart) chart;
    	    
    	Plot p = (Plot) localChart.getPlot();
    	
    	System.out.println("SERIES PAINTER PLOT TYPE IS "+p.getPlotType());
    	
    	
    	if (p instanceof XYPlot){ 
    	
    		System.out.println("THIS IS AN XYPLOT");
    				
    			System.out.println("IN TRY BLOCK");
    	
    			
    			//Customize the Domain Axis (dateAxis = Domain or X-axis)
    			String startDate = (String)params.get(String.valueOf("startDate"));
    			String endDate   = (String)params.get(String.valueOf("endDate"));
    			
    			System.out.println("startDate="+startDate);
    			System.out.println("endDate="+endDate);
    			
    			String DATE_FORMAT = "mm/dd/yyyy";
    			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    			SimpleDateFormat day = new SimpleDateFormat("dd");
    			GregorianCalendar c1 = new GregorianCalendar();
    			GregorianCalendar c2 = new GregorianCalendar();
    			
    			try{
    			Date d1 = sdf.parse(startDate);
    			Date d2 = sdf.parse(endDate);
    			
    			
    			c1.setTime(d1);
    			c2.setTime(d2);
    		
    			System.out.println("SeriesPainter: d1="+sdf.format(d1));
    			System.out.println("SeriesPainter: d2="+sdf.format(d2));	
    			
    			XYPlot plot = (XYPlot) localChart.getPlot();    	
    			XYItemRenderer renderer = plot.getRenderer();
    		
    			System.out.println("setting up domain axis");
    			DateAxis dateAxis = (DateAxis)plot.getDomainAxis();
    			dateAxis.setRange(d1, d2);
    		
    			System.out.println("domain axis is set");
    
//Customize the Domain (X Axis)
    		
//    		DateAxis dateAxis = (DateAxis)plot.getDomainAxis();
//    		dateAxis.setRange(lower, upper)
    		
    		

    		
    			//Customize the Range Axis (axis = Range or Y Axis)
    			ValueAxis axis = plot.getDomainAxis();
    			axis = plot.getRangeAxis();
    			((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
    			axis.setRange(0,24);
    		
    		//Set colors for labels and ticks
      		dateAxis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
      		dateAxis.setLabelFont(DEFAULT_TITLE_FONT);
      		dateAxis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
      		dateAxis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		
//    			dateAxis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
//    			dateAxis.setLabelFont(DEFAULT_TITLE_FONT);
//    			dateAxis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
//    			dateAxis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		
    			axis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
    			axis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    			axis.setLabelFont(DEFAULT_TITLE_FONT);
    			axis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		//Set colors for series and plot image area		   		
    			plot.setBackgroundPaint(new Color(102, 102, 102));		
    			plot.setDomainGridlinePaint(new Color(255, 255, 255));		
    			plot.setRangeGridlinePaint(new Color(240, 240, 240));		
    			plot.setDomainCrosshairVisible(true);		
    			plot.setRangeCrosshairVisible(true);		
    			plot.setDomainGridlinesVisible(true);  
    			renderer.setSeriesPaint(0, Color.red);
    			renderer.setSeriesPaint(1, Color.blue);
    			renderer.setSeriesPaint(2, Color.green);
    			renderer.setSeriesPaint(3, Color.yellow);
    		
    			}
    			catch(Exception e){
    				e.printStackTrace();
    			}
    		
    	
    	}
    	else if (p instanceof CategoryPlot) {
			
    		CategoryPlot catPlot = (CategoryPlot) p;
    		catPlot.setBackgroundPaint(new Color(102, 102, 102));
    		
    		CategoryAxis categoryAxis = (CategoryAxis)catPlot.getDomainAxis();
    		
/*    		
    		categoryAxis.
    		categoryAxis.setTickUnit(new CategoryTick);
    		ValueAxis axis = categoryAxis.getDomainAxis();
    		axis = plot.getRangeAxis();
    		((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
    		axis.setRange(0,24);
*/    		
    		
   		
//    		catPlot.get
    		
    		
    		
    		String graphType = (String) params.get(String.valueOf("producer"));
    		
			if (graphType.equalsIgnoreCase("dataex")){
				catPlot.getRenderer().setSeriesPaint(0, Color.blue);
				catPlot.getRenderer().setSeriesPaint(1, Color.yellow);
			}
			else if (graphType.equalsIgnoreCase("datatds")){
				catPlot.getRenderer().setSeriesPaint(0, Color.yellow);
			}
			else if (graphType.equalsIgnoreCase("datahsm")){
				catPlot.getRenderer().setSeriesPaint(0, Color.blue);
			}
			else if (graphType.equalsIgnoreCase("datani")){
				catPlot.getRenderer().setSeriesPaint(0, Color.green);
			}
					
		}
    	
	}
}
