/*  
 *    Resident Tracking utility routines.
 */

  // global variables
 var clearPanelId_1 = 'searchResult1';
 var clearPanelId_2 = 'mainpanel';
 var clearPanelId_3 = null;
    
   function clearSearchResult(l_divId1, l_divId2, l_divId3) {
        
        if(l_divId1) {
            var divObject = document.getElementById(l_divId1);
            if(divObject) {
                divObject.style.display = 'none';
            }
        }
        if(l_divId2) {
            var divObject = document.getElementById(l_divId2);
            if(divObject) {
                divObject.style.display = 'none';
            }
        }
        if(l_divId3) {
            var divObject = document.getElementById(l_divId3);
            if(divObject) {
                divObject.style.display = 'none';
            }
        }
        
        var divObject = document.getElementById('feedback');
            if(divObject) {
                divObject.innerHTML = '</BR>';
        }
            
    }


    function jump(url) {
        windowProperty = 'status=no, scrollbars=yes, resizable=yes, left=50, height=800, width=950, top=50';
        openInNewWindow = '_blank';
        
        window.open(url,openInNewWindow,windowProperty,"");
        
    }
    function fireStatus(contextpath,rdkey,username) {
        windowProperty = 'status=no, scrollbars=yes, resizable=yes, left=50, height=600, width=800, top=50';
        openInNewWindow = '_blank';
        url = contextpath;
        url = url+'/ardbrowse.ew?submitValue=firestatus&ruledetailkey='+rdkey+'&username='+username;
        window.open(url,openInNewWindow,windowProperty,"");
        
    }
    function popup()
    {
        var p=window.createPopup()
        var pbody=p.document.body
        pbody.style.backgroundColor="White"
        pbody.style.border="solid black 1px"
        pbody.innerHTML="This is a pop-up! Click outside to close."
        p.show(150,450,500,450,document.body)
    }

    function changeAllStatus(suspenseStatus) {
    
        //Get the form values
 	formElements=document.forms[1].elements;
        for ( var i=formElements.length-1; i>=0; --i ){
            if (formElements[i].className == 'radio' ) {
                if(suspenseStatus == formElements[i].value ) {
                    formElements[i].checked = true;
                } else {
                    formElements[i].checked = false;
                }
                
             }
            
        }
    }


    /* Start: To solve problem with hiding tables inside <div> */
    function drawBorder(tagname) {
           
            if(!tagname )return true;
            
            if(tagname.border == '0px'){
                tagname.border ='1px';
               
            }
   
   }
   
    function removeBorder(tagname) {
           
            if(!tagname )return true;
            
            if(tagname.border ='1px'){
                tagname.border ='0px';
               
            } 
   
   }
   /* End: To solve problem with hiding tables inside <div> */
   
   /* Start: To solve problem with search result display in merge screen */
    var divId = 'searchResult1' ;
    function changeResultArea(areaName) {
        divId = areaName ;
    }
    /* End: To solve problem with search result display in merge screen */

   function nextPage(path) {
	 submit("./"+path);
 }

	

	function toggleMe(a){
	  var e=document.getElementById(a);
	  if(!e)return true;
	  if(e.style.display=="none"){
	    e.style.display="block"
	  } else {
	    e.style.display="none"
	  }
	  return true;
	}

    var oldBgColor;

	var rowOldColor;
	function tableRowFocused1(tag) {
		tagobj = tag; 
						
			if(!tagobj )return true;
			
		  	if(tagobj.style.color == '#996600'){
		    tagobj.style.color = rowOldColor;
		    
		    
	  	  	} else {
		   
		    rowOldColor = tagobj.style.color;
			tagobj.style.color = '#996600';
	  	  	}
	  	  
	  		return false; 
	}
	
	function tableRowFocused(tag) {
		tagobj = tag; 
						
			if(!tagobj )return true;
			
		  	if(tagobj.style.color='#000000'){
		   // tagobj.style.backgroundColor=oldBgColor ;
                    
		    tagobj.style.color='#03328A';
		    } else {
		    
		 //   oldBgColor = tagobj.style.backgroundColor;
		 //   tagobj.style.backgroundColor="#efefef"
		 //   tagobj.style.fontWeight='bold';
                      
                      tagobj.style.color='#000000';
	  	  	}
	  	  
	  		return false; 
	}
	
	// Function for expand-collapse the result area

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

    var g_oldcolor = null;
    var g_oldobj   = null;

	function selectedRow(tagname) {

		if(g_oldcolor == null) {
			g_oldcolor = tagname.style.backgroundColor;
			g_oldobj   = tagname;
			tagname.style.backgroundColor = '#DDDDDD';
		} else {
			g_oldobj.style.backgroundColor = g_oldcolor;
			g_oldcolor = tagname.style.backgroundColor;
			g_oldobj   = tagname;
			tagname.style.backgroundColor = '#DDDDDD';
		}
	}

        
                function getWindowHeight() {
			var windowHeight = 0;
			if (typeof(window.innerHeight) == 'number') {
				windowHeight = window.innerHeight;
			}
			else {
				if (document.documentElement && document.documentElement.clientHeight) {
					windowHeight = document.documentElement.clientHeight;
				}
				else {
					if (document.body && document.body.clientHeight) {
						windowHeight = document.body.clientHeight;
					}
				}
			}
			return windowHeight;
		}
		function setFooter() {
			if (document.getElementById) {
				var windowHeight = getWindowHeight();
				if (windowHeight > 0) {
					var contentHeight = document.getElementById('content').offsetHeight;
                                        var footerElement = document.getElementById('footer');
                                        var footerHeight  = footerElement.offsetHeight;
					if (windowHeight - (contentHeight + footerHeight) >= 0) {
						footerElement.style.position = 'relative';
                                                footerElement.style.top = (windowHeight - 400 ) + 'px';
                                        }
					else {
						footerElement.style.position = 'static';
					}
                                        footerElement.style.display ='block';
				}
			}
		}