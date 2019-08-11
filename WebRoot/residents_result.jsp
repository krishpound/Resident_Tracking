<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  

<jsp:include page="/header.do" flush="true"/>  
<jsp:include page="/querymenus.do" flush="true"/> 
<jsp:useBean id="HEADER_SECTION" type="resident_tracking.HeaderBean" scope="session" />
<jsp:useBean id="RESIDENTQUERYPARM" type="resident_tracking.FormBean" scope="request" />
 
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
    	<script language="JavaScript" type="text/javascript" src="jscript/menu.js"></script> 
    	<script language="JavaScript" type="text/javascript" src="jscript/menu_items.js"></script> 
    	<script language="JavaScript" type="text/javascript" src="jscript/menu_tpl.js"></script> 
		<script language="JavaScript" type="text/javascript" src="jscript/calendar.js"></script> 
		
		
        <title>Mount Sinai Resident Query</title>
        
    </head> 
    
	  <body class="common" bgcolor="#C3D9FF"> 
	               
		<script language="JavaScript">
			new menu (MENU_ITEMS, MENU_POS);
		</script>               
              	                 
<!-- Top Menu Bar -->

	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width="100%" border="0" height="10">
          <tr>	
          	<td valign="top" style="text-align:left" nowrap="nowrap" bgcolor="#e8eefa" height="20">
           		<b><a href="./changeYear.jsp">ACADEMIC YEAR: <%=HEADER_SECTION.getAcademicYear()%></a></b>
           		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       			&nbsp;&nbsp;
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
        			out.print("height=\"157\"");
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
        </tr><tr><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td><td valign="top"><br></td></tr>
      </table>

</div>

<!--  gif table end -->

			</table>

          	<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          		
        </table>

<!-- Query Panel Form Section Start -->

			<% 
        
        		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
        			out.print("<div id=\"app_user_panel\" style=\"position:absolute;left:10;top:198;width:100%;height:100px;\">");
        		}
        		   		
         	%>

	<!--  outer panel table start -->
	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width=100% border="0" height="80" align="center">
        
          <tr>
          		<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="100">
          		
          		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=300 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Mount Sinai Resident Query</span>	
          				</td>
        			</tr>       			
				</table>
				
			<% 
        
        		if (HEADER_SECTION.getBrowser().equalsIgnoreCase("firefox")){
        			out.print("<br>");
        		}
        		   		
         	%>		
				
		<form name="ResidentQuery" action="residents.do" method="post">
		<center>		
		<table>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr> 
			<tr>
			
				
			
				<td colspan="2" align="right" height="24"><span class = lable>Program :&nbsp;</span></td>
        				
        				<td><select name="programList">
        											 
 						<%  
 		 				
							//RESIDENTQUERYPARM is a request bean set in ActionResidentQuery
							//RESIDENT_DEPARMENT is a session list set in ActionResidentMenus
							
 							String program_choice = RESIDENTQUERYPARM.getProgram();
 							Iterator iterator;
 							String pl = HEADER_SECTION.getProgramList();
 							List datalist = (List)session.getValue(pl);
 							String html_output=null;
 							String list_value=null;
 														
 							for (iterator=datalist.iterator(); iterator.hasNext();){
 							
 								list_value = (String)iterator.next();
 							
 								if (list_value.equalsIgnoreCase(program_choice)){
 								
 									html_output="<option selected=\"selected\">"+list_value+"</option>";
 									
 								}
 								else{
 								
 									html_output="<option>"+list_value+"</option>";
 								
 								}
 							
 								out.print(html_output);
 									
 							}
 									 
 					%>
 						
 						</select></td>
 						
        				<td>&nbsp;&nbsp;&nbsp;</td>
	
				<td colspan="2" align="right" height="24"><span class = lable>Status :&nbsp;</span></td>
        				
        				<td><select name="statusList">
    				
        			<% 
 		 				
							//RESIDENTQUERYPARM is a bean set in ActionResidentQuery
							//RESIDENT_DEPARMENT is a list set in ActionResidentMenus
							
 							String status_choice = RESIDENTQUERYPARM.getStatus();
 							Iterator iterator2;
 							List datalist2 = (List)session.getValue("MY_RESIDENT_STATUS");
 							String html_output2=null;
 							String list_value2=null;
 							
 							for (iterator2=datalist2.iterator(); iterator2.hasNext();){
 							
 								list_value2 = (String)iterator2.next();
 							
 								if (list_value2.equalsIgnoreCase(status_choice)){
 									html_output2="<option selected=\"selected\">"+list_value2+"</option>";
 								}
 								else{
 									html_output2="<option>"+list_value2+"</option>";
 								}
 							
 								out.print(html_output2);
 									
 							}
 									
 					%>		
        				
        					</select>
        				</td>	
        	</tr>	
			
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			        		
		</table>		
		</center>
		<input class="formButton" type="submit" value="Show My Residents" name="B13">
				
		</form>
		</td></tr></table>					


	<%  
    	if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
        	out.print("</div>");
        	out.print("<div id=\"result_set\" style=\"position:absolute;left:10;top:375;width:100%;\">");
        }
        		
    %>		

<br><br>
<center><span style="font-size: 16px;font-family: Verdana"><b>My Residents Report</b></span>
<br><br>

<center>
<table border="1" cellspacing="0" cellpadding="2">
	
	<col width="300"><col width="60"><col width="60"><col width="60"><col width="450">
	<tr style="background:#ffffff url(./images/lev0_bg2.gif)">
	
		<td><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">Resident</span></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">ID</span></center></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">Status</span></center></td>
		<td><center><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">Site</span></center></td>		
		<td><span style="font-size: 14px;font-family: Verdana;color: #FFFFFF;">Program</span></td>
	</tr>
	  
	<%Iterator itr;int rowcount=0; String styleClass=null;%>
	<% List data= (List)request.getAttribute("RESIDENT_TABLE");
	for (itr=data.iterator(); itr.hasNext(); )
	{styleClass = (rowcount%2==0 ) ? "even":"odd";
	%>
		<tr class="<%=styleClass%>">
		<td class="resultset"><%=itr.next()%></td>
		<td class="resultset"><center><%=itr.next()%></center></td> 
		<td class="resultset"><center><%=itr.next()%></center></td>
		<td class="resultset"><center><%=itr.next()%></center></td>
		<td class="resultset"><%=itr.next()%></td>
	</tr>
<% rowcount++;}%>
</table>
</center>


<% 
		if (rowcount == 1){
			out.print("<br><center>There was 1 record returned by your query.</center>"	);
		}
		else {
			out.print("<br><center>There were "+rowcount+" records returned by your query.</center>");
		}	
						
%>

		<% 
			if (HEADER_SECTION.getBrowser().equalsIgnoreCase("msie")){
				out.print("</div>");	
			}	
		%>
</body>
</html>

<% }//authorized
else{%>
	<jsp:include page="unauthorized.jsp" flush="true"/>  
<%}%>
