package resident_tracking;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jfree.chart.axis.MarkerAxisBand;
import org.jfree.util.ShapeUtilities;
import de.laures.cewolf.ChartPostProcessor;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.title.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RectangleInsets; 
import org.jfree.data.Range;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryTick;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.DateAxis;
import java.text.ParseException;
//import org.jboss.logging.Logger;
import org.apache.log4j.Logger;

public class SeriesPainter implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -2290498142826058256L;
	
	static Logger log = Logger.getLogger("SeriesPainter");
	
	public static final Paint DEFAULT_LEGEND_LABEL_PAINT = Color.WHITE;
	public static final Paint DEFAULT_AXIS_LABEL_PAINT = Color.decode("#03328A");
	public static final Paint DEFAULT_LEGEND_BACKGROUND = Color.decode("#666666");
	public static final Font  DEFAULT_TITLE_FONT = new Font("Verdana", Font.BOLD, 14);
	public static final Font  DEFAULT_LEGEND_FONT = new Font ("Verdana", Font.PLAIN, 14);
	
	public void processChart (Object chart, Map params) {
				
    	JFreeChart localChart = (JFreeChart) chart; 
    	
    	BlockFrame bf = new BlockBorder(DEFAULT_AXIS_LABEL_PAINT);
    	LegendTitle legend = localChart.getLegend();
    	legend.setItemPaint(DEFAULT_LEGEND_LABEL_PAINT);
    	legend.setItemFont(DEFAULT_LEGEND_FONT);
    	legend.setBackgroundPaint(DEFAULT_LEGEND_BACKGROUND);
    	legend.setFrame(bf);
    	
    	Plot p = (Plot) localChart.getPlot();
    	
    	if (p instanceof XYPlot){ 
    			
    		Double d1 = dateHelper.fixDateDouble((String) params.get(String.valueOf("startDate")));
    		Double d2 = dateHelper.fixDateDouble((String) params.get(String.valueOf("endDate")));
    		Float f = 3.5f;
//    		Float f1 = 5.0f;
    		Float f1 = 4.75f;
    		
    		//Customize the Domain (X Axis)
    		XYPlot plot = (XYPlot) localChart.getPlot();    	
			XYItemRenderer renderer = plot.getRenderer();
			
    		NumberAxis numberAxis = (NumberAxis)plot.getDomainAxis();
    		numberAxis.setTickUnit(new NumberTickUnit(1));
   		    		    		
    		int rg = Double.compare(d1, d2);
    		
    		if (rg != 0){
    			numberAxis.setRange(d1-.5,d2+.5);
    		}
    		else{
    			numberAxis.setRange(d1-.5,d1+.5);
    		}
    		    		
    		//Customize the Range Axis (axis = Range or Y Axis)
    		ValueAxis axis = plot.getDomainAxis();
    		axis = plot.getRangeAxis();
    		((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
    		axis.setRange(0,24);
    		
    		//Set colors for labels and ticks
      		numberAxis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
      		numberAxis.setLabelFont(DEFAULT_TITLE_FONT);
      		numberAxis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
      		numberAxis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
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
    		
    		Shape uptriangle = ShapeUtilities.createUpTriangle(f);
    		Shape downtriangle = ShapeUtilities.createDownTriangle(f);
    		Shape ORuptriangle = ShapeUtilities.createUpTriangle(f1);
    		Shape ORdowntriangle = ShapeUtilities.createDownTriangle(f1);
    		
    		
    		Shape diamond   = ShapeUtilities.createDiamond(f);
    		Shape square = new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0); 
    		Shape circle = new Ellipse2D.Double(-3.0,-3.0,6.0,6.0);
    		
    		renderer.setSeriesShape(0, uptriangle);
    		renderer.setSeriesShape(1, downtriangle);
    		renderer.setSeriesShape(2, diamond);
    		renderer.setSeriesShape(3, ORuptriangle);
    		renderer.setSeriesShape(4, ORdowntriangle);
    		renderer.setSeriesPaint(0, Color.blue);
    		renderer.setSeriesPaint(1, Color.blue);
//    		renderer.setSeriesPaint(2, new Color(255,255,0,65));
    		renderer.setSeriesPaint(2, Color.yellow);
    		renderer.setSeriesPaint(3, Color.red);
    		renderer.setSeriesPaint(4, Color.red);
    	
    	}
    	else if (p instanceof CategoryPlot) {
			
    		CategoryPlot catPlot = (CategoryPlot) p;
    		catPlot.setBackgroundPaint(new Color(102, 102, 102));
    		ValueAxis axis = catPlot.getRangeAxis();
    		CategoryAxis domainAxis = catPlot.getDomainAxis();
    		Range r = catPlot.getDataRange(axis);
    		int lower = Double.compare(r.getLowerBound(), 0.0);
    		int upper = Double.compare(r.getUpperBound(),0.0);
    		domainAxis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
    		domainAxis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		domainAxis.setLabelFont(DEFAULT_TITLE_FONT);
    		domainAxis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		axis.setAxisLinePaint(DEFAULT_AXIS_LABEL_PAINT);
    		axis.setLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		axis.setLabelFont(DEFAULT_TITLE_FONT);
    		axis.setTickLabelPaint(DEFAULT_AXIS_LABEL_PAINT);
    		
    		if (lower==0 && upper==0){
    			((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
    		}
    		
    		CategoryAxis categoryAxis = (CategoryAxis)catPlot.getDomainAxis();
    		    		  			
    		String graphType = (String) params.get(String.valueOf("producer"));
    		
			if (graphType.equalsIgnoreCase("dataex")){
				catPlot.getRenderer().setSeriesPaint(0, Color.red);
				catPlot.getRenderer().setSeriesPaint(1, Color.yellow);
			}
			else if (graphType.equalsIgnoreCase("datatds")){
				catPlot.getRenderer().setSeriesPaint(0, Color.yellow);
			}
			else if (graphType.equalsIgnoreCase("datahsm")){
				catPlot.getRenderer().setSeriesPaint(0, Color.red);
				axis.setRange(0,16);
				((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
			}
			else if (graphType.equalsIgnoreCase("datani")){
				catPlot.getRenderer().setSeriesPaint(0, Color.blue);
				axis.setRange(0,16);
	    		((NumberAxis) axis).setTickUnit(new NumberTickUnit(1));
			}
					
		}
    	
	}
}
