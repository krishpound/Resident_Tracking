function enterKey(evt) {
 
  	var evt = (evt) ? evt : event
  	var charCode = (evt.which) ? evt.which : evt.keyCode
  	
  	if (charCode == 13) {
    	
    	if((LoginForm.j_username.value != "") || (LoginForm.j_password.value != "") 
    		|| (LoginForm.j_username.value != null) || (LoginForm.j_password.value != null))
    		
    	{
    	
    		document.LoginForm.submit();
  	
  		}
  		else {
  		
  			document.LoginForm.reset();
    		LoginForm.j_username.focus();
  	
		}
	}
}	 

function validateForm(){

	if((LoginForm.j_username.value != "") || (LoginForm.j_password.value != "")){
    	
    		document.LoginForm.submit();
    }		
    else {
    
    		document.LoginForm.reset();
    		LoginForm.j_username.focus();
    }		
    	
}

function validateHelpForm(){


	if ((helpForm.userEmail.value != "") || (helpForm.userEmail.value != null)){
    	
    		document.helpForm.submit();
    }		
    else {
    
    		document.helpForm.reset();
    		helpForm.userEmail.focus();
    }		
    	
}

function enterKey_Help(evt) {
 
  	var evt = (evt) ? evt : event
  	var charCode = (evt.which) ? evt.which : evt.keyCode
  	
  	if (charCode == 13) {
    	
    if ((helpForm.userEmail.value == "") || (helpForm.userEmail.value == null)) {
    	
    		document.helpForm.reset();
    		helpForm.userEmail.focus();
  	
  		}
  	
	}
	
}	 

