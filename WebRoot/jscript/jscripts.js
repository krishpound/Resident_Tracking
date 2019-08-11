
//global variables
  var req;
  var which;
  var ajaxDivId;
  var refreshSearch = false;
  var actionPathForRefreshSearch = "default";
  var submitAjaxValue = "";
  var ewActionPath = "";
  var processingForm;
  var queryStringForSearchRefresh = "";
function submitPage(param,formName) {
	//alert(formName+' '+param);
        document.forms[formName].submitValue.value = param;
	document.forms[formName].submit();
}
   
function submitAjax(action,param,fname,tagId,resourceIndex) {
        
        if(fname) {
            //alert(param);
            processingForm = fname;
            document.forms[fname].submitValue.value = param;
            submitAjaxValue = param;
            if(document.forms[fname].resourceIndex) {
                //alert(resourceIndex);
                document.forms[fname].resourceIndex.value = resourceIndex ;
            }
            refreshSearch = false; 
            if(action.search('hupdate') != -1 || action.search('hdelete') != -1) {
                refreshSearch = false;    
            } else if(action.search('update') != -1) {
                actionPathForRefreshSearch = action.replace(/update/i,'browse');
                refreshSearch = true;
            } else if (action.search('adopt') != -1) {
                actionPathForRefreshSearch = action.replace(/adopt/i,'browse');
                refreshSearch = true;
            } else if (action.search('delete') != -1) {
                actionPathForRefreshSearch = action.replace(/delete/i,'browse');
                refreshSearch = true;
            } 
            
            
            
           // while search/nest/prev search result should disappear.
            if (submitAjaxValue.search('result') != -1 || submitAjaxValue.search('review') != -1 ) {
                if(document.getElementById('hiddenPanel')) {
                    document.getElementById('hiddenPanel').style.display='none';
                }
                if(document.getElementById('searchResult1')) {
                    document.getElementById('searchResult1').style.display='none';
                }
            }    
            
            // remove old messages on new request.
            if (submitAjaxValue.search('searchrefresh') != -1 ) {
                
            } else {
                if(document.getElementById('feedback')) {
                        document.getElementById('feedback').innerHTML='<BR/>';
                }
            }   
            
            //alert(actionPathForRefreshSearch);  
            action = action +".ew" ;
            ewActionPath = action;
            document.getElementById('indicator').style.display="block";
            ajaxDivId=tagId;
            if(document.forms[fname].ajaxTargetTag) {
                //alert(ajaxDivId);
                document.forms[fname].ajaxTargetTag.value = ajaxDivId ; 
            }

            retrieveURL(action,fname);
            return false;
        }
}
 
function submitHeader(param) {
	document.forms[0].submitValue.value = param;
	document.forms[0].submit();
}


/**
 * Ajax - javascript
 *
 * Collection of Scripts to allow in page communication from browser to (struts) server
 * ie can reload part instead of full page
 *
 * How to use
 * ==========
 * 1) Call retrieveURL from the relevant event on the HTML page (e.g. onclick)
 * 2) Pass the url to contact (e.g. Struts Action) and the name of the HTML form to post
 * 3) When the server responds ...
 *		 - the script loops through the response , looking for <span id="name">newContent</span>
 * 		 - each <span> tag in the *existing* document will be replaced with newContent
 *
 * NOTE: <span id="name"> is case sensitive. Name *must* follow the first quote mark and end in a quote
 *		 Everything after the first '>' mark until </span> is considered content.
 *		 Empty Sections should be in the format <span id="name"></span>
 */


  

  /**
   * Get the contents of the URL via an Ajax call
   * url - to get content from (e.g. /struts-ajax/sampleajax.do?ask=COMMAND_NAME_1) 
   * nodeToOverWrite - when callback is made
   * nameOfFormToPost - which form values will be posted up to the server as part 
   *					of the request (can be null)
   */
  function retrieveURL(url,nameOfFormToPost) {
    
    //get the (form based) params to push up as part of the get request
    if(nameOfFormToPost){
        url=url+getFormAsString(nameOfFormToPost);
    }
    
    
    // Ajax call
    // ajaxpagefetcher.load(ajaxDivId, url, true);
    
    //Do the Ajax call
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
      
      	req.open("POST", url, true); //was get
      } catch (e) {
        alert("Problem Communicating with Server\n"+e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
//        alert(url);     
        req.onreadystatechange = processStateChange;
        req.open("POST", url, true);
        req.send();
      }
    }
    
    
  }

  /**
   * Set as the callback method for when XmlHttpRequest State Changes 
   * used by retrieveUrl
   */
  function processStateChange() {
    var ajaxResponseText = "";
    if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        // hide progress indicator
        document.getElementById('indicator').style.display='none';
        
        var name = ajaxDivId; // Ajax content will be inserted here.
        // Hide the form after performing delete or adopt operation.
           
        if(document.getElementById(name)){
	 		
//            alert(req.responseText);
            ajaxResponseText = req.responseText;
            document.getElementById(name).style.display = 'block';
            document.getElementById(name).innerHTML = ajaxResponseText;
            
            if (submitAjaxValue.search('result') != -1 || submitAjaxValue.search('review') != -1 ) {
                if(document.getElementById('searchResult1')) {
                    document.getElementById('searchResult1').style.display='block';
                }
            }
	 
                
             if(ajaxResponseText.match('id="error"') != 'id="error"') {
                 
                 //alert(submitAjaxValue);
                    if(ewActionPath.search('acref') != -1 || ewActionPath.search('ah') != -1) {
                        if(document.getElementById('hiddenPanel')) {
                            document.getElementById('hiddenPanel').style.display='none';
                        }
                        if (ewActionPath.search('acref') != -1 && submitAjaxValue.search('insert') != -1) {
                            document.forms[processingForm].reset();
                         }
                    } else {
                      if(submitAjaxValue.search('delete') != -1 || submitAjaxValue.search('adopt') != -1 
                        || submitAjaxValue.search('update') != -1 || submitAjaxValue.search('merge') != -1
                         || submitAjaxValue.search('build') != -1 ) {
                            document.forms[processingForm].innerHTML="";
                            
                         } else if (submitAjaxValue.search('subscribe') != -1) {
                             if(document.getElementById('hiddenPanel')) {
                                document.getElementById('hiddenPanel').style.display='none';
                            }
                         } else if (submitAjaxValue.search('insert') != -1) {
                            document.forms[processingForm].reset();
                         }
                 }
                 if(ewActionPath.search('rdinsert') != -1 && submitAjaxValue.search('populate') != -1) {
                    refreshAutocomplete();
                 }
                 if(submitAjaxValue.search('rdinsert') != -1 ) {
                    checkAll();
                 }
                    
             } else {
                // In case of validation error / failure, dont hide the main panel. 
                // Dont refresh, in case of error.  
                refreshSearch = false;
              }   

            //alert("refreshSearch "+refreshSearch);
            if(refreshSearch) {
                    //alert('call refresh ...');
                    submitAjax(actionPathForRefreshSearch,'searchrefresh','0','searchResult1');
                    refreshSearch = false;
            }
        }
        
      } else {
        alert("Problem with server response:\n " + req.statusText+" "+req.status);
        document.getElementById('indicator').style.display='none';
        refreshSearch = false;
      }
    } else {
        document.getElementById('indicator').style.display='block';
        
    }
  }
 
 /**
  * gets the contents of the form as a URL encoded String
  * suitable for appending to a url
  * @param formName to encode
  * @return string with encoded form values , beings with &
  */ 
 function getFormAsString(formName){
 	
 	//Setup the return String
 	returnString ="?"; //initialize query string
 	
  	//Get the form values
 	formElements=document.forms[formName].elements;
 	
 	//loop through the array , building up the url
 	//in the form /strutsaction.do?name=value&
 	
 	for ( var i=formElements.length-1; i>=0; --i ){
 		//we escape (encode) each value
                if (formElements[i].className == 'radio') {
                    if (formElements[i].checked) { 
                        returnString=returnString+(escape(formElements[i].name)+"="+escape(formElements[i].value)+"&");                
                    }
                } else {
                      if(formElements[i].name == 'searchByGroupDescription') {
                          // encode '+' with '%2B'  
                          returnString=returnString+(escape(formElements[i].name)+"="+((formElements[i].value).replace(/\+/g,"%2B"))+"&");
                      } else {
                          returnString=returnString+(escape(formElements[i].name)+"="+escape(formElements[i].value)+"&");
                      }
                }
        }
        // Solve problen with radio button. (Rajan)
        // prob: above for loop will add checked as well as unchecked radio. 
        // To avoid this, we are filtering only checked radio using class name.
  
       
        	
 	//return the values
        return returnString; 
 }
 
 /**
 * Splits the text into <span> elements
 * @param the text to be parsed
 * @return array of <span> elements - this array can contain nulls
 */
 function splitTextIntoSpan(textToSplit){
 
  	//Split the document
 	returnElements=textToSplit.split("</span>")
 	
 	//Process each of the elements 	
 	for ( var i=returnElements.length-1; i>=0; --i ){
 		
 		//Remove everything before the 1st span
 		spanPos = returnElements[i].indexOf("<span");		
 		
 		//if we find a match , take out everything before the span
 		if(spanPos>0){
 			subString=returnElements[i].substring(spanPos);
 			returnElements[i]=subString;
 		
 		} 
 	}
 	
 	return returnElements;
 }
 
 /*
  * Replace html elements in the existing (ie viewable document)
  * with new elements (from the ajax requested document)
  * WHERE they have the same name AND are <span> elements
  * @param newTextElements (output of splitTextIntoSpan)
  *					in the format <span id=name>texttoupdate
  */
 function replaceExistingWithNewHtml(newTextElements){
       
 	//loop through newTextElements
 	for ( var i=newTextElements.length-1; i>=0; --i ){
  
 		//check that this begins with <span
 		if(newTextElements[i].indexOf("<span")>-1){
 			
 			//get the name - between the 1st and 2nd quote mark
 			startNamePos=newTextElements[i].indexOf('"')+1;
 			endNamePos=newTextElements[i].indexOf('"',startNamePos);
 			name=newTextElements[i].substring(startNamePos,endNamePos);
 			
 			//get the content - everything after the first > mark
 			startContentPos=newTextElements[i].indexOf('>')+1;
 			content=newTextElements[i].substring(startContentPos);
 			
 			//Now update the existing Document with this element
 			
	 			//check that this element exists in the document
	 			if(document.getElementById(name)){
	 			
	 				//alert("Replacing Element:"+name);
	 				document.getElementById(name).innerHTML = content;
	 			} else {
	 				//alert("Element:"+name+"not found in existing document");
	 			}
 		}
 	}
 }
