<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  

<jsp:include page="/header.do" flush="true"/>  
<jsp:include page="/querymenus.do" flush="true"/> 
<jsp:useBean id="HEADER_SECTION" type="resident_tracking.HeaderBean" scope="session" />

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
		<td style="border:0px none;text-align:left">&nbsp;<%=HEADER_SECTION.getWelcomeName()%>
		</td>
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
          <td style="border:0px none" height="30" width="225" rowspan="2" valign="top" bgcolor="#e8eefa"><span class="text"><b><i><font face="Verdana" color="#03328A" size="5"><span style="font-size:26px;line-height:30px;color:03328a">R</span></font></i></b><i><font face="Verdana" color="#03328A" size="4"><span style="font-size:22px;line-height:28px;color:03328a">esident </span></font></i><b><i><font face="Verdana" color="#03328A" size="5"><span style="font-size:26px;line-height:30px;">T</span></font></i></b><i><font face="Verdana" color="#03328A" size="4"><span style="font-size:22px;line-height:28px;">racking<br soft></span></font></i></span></td>
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
		<table><tr><td></td></tr><tr></tr></table>
		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=200 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Getting Started</span>	
          				</td>
        			</tr>
				</table>

<br>
<h3><i>Welcome to all New Users!</i></h3>
<p>
<img src="images/greenchecksmall.png" style="border-style:none" alt="get started!">&nbsp;
Let's talk about the fundamentals of Resident Tracking!&nbsp;&nbsp;There are a few basics to cover and then you 
can begin using the application.
<p>
Resident Tracking allows you to monitor the activity of your residents, and to keep track of the amount
of duty that they are actually performing.&nbsp;&nbsp;We can do this by importing electronic data from 
several computer systems that are heavily used by residents, merging it together, and allowing you to dynamically
query it.
<p>

<h4>Application Availability</h4>
<p>Resident Tracking is refreshed with new data each day and is available to use at 9 AM on weekdays.&nbsp;&nbsp;
On weekends the application may be unavailable for certain periods after 9 AM while system maintenance
is performed.&nbsp;&nbsp;When using the application you will have access to data that is entered or tracked for the entire academic year, 
up to midnight last night.

<h4>The Academic Year</h4>
<p>The Mount Sinai School of Medicine's Academic Year runs from July 1st through June 30th.&nbsp;&nbsp;The data in Resident Tracking
goes back as far as July 1, 2008.&nbsp;&nbsp;Now that we have moved into a new academic year, you may run reports
for the current or prior year.&nbsp;&nbsp;YOU MAY RUN REPORTS FOR ONE ACADEMIC YEAR ONLY AT ONE TIME.&nbsp;&nbsp;You can easily
switch to another year, but all reporting is limited to the year that you are currently working in.&nbsp;&nbsp;In the upper right corner
of your screen is a link that allows you to switch to a different academic year.
</p>

<h4>Navigating the Application</h4>
<p>The top of your screen will look the same no matter where you are in Resident Tracking,
and the dark blue menu bar located just below the Resident Tracking logo, will be the primary way that you navigate 
through the application.&nbsp;&nbsp;Also notice the the 'Home' and 'Logout' links in the top right corner of 
your window.&nbsp;&nbsp;Please be sure to always click the 'Logout' link when you are finished using the application.
</p>

<h4>The Resident Activity Summary Query</h4>
<p>Resident Tracking provides four query screens for you to use and you can get to them by selecting
'Ad-Hoc Query' on the menu bar.
<p>The 'Resident Activity Summary' will normally be your starting point, and is really the focal point of the
application.&nbsp;&nbsp;This screen allows you to get 'the big picture' of what activity has been recorded for 
your residents.&nbsp;&nbsp;You can <i>drill down</i> to get more detailed information later, but you will normally 
start on the Resident Activity Summary page.</p>
<p>The Resident Activity Summary screen presents you with six fields that you can use to customize a query.&nbsp;&nbsp;
You will only be able to choose from the departments, programs, and residents that <i>you</i> are responsible for on this screen.&nbsp;&nbsp;
After making your selections, simply click the submit button and a tabular report will appear in the bottom of your screen.&nbsp;&nbsp;  
<p>
The Resident Activity Summary Report will contain one summarized row for each resident that is returned by your
query.&nbsp;&nbsp;By experimenting with your query selection options, you will find that the report can be sorted
and filtered in various ways.&nbsp;&nbsp;Try to use these options to organize your report in a way that is most
useful and meaningful for you.&nbsp;&nbsp;  
<p>
<img src="images/yellowstar.png">&nbsp;Tip: The Resident column in the report is a hyperlink to a more detailed report!

<h4>Where does the data come from?</h4>
<p>Resident Tracking receives data from three sources:<br>
<ul>
<li type=circle>New Innovations
<li type=circle>TDS
<li type=circle>Horizon Surgical Manager (HSM - the OR system)
</ul>
<p>Please visit the <a href="./generalhelp.jsp" style="font-size: 15px;text-decoration: underline;">general help page</a> for more information on these systems.

<h4>What are <i>Exceptions</i>?</h4>
<p>By importing data from multiple source systems at Mount Sinai, Resident Tracking is able to detect work activity that occurs beyond
a person's recorded duty time.&nbsp;&nbsp;Residents are required to enter their hours worked into the New Innovations application, which in 
turns feeds Resident Tracking.&nbsp;&nbsp;Resident Tracking is then able to compare the recorded duty time with activity in other applications.
&nbsp;&nbsp;Exceptions are logged system events that occur outside of the recorded duty time for a resident.
      
<h4>Saving your Reports</h4>
<p>Resident Tracking now permits you to save your reports to Microsoft Excel.&nbsp;&nbsp;This capability is 
available on both the Resident Activity Summary and Resident Activity Detail pages.&nbsp;&nbsp;The 
Resident Activity Detail query gives you the option of generating reports in a tabular or graphical style,
though only tabular style reports may be saved to Excel.&nbsp;&nbsp;Simply click the small Microsoft Excel icon
at the top of your report to save it to your computer system.&nbsp;&nbsp;To save a graphical style report, 
position your cursor over the graph and right-click your mouse.&nbsp;&nbsp;The popup box gives you the option 
to save the image, so you may copy the graph image into an email or another program in this manner.<br> 

</body>
</html>
<% }//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>

