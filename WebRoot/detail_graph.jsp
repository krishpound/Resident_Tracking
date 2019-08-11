<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="resident_tracking.*"%>
<%@page import="java.awt.*" %>
<%@page import="de.laures.cewolf.*"%>
<%@page import="de.laures.cewolf.cpp.*"%>
<%@page import="de.laures.cewolf.links.*"%>
<%@page import="de.laures.cewolf.taglib.CewolfChartFactory" %>
<%@page import="de.laures.cewolf.tooltips.*"%>
<%@page import="org.jfree.chart.*"%>
<%@page import="org.jfree.chart.event.ChartProgressEvent" %>
<%@page import="org.jfree.chart.event.ChartProgressListener" %>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.category.*"%>
<%@page import="org.jfree.data.gantt.*"%>
<%@page import="org.jfree.data.general.*"%>
<%@page import="org.jfree.data.time.*"%>
<%@page import="org.jfree.data.xy.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ChartPostProcessor SeriesPainter = new SeriesPainter(); 
pageContext.setAttribute("SeriesPainter", SeriesPainter);
%>  

<jsp:include page="/header.do" flush="true"/>  
<jsp:include page="/querymenus.do" flush="true"/> 
<jsp:useBean id="HEADER_SECTION" type="resident_tracking.HeaderBean" scope="session" />
<jsp:useBean id="QUERYPARM" type="resident_tracking.FormBean" scope="request" /> 

<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<jsp:useBean id="dataTDS" class="resident_tracking.ProducerTDS"/>
<jsp:useBean id="dataHSM" class="resident_tracking.ProducerHSM"/>
<jsp:useBean id="dataNI" class="resident_tracking.ProducerNI"/>
<jsp:useBean id="dataEX" class="resident_tracking.ProducerEX"/>
<jsp:useBean id="dataALL" class="resident_tracking.ProducerALLTime"/>




<% if (HEADER_SECTION.getAuthorized()) { %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--    "http://www.w3.org/TR/html4/loose.dtd">    --> 
 
<html>
    <head>
        
       	<meta http-equiv="Content-Language" content="en-us"> 
   		<link rel="stylesheet" type="text/css" href="css/menu.css"/>
   		
    	<link rel="stylesheet" type="text/css" href="css/rt.css"/>
    	<link rel="stylesheet" type="text/css" href="css/combo.css"/>
    	<link rel="stylesheet" type="text/css" href="css/autocomplete.css" /> 
    	<link rel="stylesheet" type="text/css" href="css/DatePicker.css"/>
    	<script language="JavaScript" type="text/javascript" src="jscript/menu.js"></script> 
    	<script language="JavaScript" type="text/javascript" src="jscript/menu_items.js"></script> 
    	<script language="JavaScript" type="text/javascript" src="jscript/menu_tpl.js"></script>
    	<script language="JavaScript" type="text/javascript" src="jscript/popup_calendar.js"></script>
    	
        <title>Resident Activity Detail Query</title>
        
    </head> 
    
	  <body class="common" bgcolor="#C3D9FF"> 
	               
		<script language="JavaScript">
			new menu (MENU_ITEMS, MENU_POS);
		</script>               
              	 
        <script language="JavaScript" type="text/javascript">
	  		function initializeCalendar(){
	  		
	  			var initDate = document.getElementById('startDate').value;
	  			displayDatePicker('endDate',null,null,null,initDate);
	  			
	  		}	
	  	</script>            	 
              	             	                 
<!-- Top Menu Bar -->

	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="0" cellpadding="5" width="100%" border="0" height="10">
          <tr>	
          	<td valign="top" style="text-align:left" nowrap="nowrap" bgcolor="#e8eefa" height="20">
           		<b><a href="./changeYear.jsp">ACADEMIC YEAR: <%=HEADER_SECTION.getAcademicYear()%></a></b>
           	</td>   
           	
           	<td valign="top" style="text-align:right" nowrap="nowrap" bgcolor="#e8eefa" height="20">  	
           	<a href=".">Home</a>&nbsp;|&nbsp;<a href="./logout.jsp">Logout</a>
           	</td>
           	
           			
          </tr>        
   	</table>
        
<!-- Page Header Section -->

        
<!-- TABLE #2 START: outer table around entire Page Header section -->    
        
        <table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width="100%" border="0"
        
        	<% 
        
        		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
        			out.print("height=\"147\"");
        		}
        		else{
        			out.print("height=\"163\"");
        		}
        		
         	%>>
        
          <tr>
          	<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="133">


<!-- <table style="border:0px none" width=250 height=48 border=2 cellspacing=2 cellpadding=0><col width="200"><col width="50" align="left"> -->

<table style="border:0px none" width="100%" height=48 border=2 cellspacing=2 cellpadding=0><col width="800"><col width="300">

	<tr></tr><tr></tr><tr></tr>
	<tr>
		<td class="header">Welcome To :</td>
		<td style="border:0px none;text-align:left">&nbsp;<%=HEADER_SECTION.getWelcomeName()%></td>
	</tr>	

	<tr>
		<td class="header">Position : </td>
		<td style="border:0px none;text-align:left">&nbsp;<%=HEADER_SECTION.getTitle()%></td>
		
	</tr>

</table>

<!-- gif table start -->

<div id="page_header" style="position:absolute;left:10;top:65;width:100%;height:150px;">

<table style="border:0px none" width="100%" height=48 border=0 cellspacing=2 cellpadding=0>
        
        <tr>
          <td width="27" height="27"></td> 
          <td height="57" width="62" rowspan="3" valign="top"><img src="images/home.gif" width="62" height="57" alt=""></td>
          <td width="327" height="27" colspan="3"></td>
          <td width="2" height="27"></td>
          <td height="27"></td> <!-- workaround for IE table layout bug -->
        </tr>
        <tr>
          <td width="27" height="3"></td>
          <td width="27" height="3"></td>
          <td style="border:0px none" nowrap height="30" width="225" rowspan="2" valign="top" bgcolor="#e8eefa"><span class="text"><b><i><font face="Verdana" color="#03328A" size="5"><span style="font-size:26px;line-height:30px;color:03328a">R</span></font></i></b><i><font face="Verdana" color="#03328A" size="4"><span style="font-size:22px;line-height:28px;color:03328a">esident </span></font></i><b><i><font face="Verdana" color="#03328A" size="5"><span style="font-size:26px;line-height:30px;">T</span></font></i></b><i><font face="Verdana" color="#03328A" size="4"><span style="font-size:22px;line-height:28px;">racking<br soft></span></font></i></span></td>
          <td width="75" height="3"></td>
          <td width="2" height="3"></td>
          <td height="3"></td> <!-- workaround for IE table layout bug -->
        </tr>
        <tr>
          <td width="27" height="27"></td>
          <td width="27" height="27"></td>
          <td width="396" height="27" colspan="3"></td>
          <td height="27"></td> <!-- workaround for IE table layout bug -->
        </tr>
        <tr>
          <td width="737" height="56" colspan="7"></td>
          <td height="56"></td> <!-- workaround for IE table layout bug -->
        </tr>
        <tr> <!-- workaround for IE table layout bug -->
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
      </table>

</div>

<!--  gif table end -->

			</table>

          	<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          		
        </table>

<!-- Query Panel Form Section Start -->

	<% 
        
   		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
        	out.print("<div id=\"query_panel\" style=\"position:absolute;left:10;top:198;width:100%;height:200px;\">");
        }
        	
 	%>
	
<form name="DetailGraph" action="detail.do" method="post">

	<!--  outer panel table start -->
	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width=100% border="0" height="210" align="center">
        
          <tr>
          		<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="180">
          		
          		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=300 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Resident Activity Detail Query</span>	
          				</td>
        			</tr>
				</table>
                 		 
          <!--  inner panel table start -->
          		     		
          		<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          			<tr></tr>
          			<tr></tr>
           			<tr></tr>    
          			<tr>
        				<td colspan="2" align="right" height="30"><span class = lable>Start Date :&nbsp;</span></td>
        				<td><input class="label"  type="text" id="startDate" name="startDate" size="12" 
        				value=<jsp:getProperty name="QUERYPARM" property="startDate"/>>
        					
        					<img src="images/calendar.gif" width="16" height="16" border="0" alt="Pick a date"
        						onclick="displayDatePicker('startDate');">           					
						</td>       				
        				       				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				<td colspan="2" align="right" height="30"><span class = lable>Filter :&nbsp;</span></td>
        				<td><select class="filter" name="menuFilter">
        				
        				 <c:forEach items="${HEADER_SECTION.detailFilterList}" var="item"> 
        						<c:choose>
  									<c:when test="${QUERYPARM.filter==item}">
  										<option selected="selected">${item}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${item}</option>
      								</c:otherwise>
  								</c:choose>
        					</c:forEach>
        				    					 					    							
        				</select></td>
        				 				
        			</tr>
        			<tr> 
        				<td colspan="2" align="right" height="30"><span class = lable>End Date :&nbsp;</span></td>
        				<td><input class="label"  type="text" name="endDate" size="12"
        				value=<jsp:getProperty name="QUERYPARM" property="endDate"/>>
        						
        					<img src="images/calendar.gif" width="16" height="16" border="0" alt="Pick a date"
        						onclick="initializeCalendar();"> 	
        						
        				</td> 
        				
        				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				<td colspan="2" align="right" height="30"><span class = lable>Sort Order :&nbsp;</span></td>
        				
        				<td><select name="menuSort">
        					 
        					 <c:forEach items="${HEADER_SECTION.detailSortList}" var="sort"> 
        						<c:choose>
  									<c:when test="${QUERYPARM.sortOrder==sort}">
  										<option selected="selected">${sort}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${sort}</option>
      								</c:otherwise>
  								</c:choose>
        					</c:forEach>
      					    
        				</select></td>
        				       				
        			</tr>
        			
        			<tr>
        			
        			<td colspan="2" align="right" height="30"><span class = lable>Report Style :&nbsp;</span></td>
        				
        				<td><select class="sort" name="menuReportStyle">
        				
        					 <c:forEach items="${HEADER_SECTION.menuStyleList}" var="style"> 
        						<c:choose>
  									<c:when test="${QUERYPARM.reportStyle==style}">
  										<option selected="selected">${style}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${style}</option>
      								</c:otherwise>
  								</c:choose>
        					</c:forEach>
        					 
        				</select></td>
        			
        			<td>&nbsp;&nbsp;&nbsp;</td>	
   		
        				<td colspan="2" align="right" height="30"><span class = lable>Resident :&nbsp;</span></td>
        				
        				<td><select name="menuResident">
        				
        					<c:set var="selection" value="${QUERYPARM.resident}" scope="request"/>
							
							<c:forEach items="${sessionScope.RESIDENT_LIST}" var="resident"> 
        						<c:choose>
  									<c:when test="${resident==selection}">
  										<option selected="selected">${resident}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${resident}</option>
      								</c:otherwise>
  								</c:choose>
        					</c:forEach>
        					 
        					</select></td>
 		
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				
        		
        			</tr>
        			
        			<tr></tr>
        			<tr></tr>
        			                			
          		</table>
          		
          		<center>
          		<span class="text"><font face="Verdana" color="#03328A">	
          		<input class="formButton" type="submit" value="Run Query" name="B4" >&nbsp;&nbsp;
          		</font></span>
          		</center>
      		         	
        </tr>
	</table>
	 
</form>	

	<% 
		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
    		out.print("</div>");
    	}
    %>		

	<div id="HELP" style="position:absolute;left:10;top:420;width:100%;height:200px;">
			<center><font color="red">
			<% 
			
				if (QUERYPARM.getMessage1() != null)
					out.print("<br><img src=\"images/error.gif\">&nbsp;"+QUERYPARM.getMessage1()+"<br><br>");
			
				if (QUERYPARM.getMessage2() != null)
					out.print(QUERYPARM.getMessage2()+"<br><br>");
					 
				if (QUERYPARM.getMessage3() != null)
					out.print(QUERYPARM.getMessage3()+"<br><br>");	 
					 					 					 					 
					 					 
			%>
			
			</font></center></div>


<%  if (null==QUERYPARM.getMessage1()){   %>

	<% 
		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
    		out.print("<div id=\"result_set\" style=\"position:absolute;left:10;top:430;width:100%;\">");
    	}
    %>		
    
     
<br>
<center><span style="font-size: 16px;font-family: Verdana"><b><%=QUERYPARM.getChartTitle().trim()%></b></span> 
<span style="font-size: 14px;font-family: Verdana"><b>:&nbsp;&nbsp;<%=QUERYPARM.getStartDate()%>&nbsp;-&nbsp;<%=QUERYPARM.getEndDate()%>
</b></span>	
		
<%

	if ((!QUERYPARM.getReportedDuty()) &&
		(!QUERYPARM.getFilter().equalsIgnoreCase("new innovations events only"))){
	
		out.print("<br><img src=\"images/info.gif\">&nbsp;");
		out.print("<span style=\"font-size: 12px;font-family: Verdana\">This resident has reported no duty time for the period you selected.&nbsp;&nbsp;In consequence, any activity is considered exceptions.</span>");
	}
	
 %> 

<cewolf:chart 
    id="<%=QUERYPARM.getChartID()%>"
    type="<%=QUERYPARM.getChartType()%>"
    title="" 
    xaxislabel="Day of Month" 
    yaxislabel="<%=QUERYPARM.getChartYLable()%>">
    
    <cewolf:colorpaint color="#C3D9FF"/>
    
    <cewolf:data> 
        <cewolf:producer id="<%=QUERYPARM.getChartProducer()%>">
        	<cewolf:param name="startDate" value="<%=QUERYPARM.getStartDate()%>"/>
        	<cewolf:param name="endDate" value="<%=QUERYPARM.getEndDate()%>"/>
        	<cewolf:param name="resident" value="<%=QUERYPARM.getResident()%>"/>
        	<cewolf:param name="filter" value="<%=QUERYPARM.getFilter()%>"/>
        	<cewolf:param name="academic_year" value="<%=HEADER_SECTION.getAcademicYearKeyString()%>"/>
        </cewolf:producer>
    </cewolf:data>
   
 <cewolf:chartpostprocessor id="SeriesPainter">
 	<cewolf:param name="producer" value="<%=QUERYPARM.getChartProducer()%>"/>
 	<cewolf:param name="startDate" value="<%=QUERYPARM.getStartDate()%>"/>
    <cewolf:param name="endDate" value="<%=QUERYPARM.getEndDate()%>"/>
 </cewolf:chartpostprocessor>  
   
</cewolf:chart> 

<p>
<center>
<cewolf:img chartid="<%=QUERYPARM.getChartID()%>" renderer="cewolf" width="980" height="<%=QUERYPARM.getChartHeight()%>"/>

</center>
	<% 
		
    	
    	if (QUERYPARM.getChartID().equalsIgnoreCase("scatterall")){ 
    	
		    out.print("</center>");	
    		out.print("<h4>Approved Duty Hours and all OR and TDS events</h4>");
    		out.print("<p>This graph summarizes a resident's activity for the selected period of time.&nbsp;&nbsp;");
    		out.print("The numbers along the base of the graph represent calendar days and numbers along the ");	
    		out.print("left side of the graph represent the hours within a given day.&nbsp;&nbsp;");
    		out.print("All system events for the given resident and time period are plotted on this graph.&nbsp;&nbsp;");
    		out.print("Note that multiple events, like TDS ");
    		out.print("screen hits, may occur very close together in time.&nbsp;&nbsp;When this happens, a plotted symbol ");
    		out.print("on the graph represents multiple occurrances of the given event.");
    		out.print("<br><br><br>");
    	}
	
		if (QUERYPARM.getChartID().equalsIgnoreCase("scatterex")){ 
    	
		    out.print("</center>");	
    		out.print("<h4>Approved Duty Hours and Exceptions</h4>");
    		out.print("<p>This graph summarizes a resident's activity for the selected period of time.&nbsp;&nbsp;");
    		out.print("The numbers along the base of the graph represent calendar days and numbers along the ");	
    		out.print("left side of the graph represent the hours within a given day.&nbsp;&nbsp;For clarity, ");
    		out.print("this graph will show recorded shifts and exceptions only.&nbsp;&nbsp;Normal TDS and OR events that ");
    		out.print("occur during a shift are not shown on this graph, but this information is presented on the 'Approved Duty Hours and all OR and TDS events' ");
    		out.print("graph or tabular report.&nbsp;&nbsp;Note that multiple events, like TDS ");
    		out.print("screen hits, may occur very close together in time.&nbsp;&nbsp;When this happens, a plotted symbol ");
    		out.print("on the graph represents multiple occurrances of the given event.");
    		out.print("<br><br><br>");
    	}
		
		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
    		out.print("</div>");
    	}
	
    %>		

</body>
         
</html>

<% }}//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>


