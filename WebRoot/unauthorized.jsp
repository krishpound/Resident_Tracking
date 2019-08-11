<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
session.invalidate();

%> 
 
<html>
    <head>
        <meta http-equiv="Content-Language" content="en-us">
        <link rel="stylesheet" type="text/css" href="./css/rt.css">
        

        <title>MSDW - Resident Tracking Unauthorized User</title>
        
    </head> 
      
      <body class="common" bgcolor="#C3D9FF"> 
	                                 
      <!-- DIV #1 START -->        
	<div style="width: 100%; height: 175">
        &nbsp;<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </p>
         
<!-- DIVE #2 START -->
        <div align="center">
          <center>

<!-- TABLE #1 START -->
          
          <table border="0" cellspacing="0" width="100%" id="AutoNumber1" bordercolorlight="#C3D9FF" 	bordercolordark="#C3D9FF">
            <tr>
              <td width="100%" align="center">
              <p align="center"><span class="heading2">MSDW - Resident Tracking</span></td>
            </tr>
          </table><br>
<!-- TABLE #1 END -->
          
          </center>
        </div>
<!-- DIV #2 END -->        
        
<!-- TABLE #2 START: outer table around entire User Authentication section -->    
        
        <table style="background-color: #ffffff; border: #C3D9FF 1px solid" cellspacing="3" cellpadding="5" width="100%" border="0" height="146">
          <tr>
          	<td valign="top" style="text-align:center" nowrap="nowrap" bgcolor="#e8eefa" height="133">

<!-- TABLE #3 START-->
          	<table  align="center" border="0" cellpadding="1" cellspacing="0" height="125">
          		<tr>
        			<td colspan="2" align="center" height="24">
          	
<!-- TABLE #4 START -->
				<table height="34">
          					<tr>
          						<td valign="top" height="40">

          						</td>
          						<td align="center" height="35">
       								<span class ="heading4" >You are not authorized to use the Resident Tracking application at this time.</span>
          						</td>
          					</tr>
          					
          					<tr>
          						<td valign="top" height="40">

          						</td>
          						<td align="center" height="35">
       								<span class="heading4">The application is not available Sunday mornings while maintenance is performed.</span>
          						</td>
          					</tr>
          					
          					<tr>
          						<td valign="top" height="40">

          						</td>
          						<td align="center" height="35">
       								<span class="heading4">Contact Gaber Badran to request access if you get this message at another time.&nbsp;&nbsp;212.241.3073.</span>
          						</td>
          					</tr>
          																											
        			</table>
<!-- TABLE #4 END -->
          
          				<font size="-1"></font>
        
        			</td>
        		</tr>

        		<tr>
          			<td colspan="2" align="center" height="1"></td>
        		</tr>
        
        		<tr>
          			<td nowrap="nowrap" align="right" height="22">

         
          </td>
        </tr>

		
        </table>
<!-- TABLE #2 END -->

</body>

        
</html>

