<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
  
<html>
    <head>
        <meta http-equiv="Content-Language" content="en-us">
        <meta http-equiv="Cache-control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		<link rel="stylesheet" type="text/css" href="./css/rt.css">
        	
		<script language="JavaScript" src="./jscript/userKeys.js"></script>
        <script language="JavaScript" type="Text/JavaScript"> 
			function loadFunction() {  
				LoginForm.j_username.focus();	
				document.body.onkeypress = enterKey;
				document.focus();
 			} 
		</script>	


        <title>MSDW - Resident Tracking Login</title>
        
    </head> 
      
      <body class="common" bgcolor="#C3D9FF" onload="loadFunction()"> 
	                          
  <form NAME="LoginForm" ACTION="j_security_check" METHOD="post">
         
      <!-- DIV #1 START -->        
	<div style="width: 100%; height: 175">
        &nbsp;<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </p>
         
<!-- DIVE #2 START -->
        <div align="center">
          <center>

<!-- TABLE #1 START -->
          
          <table border="0" cellspacing="0" width="100%" id="AutoNumber1" bordercolorlight="#C3D9FF" bordercolordark="#C3D9FF">
            <tr>
              <td width="100%" align="center">
              <p align="center"><span class="heading2">Welcome to MSDW - Resident Tracking</span></td>
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
       								<!--   <font class = "std" size="+0"><b> User Authentication </b></font>  -->
       								<span class ="heading4" >User Authentication</span>
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
          			<td nowrap="nowrap" align="right" height="24">
<!-- DIV #3 START -->          		
					<div align="right">
          					<span class = label>Username :</span>
          				</div>
<!-- DIV #3 END -->
          			</td>
          			<td height="22">&nbsp; 
             		
             			<input class="textField" TYPE="text" NAME="j_username" size="18">		
             		            			
             		</td> 
        		</tr> 

        		<tr>
            		<td nowrap="nowrap" align="right" height="30">
<!-- DIV #4 START -->
              			<div align="right">
              				<span class = label>Password :</span>
              			</div>
<!-- DIV #4 END -->
            		</td>
                
                	<td height="22">&nbsp;
                  	
                  	<input class="passwordField" TYPE="password" NAME="j_password" size="18">
                  	                  		                  		
                  	</td>
        		</tr>

	
				<tr height="5px">
          			<td height="1">   
          			</td>
          			<td align="left" height="1">
					</td>
        		</tr>
         
        		<tr>
          			<td colspan="2" height="40" align="center">	
          				<input class="formButton" type="SUBMIT" onclick="javascript:validateForm()" value="Login" name="B4" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
            		</td>
        		</tr>
	  	
				<tr>
					<td colspan="2" height="25" align="center" bgcolor="e8eefa">
						<a href="https://sinaicentral.mssm.edu/intranet/Home/login/help" class="inline">Login or Password Help</a>
					</td>
				</tr>
				</table>
                
<!-- TABLE #3 END -->

         
          </td>
        </tr>

		
        </table>
<!-- TABLE #2 END -->
<br><center><font color="red"><img src="images/error.gif">&nbsp;You entered an invalid userid or password</font></center> 

  </div>
<!-- DIV #1 END -->       

</form>


	
</body>
        
</html>