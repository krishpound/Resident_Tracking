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

		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=300 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Resident Tracking General Help</span>	
          				</td>
        			</tr>
				</table>


<br>
<h3><i>Source Data</i></h3>
<p>
Resident Tracking currently imports data from three existing Mount Sinai applications.&nbsp;&nbsp;After collecting
and merging this data, it is made available for you to query and analyze.&nbsp;&nbsp;The three source systems are: New Innovations, TDS, and HSM.&nbsp;&nbsp;
Each system is discussed in the following sections.</p>
<p>
<h4><i>New Innovations</i></h4>
<p>New Innovations Inc.'s Residency Management Suite is a tool that allows you to unify data into a centralized data warehouse.&nbsp;&nbsp;
The Duty Hours module of this application suite is used by all resident fellows to enter and record their schedules and work activity.&nbsp;&nbsp;This data
is exchanged with Resident Tracking on a daily basis, and includes updates from the prior day.</p>
 
<p>
<h4><i>TDS</i></h4>
<p>The TDS system is used extensively throughout MSMC's patient care units, and is the main 
Inpatient Computerized Physician Order Entry (CPOE) system.&nbsp;&nbsp;
TDS is used in all Inpatient units (excluding the paediatric and neonatal ICUs and the 
General Clinical Research Center) for lab and medication order, result, and medical record
documentation.&nbsp;&nbsp;
<br>
TDS provides Resident Tracking with an audit file that identifies all application screens that were
accessed, by all users, throughout the prior day.</p>

<p>
<h4><i>MSDW - HSM</i></h4>
<p>Resident Tracking is able to leverage the very rich content now available in the 
<a href="https://msdw.mountsinai.org" style="font-size: 15px;text-decoration: underline">Mount Sinai Data Warehouse</a> (MSDW) as a third
stream of in-bound data for your reports.&nbsp;&nbsp;The Data Warehouse collects data generated by Mount Sinai's many patient care and
business transactional computer systems.&nbsp;&nbsp;All this data is organized and loaded into a state-of-the-art reporting model, where
it is made available to the many interested researchers and administrative report consumers at Mount Sinai.
<p>Horizon Surgical Manager (HSM) is a McKesson product used by the Sterile Processing Department (SPD), and Perioperative Nurses, 
to prepare, allocate and keep track of OR supplies.&nbsp;&nbsp;HSM is also used by Journalers for post case perioperative 
documentation data entry, and to review and process charges. 

</body>
</html>
<% }//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>


