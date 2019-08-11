package resident_tracking;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.format.Alignment;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.WritableCellFormat;
import jxl.write.biff.RowsExceededException;
import org.apache.log4j.Logger;

public class ActionDownload extends HttpServlet {
	
	static Logger log = Logger.getLogger("ActionDownload");
				 
	public ActionDownload() {
		super();
	}
 
	public void destroy() {
		super.destroy(); 
	}
		   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request,response);
			
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			HttpSession session = request.getSession( ); 
			String report = request.getParameter("filename");
			Iterator itr;
			String resident;
			String TDS;
			String HSM;
			String TOTAL;
			String tds[];
			String hsm[];
			String total[];
			String reported_duty;
			String program;
			String application;
			String event;
			String time;
			String tds_screen;
			String duty_type;
			String status;
			String exception;
			int count=1;
			List rs=null;
			
			if (report.equalsIgnoreCase("Resident_Summary")){ 
				rs = (List)session.getAttribute("DATASET");
			}
			else if (report.equalsIgnoreCase("Resident_Detail")){ 
				rs = (List)session.getAttribute("DETAILSET");
			}

			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment; filename="+report+".xls");
		   
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet s = w.createSheet("Demo", 0);
			WritableCellFormat c = new WritableCellFormat();
			
			try{
				
				c.setAlignment(Alignment.CENTRE);
				
				if (report.equalsIgnoreCase("Resident_Summary")){
				
					s.setColumnView(0,25);
					s.setColumnView(1,45);
					s.setColumnView(2,23);
					s.setColumnView(3,18);
					s.setColumnView(4,22);
					s.setColumnView(5,18);
					s.setColumnView(6,21);
					s.setColumnView(7,21);
					s.setColumnView(8,23);
					s.addCell(new Label(0,0,"RESIDENT",c));
					s.addCell(new Label(1,0,"PROGRAM",c));
					s.addCell(new Label(2,0,"HAS REPORTED DUTY",c));
					s.addCell(new Label(3,0,"TDS EXCEPTIONS",c));
					s.addCell(new Label(4,0,"TDS EXCEPTION DAYS",c));
					s.addCell(new Label(5,0,"HSM EXCEPTIONS",c));
					s.addCell(new Label(6,0,"HSM EXCEPTION DAYS",c));
					s.addCell(new Label(7,0,"TOTAL EXCEPTIONS",c));
					s.addCell(new Label(8,0,"TOTAL EXCEPTION DAYS",c));
				
					for (itr=rs.iterator(); itr.hasNext();){
					
						resident = (String) itr.next().toString();
						TDS = (String) itr.next().toString();
						HSM  = (String) itr.next().toString();
						reported_duty = (String) itr.next().toString();
						TOTAL = (String) itr.next().toString();
						program = (String) itr.next().toString();
						tds = TDS.split("/");
						hsm = HSM.split("/");
						total = TOTAL.split("/");
					
						s.addCell(new Label(0,count,resident));
						s.addCell(new Label(1,count,program));
						s.addCell(new Label(2,count,reported_duty,c));
						s.addCell(new Number(3,count,Integer.parseInt(tds[0].trim()),c));
						s.addCell(new Number(4,count,Integer.parseInt(tds[1].trim()),c));
						s.addCell(new Number(5,count,Integer.parseInt(hsm[0].trim()),c));
						s.addCell(new Number(6,count,Integer.parseInt(hsm[1].trim()),c));
						s.addCell(new Number(7,count,Integer.parseInt(total[0].trim()),c));
						s.addCell(new Number(8,count,Integer.parseInt(total[1].trim()),c));
					
						count++;
					
					}
					
				}
				
				if (report.equalsIgnoreCase("Resident_Detail")){
					
					s.setColumnView(0,15);
					s.setColumnView(1,14);
					s.setColumnView(2,20);
					s.setColumnView(3,16);
					s.setColumnView(4,16);
					s.setColumnView(5,12);
					s.setColumnView(6,16);
					s.addCell(new Label(0,0,"APPLICATION",c));
					s.addCell(new Label(1,0,"EVENT",c));
					s.addCell(new Label(2,0,"TIME",c));
					s.addCell(new Label(3,0,"TDS SCREEN",c));
					s.addCell(new Label(4,0,"DUTY TYPE",c));
					s.addCell(new Label(5,0,"STATUS",c));
					s.addCell(new Label(6,0,"EXCEPTION",c));
					
					for (itr=rs.iterator(); itr.hasNext();){
					
						application = (String) itr.next().toString();
						event = (String) itr.next().toString();
						time  = (String) itr.next().toString();
						tds_screen = (String) itr.next().toString();
						duty_type = (String) itr.next().toString();
						status = (String) itr.next().toString();
						exception = (String) itr.next().toString();
						
						if (application.equalsIgnoreCase("TDS")){
							
							if (tds_screen.equalsIgnoreCase("&nbsp;")){
								tds_screen="";
							}
							
						}
						
						s.addCell(new Label(0,count,application));
						s.addCell(new Label(1,count,event));
						s.addCell(new Label(2,count,time,c));
						s.addCell(new Label(3,count,tds_screen,c));
						s.addCell(new Label(4,count,duty_type,c));
						s.addCell(new Label(5,count,status,c));
						s.addCell(new Label(6,count,exception,c));
						
						count++;
					
					}
					
				}
								
				w.write();
				w.close();
				
			}
			catch(RowsExceededException ree){
				log.error("rows exceeded exception happened");
			}
			catch(WriteException we){
				log.error("write exception happened");
			}
		}
	
	public void init() throws ServletException {
			
	}

}

