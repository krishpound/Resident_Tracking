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

<!--  gif table end -->

          	<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          		
        </table>
		<table><tr><td></td></tr><tr></tr></table>
		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=200 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Resident Tracking Welcome Page</span>	
          				</td>
        			</tr>
		</table>

<br>

<h3><i>Welcome to Resident Tracking!</i></h3>

<p>
Resident Tracking is a tool to help you monitor your Residency Programs for compliance with 
State and Federal regulations.&nbsp;&nbsp;Please login at any time to run ad-hoc reports on your programs or individual residents.

<p>
<img src="images/info.gif" style="border-style: none">&nbsp;
Data for the 2013-2014 Academic Year is available NOW! 
</p>

<p>
<a href="./gettingstarted.jsp" style="text-decoration:none;">
<img src="images/yellowstar.png" style="border-style:none"></a>&nbsp;If you are a new user, please visit the 
<a href="./gettingstarted.jsp" style="font-size: 15px;text-decoration: underline;">getting started</a>&nbsp;page!&nbsp;&nbsp;Information on this page will help 
you to begin using the application quickly.
<p>

<a href="mailto:Christopher.Pound@mountsinai.org?subject=Resident Tracking Feedback" 
	style="text-decoration:none;">
<img src="images/email.png" style="border-style: none">&nbsp;</a>
We are very interested to hear your comments and feedback!&nbsp;&nbsp;Please direct any inquiries to the 
<a href="mailto:cfapound@yahoo.com?subject=Resident Tracking Feedback" style="font-size: 15px;text-decoration: underline">Data Administration
team</a>.

</body>
</html>
<% }//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>

