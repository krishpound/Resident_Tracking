   var global_obj = null ;
   var flag = false;
   var timer; 	
	
	
	
	function xCombo(length,inputParam,list) {
				

        this.itemList = list;
		this.inputSize = length;
		this.comboName = inputParam;
		str = '\"'+this.comboName+'\"';
		

		this.strCombo = " <table id='table_' border='0' style='cursor:hand; border-color:#7B7EEA; border-collapse: collapse;height:22 ' "
			+" onclick='onClickHandler("+str+");return true;'  cellpadding='0' cellspacing='0' >  <tr><td valign='middle' class='leftPattern'  >"
			+"<input id='input_"+this.comboName+"'  type='text'  value=' "+this.itemList[0]+"' class='commonCombo' "
			+"name='combo_"+this.comboName+"' size='"+this.inputSize+"' ></td><td width='10' valign='middle' class='rightPattern'>"
			+"<input  type='text' readonly='true'  name='combo1_'  style='width:20px;cursor:hand;border:0px solid #FFFFFF; background-image: url(../images/combo_arrow.bmp); background-repeat: no-repeat; background-position:  right ;  ' >"
			+" </td></tr></table> <div id='div_"+this.comboName+"'  style='display:none;width:140px; position:"
			+" absolute; z-index:101; background-color:#FFFFFF'  ><table  border=1 style='width:100%;border-color:#7B7EEA; "
			+"border-collapse: collapse;height:22 '  cellpadding='0' cellspacing='0' >	";
		
		this.listItem = '';	
		for(i = 0; i < this.itemList.length; i++ ) {

		this.listItem = this.listItem + " <tr onmouseover='return toggleColor(this);' onmouseout='return toggleColor(this);' style='cursor:pointer;background:#FFFFFF;' >"
				+"<td onclick='return assignValue(this,"+str+");' nowrap style='border-style: solid; border-width: 1' >&nbsp;"
				+ this.itemList[i] +"&nbsp;</td> </tr> " ; 
			
		}		
			
			
			
		  this.finalstr = "</table></div> ";	 
		this.strCombo = this.strCombo + this.listItem + this.finalstr ;
		
		this.value = function() {
			if(this.inputSize <= 0) return "Size cannot be smaller than or equal to zero";
			if(this.comboName == 'undefined' || this.comboName.length <= 0 ) return "Input is mandatory";
			
			 return (this.strCombo);
			 //alert (this.strCombo);
			 //return "combo";
		}

		
	}
/*  Attempt for closing drop down menu when focusLost event occurs.
		function closeDiv(name) {

			//divID = "div_"+name;
			obj = document.getElementById(name);
			alert(obj);

		}
*/	
		function assignValue(tagName,param) {
		
			//alert(tagName.innerHTML);
			name = 'input_'+param;
			obj = document.getElementById(name);
			
			str = tagName.innerHTML;
			//alert(str );
			obj.value = str.replace(/&nbsp;/g, " ");
			
			onClickHandler(param);
		}
	
		function changeColor(tag) {
		tagobj = tag;				
			if(!tagobj )return true;
		  	if(tagobj.style.backgroundColor=="#ffffff"){
		  
		    tagobj.style.backgroundColor="#C3D9FF" ;
	  	  	} else {
		   	tagobj.style.backgroundColor="#ffffff" ;
	  	  	}
	  	  
	  		return true; 
		}

 	function closeCombo() {
	
		if(global_obj != null) { global_obj.style.display = 'none';  flag = false; }
	}

	function onClickHandler(partialIDValue) {
		if(partialIDValue.length != 0) {
		var full_ID_Value = "div_"+partialIDValue;
		obj = document.getElementById(full_ID_Value);
		//alert(full_ID_Value);
		if(!obj) return;
		
			if(obj.style.display == 'none') {
				
				if(global_obj != null) { global_obj.style.display = 'none';  flag = false; }		    
				global_obj = obj;
				flag = true;
				obj.style.display = 'block';
			}
			else {
			
				obj.style.display = 'none';
				global_obj = null;
				flag = false;
			}
		}
		return true;
	}
   
   function toggleColor(tagPointer) {
   		// alert(tagPointer.style.backgroundColor);
   	   	if(tagPointer.style.backgroundColor == '#ffffff') {
			tagPointer.style.backgroundColor = '#ccffff';
			clearTimeout(timer);  // Don't close drop down menu on mouse moveover
		}
   		else if (tagPointer.style.backgroundColor == '#ccffff') { 
				tagPointer.style.backgroundColor = '#ffffff'; 
				timer = setTimeout("closeCombo()",500); // Do close drop down menu on mouse moveout
		}
	   return true;
   }
 