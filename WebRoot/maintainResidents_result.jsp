<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  

<jsp:include page="/header.do" flush="true"/>  
<jsp:include page="/querymenus.do" flush="true"/> 
<jsp:useBean id="HEADER_SECTION" type="resident_tracking.HeaderBean" scope="session" />
<jsp:useBean id="MAINTAINRESIDENTPARM" type="resident_tracking.FormBean" scope="request" />

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
    	
    	<script language="JavaScript" type="Text/JavaScript"> 
			function loadFunction() {  
				
				var msg = '<%=MAINTAINRESIDENTPARM.getMessage1()%>';
				
				if (msg == 'Please enter a value in the Last Name field.'){
					var focus_fld = 'lastName';
				}
				else if (msg == 'Please enter a value in the First Name field.'){
					var focus_fld = 'firstName';
				}
				else {
					var focus_fld = 'dictationID';
				}
						
				eval('maintainResidentsForm.'+focus_fld+'.focus()');	
				
 			} 
		</script>	
    	
        <title>Maintain Residents</title>
        
    </head> 
    
	  <body class="common" bgcolor="#C3D9FF" onload="loadFunction()"> 
	               
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

<form name="maintainResidentsForm" method="post" action="maintainResidents.do">

	<!--  outer panel table start -->
	<table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width=100% border="0" height="250" align="center">
        
          <tr>
          		<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="280">
          		
          		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=300 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20">
          					<span class="heading3">Maintain Residents</span>	
          				</td>
        			</tr>
				</table>
                <br>	
          <!--  inner panel table start -->
          		
          		<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          			
          			<tr>
        				<td colspan="2" align="right" height="30">Dictation ID :&nbsp;</td>
        				<td><input class="textField"  type="text" name="dictationID" size="10" value="<%=MAINTAINRESIDENTPARM.getDictationID()%>"></td>       				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				
        				<td colspan="2" align="right" height="30">Last Name :&nbsp;</td>
        				<td><input class="textField"  type="text" name="lastName" size="30" value="<%=MAINTAINRESIDENTPARM.getLastName()%>"></td>      
        				 				
        			</tr>
        			<tr> 
        				<td colspan="2" align="right" height="30">First Name :&nbsp;</td>
        				<td><input class="textField"  type="text" name="firstName" size="30" value="<%=MAINTAINRESIDENTPARM.getFirstName()%>"></td> 
        				
        				
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				<td colspan="2" align="right" height="30">Program :&nbsp;</td>
        				
        				
        				<td><select name="programList">	
        					<%
        					
        						List<String> data3;
        						String program=null;
        						String program_choice = MAINTAINRESIDENTPARM.getProgram();
        						
        						String pl = HEADER_SECTION.getProgramList();
        						List<String> data1 = (List<String>)session.getAttribute(pl);
       
								Iterator<String> itr3 = data1.iterator();
								
								while (itr3.hasNext())
								{							
									program=itr3.next();
									if ((program.equalsIgnoreCase("all my programs")) ||
										(program.equalsIgnoreCase("mount sinai - all programs"))){;}
									else{
									
										if (program.equalsIgnoreCase(program_choice)){	
									
											out.print("<option selected=\"selected\">"+program+"</option>");
										}
										else{
										
											out.print("<option>"+program+"</option>");
											
										}	
									}	
								}
								
							%>   		
        					     					
        					</select>
        				</td>	
        				
			       				
        			</tr>
        			
        			<tr>
        				<td colspan="2" align="right" height="30">Status :&nbsp;</td>
        				
        				<td><select name="statusList">
        					
        					<%
        					
							  	List<String> statusData = (List<String>)session.getAttribute("MY_RESIDENT_STATUS");
							  	Iterator<String> itr2 = statusData.iterator();
							 	String s=null;
							 	String status_choice=MAINTAINRESIDENTPARM.getStatus();
							
							  	while (itr2.hasNext()){							
									s = itr2.next();
									if (s != "All"){
									
										if (s.equalsIgnoreCase(status_choice)){
											out.print("<option selected=\"selected\">"+s+"</option>");
										}
										else{
											out.print("<option>"+s+"</option>");
										}
							  		}
							  	}
							%>  		
        					     					
        					</select>
        				</td>
        				 		
        				<td>&nbsp;&nbsp;&nbsp;</td>
        				
        				<td colspan="2" align="right" height="30">Employer :&nbsp;</td>
        				<td><select name="siteList">	
        					<%
							  	List data4= (List<String>)session.getValue("ALL_SITES");
							  	Iterator<String> itr4 = data4.iterator();
		
								for (itr4=data4.iterator(); itr4.hasNext(); ){							
				
									out.print("<option>"+(String)itr4.next()+"</option>");
									
								}
							%>   		
        					     					
        					</select>	
        				</td>		
        				
        	
        			</tr>
        			<tr></tr>
        			
        				               			
          		</table>
          		 
          		<table style="background-color: #ffffff; border: #03328A 1px solid" cellspacing="3" cellpadding="5" width=200 border="0" height="10" align="center">
          			<tr>
          				<td valign="top" style="text-align:center" nowrap="nowrap" class="title" height="20"><span class="heading3">
          				
          				<%
          					String sql_command_choice = MAINTAINRESIDENTPARM.getSqlAction();
          					
          					if (sql_command_choice.equalsIgnoreCase("add")){
          						out.print("ADD &nbsp;<input type=\"radio\" name=\"sqlAction\" value=\"ADD\" checked>&nbsp;");
          						out.print("DELETE &nbsp;<input type=\"radio\" name=\"sqlAction\" value=\"DELETE\"></span>");
          					}
          					else{
          						out.print("ADD &nbsp;<input type=\"radio\" name=\"sqlAction\" value=\"ADD\">&nbsp;");
          						out.print("DELETE &nbsp;<input type=\"radio\" name=\"sqlAction\" value=\"DELETE\" checked></span>");
          					}
          					
          				%>	
 				
          				</td>
        			</tr>
        			
				</table>
          		
          		<br>
          			
          		<center><input class="formButton" type="submit" value="Submit" name="B4" ></center>	     	
        </tr>
      
	</table>
	
	
</form>	 
	
	<center><font color="red">
			
			<% 
			
				if (false==MAINTAINRESIDENTPARM.getSqlStatus()){
			
					if (MAINTAINRESIDENTPARM.getMessage1() != null)
						out.print("<img src=\"images/error.gif\">&nbsp;"+MAINTAINRESIDENTPARM.getMessage1()+"<br><br>");
											
					if (MAINTAINRESIDENTPARM.getMessage2() != null)
						out.print(MAINTAINRESIDENTPARM.getMessage2()+"<br><br>");
					 
					if (MAINTAINRESIDENTPARM.getMessage3() != null)
						out.print(MAINTAINRESIDENTPARM.getMessage3()+"<br><br>");	 
					 					 					
				}
				else{
				
					if (MAINTAINRESIDENTPARM.getRowsUpdated()==0){
						out.print("<center><font color=\"#03328A\"><img src=\"images/info.gif\"/>&nbsp;There were 0 rows affected by your "+MAINTAINRESIDENTPARM.getSqlAction().toLowerCase()+" command.</font></center>");
					}
					else{				
						out.print("<center><font color=\"#03328A\"><img src=\"images/ok.gif\"/>&nbsp;Resident Tracking has applied your "+MAINTAINRESIDENTPARM.getSqlAction().toLowerCase()+" command successfully.</font></center>");
					}	 	
				}	
					 					 					 					 					 					 
			%>
			
	</font></center>
	
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




