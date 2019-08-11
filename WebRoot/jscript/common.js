	
	/* START:  Display default date format in all date field */
	function clearInit(tag) {
	
		if(tag.value == 'mm/dd/yyyy') {
		    
			tag.value = '';
			thisId = tag.id;
			document.getElementById(thisId).style.color = "black";
		}
		return true;
	}
	
	function initDateFormat(tag) {
		
		inputstr = tag.value;
		inputstr = new String(inputstr);
		if(inputstr.length == 0) {
		  
		   tag.value = "mm/dd/yyyy";
			thisId = tag.id;
			document.getElementById(thisId).style.color = "#C0C0C0";
		}
		return true;		
	}

	/* END:  Display default date format in all date field */
	function reportCollapse(tag) {
		
		divid = tag.id + '_div';
		divobj = document.getElementById(divid);
		boxid = tag.id + '_collapse';
        boxobj = document.getElementById(boxid);
         
		 if(divobj.style.display=="none")  {
				divobj.style.display="block"
				 boxobj.value=' '+'-';                
		} else  {
				divobj.style.display="none"
				 boxobj.value=' '+'+';
	  }


	}





