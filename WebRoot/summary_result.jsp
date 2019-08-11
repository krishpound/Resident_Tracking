<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  

<jsp:include page="/header.do" flush="true"/>  
<jsp:include page="/querymenus.do" flush="true"/> 
<jsp:useBean id="HEADER_SECTION" type="resident_tracking.HeaderBean" scope="session" />
<jsp:useBean id="QUERYPARM" type="resident_tracking.FormBean" scope="request" />


<% if (HEADER_SECTION.getAuthorized()) { %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--    "http://www.w3.org/TR/html4/loose.dtd">    --> 
 
 
<html>
    <head>
    
    	<script language="JavaScript" type="text/javascript">
    	<!--
    		function ResidentDrillDown(resident)
    		{
    			
    			document.DrillQuery.DrillDownSelect.value = resident ;
				document.DrillQuery.submit();
    			
    		}
    
    	-->
    	</script>
    	
    	<script language="JavaScript" type="text/javascript">
    	<!--
    		function DownloadExcel()
    		{
    			document.Download.submit();	
    		}
    
    	-->
    	</script>
    	
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
		
        <title>Resident Activity Summary Query</title>
        
    </head> 
    
	  <body class="common" bgcolor="#C3D9FF" onload="load()"> 
	               
		<script language="JavaScript">
			new menu (MENU_ITEMS, MENU_POS);
		</script>               
  		
  		<script language="JavaScript" type="text/javascript">
	  		function load(){
	  			window.focus();
	  		}	
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
		<td class="header">Welcome To : </td>
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

<form name="SummaryQuery" action="summary.do" method="post">

	<!--  outer panel table start -->
	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width=100% border="0" height="210" align="center">
        
          <tr>
          		<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="180">
          		
          		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=300 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Resident Activity Summary Query</span>	
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
        				<td>
        				
						<input class="label"  type="text" id="startDate" name="startDate" size="12" value=<jsp:getProperty name="QUERYPARM" property="startDate"/>>
						<img src="images/calendar.gif" width="16" height="16" border="0" alt="Pick a date"
        						class="pointer" onclick="displayDatePicker('startDate');">
				
						</td>       					       				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				<td colspan="2" align="right" height="24"><span class = lable>Filter :&nbsp;</span></td>
        				<td title='criteria for inclusion in your report'><select class="filter" name="menuFilter">
        				
        					<c:forEach items="${HEADER_SECTION.summaryFilterList}" var="item"> 
        					
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
        				<td colspan="2" align="right" height="30">End Date :&nbsp;</td>
        				<td><input class="label"  type="text" name="endDate" size="12"
        				value=<jsp:getProperty name="QUERYPARM" property="endDate"/>>
        					<img src="images/calendar.gif" width="16" height="16" border="0" alt="Pick a date"
        						class="pointer" onclick="initializeCalendar();">	       				
        				</td> 
        			    				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				<td colspan="2" align="right" height="24">Sort Order :&nbsp;</td>
        				
        				<td><select name="menuSort">
        					
        					<c:forEach items="${HEADER_SECTION.summarySortList}" var="sort"> 
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
        				
        				<td colspan="2" align="right" height="30">Program :&nbsp;</td>
        				
        				<td><select name="menuProgram">
        		  				
        					<c:forEach items="${sessionScope.PROGRAM_LIST}" var="pgm"> 
        						<c:choose>
  									<c:when test="${QUERYPARM.program==pgm}">
  										<option selected="selected">${pgm}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${pgm}</option>
      								</c:otherwise>
  								</c:choose>
        					</c:forEach>
      
        					</select></td>
 		
        				<td>&nbsp;&nbsp;&nbsp;</td>        				
        				
        				<td colspan="2" align="right" height="30">Resident :&nbsp;</td>
        				
        				<td><select name="menuResident">
        				
        				<c:choose>
        					<c:when test="${QUERYPARM.program=='All My Programs'}">
        						<c:set var="rl" value="${sessionScope.RESIDENT_LIST}" scope="request"/>
        					</c:when>
        					<c:when test="${QUERYPARM.program=='Mount Sinai - All Programs'}">
        						<c:set var="rl" value="${sessionScope.RESIDENT_LIST}" scope="request"/>
        					</c:when>
        					<c:otherwise>
        						<c:set var="rl" value="${requestScope.VALID_RESIDENTS}" scope="request"/>
        					</c:otherwise>
        				</c:choose>		
        				
        				<c:forEach items="${rl}" var="rsd"> 
        						<c:choose>
  									<c:when test="${QUERYPARM.resident==rsd}">
  										<option selected="selected">${rsd}</option>
  									</c:when>
  									<c:otherwise>
       									<option>${rsd}</option>
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

			<div id="HELP" style="position:absolute;left:10;top:425;width:100%;height:200px;">
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

<%    if (null==QUERYPARM.getMessage1()){        %>

	<% 
		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
    		out.print("<div id=\"result_set\" style=\"position:absolute;left:10;top:430;width:100%;\">"); 
    	}
    	else{
    		out.print("<div id=\"result_set\" style=\"position:absolute;left:0;top:430;width:100%;\">"); 
    	}
    %>		



<form name="Download" action="download.do" method="post">
	<input type="hidden" name="filename" value="Resident_Summary"/>
	<center><a title="Click to Download" href="javascript:DownloadExcel()"><img src="images/msexcel.gif" border="0"/></a>&nbsp;Export this Report to Excel</center>
</form>	


<center><span style="font-size: 16px;font-family: Verdana"><b>Resident Activity Summary Report</b></span>
<span style="font-size: 14px;font-family: Verdana"><b>:&nbsp;&nbsp;<%=QUERYPARM.getStartDate()%>&nbsp;-&nbsp;<%=QUERYPARM.getEndDate()%>
</b></span>

<%

	if (!QUERYPARM.getFilter().equalsIgnoreCase("residents with reported time")){
	
		out.print("<br><img src=\"images/info.gif\">&nbsp;");
		out.print("<span style=\"font-size: 12px;font-family: Verdana\">Residents who have not reported duty hours for the period selected by your query are shaded grey.</span>");
		out.print("<br>");
		
	}
	else{
		
		out.print("<br><br>");
		
	}

 %>

<table border="1" cellspacing="0" cellpadding="2">
<!--  	<caption><span style="font-size: 16px;font-family: Verdana"><b>Resident Activity Summary Report</b></span></caption> -->
	<col width="350"><col width="200"><col width="200"><col width="200">
	<tr style="background:#ffffff url(./images/lev0_bg2.gif)">
		<td><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;"> Resident</span></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">TDS Exceptions / Days</span></center></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">OR Exceptions / Days<span></center></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">Total Exceptions / Days</span></center></td>		
	</tr>
	 
	 
	<%	
			int rowcount=0; 
			String styleClass=null;
			String textStyle=null;
			String resident_name=null;
			String generated=null;
			String tds_exceptions=null;
			String hsm_exceptions=null;
			String reported_duty="false";
			String ttl_exceptions=null;
			String program=null;
			List data= (List)session.getAttribute("DATASET");
			Iterator itr = data.iterator();
			String fm = "<form name=\"DrillQuery\" action=\"summary.do\" method=\"post\">";
			String hf 	= "<input type=\"hidden\" name=\"DrillDownSelect\"/>";
			String  acr	= null;
			String textStyleGrey = "style=\"font-size: 14px;font-family: Verdana;color: #808080\"";
			String textStyleBlue = "style=\"font-size: 14px;font-family: Verdana;color: #03328A\"";
			
			out.println(fm);
			out.print(hf);
						
			while (itr.hasNext()){
	
				resident_name = (String) itr.next();
				tds_exceptions = (String) itr.next().toString();
				hsm_exceptions = (String) itr.next().toString();
				reported_duty = (String) itr.next().toString();
				ttl_exceptions = (String) itr.next().toString();
				program = (String) itr.next().toString();
				
				styleClass = (rowcount%2==0 ) ? "even":"odd";

				textStyle = (reported_duty.equalsIgnoreCase("true")) ? textStyleBlue:textStyleGrey;
				
				out.print("<tr class=\""+styleClass+"\">");
				acr	= "<a title=\""+program+"\""+textStyle+" href=\"javascript:ResidentDrillDown";
				generated = acr+"('"+resident_name+"')\">"+resident_name+"</a>";

				out.print("<td>"+generated+"</td>");
				out.print("<td "+textStyle+"><center>"+tds_exceptions+"</center></td>");
				out.print("<td "+textStyle+"><center>"+hsm_exceptions+"</center></td>");
				out.print("<td "+textStyle+"><center>"+ttl_exceptions+"</center></td>");
				out.print("</tr>");			
	
			rowcount++;}
			
			out.print("</input>");
			out.print("</form>");
	%> 
	

</table>
</center>

<% 
		if (rowcount == 1){
			out.print("<br><center>There was 1 record returned by your query.</center>");
		}
		else {
			out.print("<br><center>There were "+rowcount+" records returned by your query.</center><br>");
		}	
				
		
	}		
	
%>
  
<% out.print("</div>"); %>	
 
</body>
</html>

<% }//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>

