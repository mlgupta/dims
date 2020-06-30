/***********************Opening Modal and Modalless Widnows Starts **************************/
var winModalWindow
 
function openWindow(url,name,height,width,top,left,alignCenter,otherfeatures)
{
	if (alignCenter){
				top=(window.screen.availHeight/2)-(height/2);
				left=(window.screen.availWidth/2)-(width/2);
	 }  
    var features="width=" + width + "px,height=" + height +"px,";
		features=features + "left=" + left + "px," + "top=" + top + "px,";    
		features=features+otherfeatures;
    var obj_window=window.open (url,name,features);
    obj_window.focus();
    return window.open (url,name,features);

 }


 
function openModal(url, height,width,top,left,alignCenter)
{
	if (alignCenter){
				top=(window.screen.availHeight/2)-(height/2);
				left=(window.screen.availWidth/2)-(width/2);
	 }  
    
  if (window.showModalDialog)
  {
    var features="dialogWidth:" + width + "px;dialogHeight:" + height + "px;";
    features=features + "dialogLeft:" + left + "px;" + "dialogTop:" + top + "px;";
    window.showModalDialog(url,null,features)
  }
  else 
  {
    
    var features="dependent=yes,width=" + width + "px,height=" + height +"px,";
		features=features + "left=" + left + "px," + "top=" + top + "px,";    
    window.top.captureEvents (Event.CLICK|Event.FOCUS)
    window.top.onclick=false
    window.top.onfocus=handleFocus 
    winModalWindow = window.open (url,"ModalChild",features)
    winModalWindow.focus()
  }
}

 
function handleFocus()
{
  if (winModalWindow)
  {
    if (!winModalWindow.closed)
    {
      winModalWindow.focus()
    }
    else
    {
      window.top.releaseEvents (Event.CLICK|Event.FOCUS)
      window.top.onclick = ""
    }
  }
  return false
}

/***********************Opening Modal and Modalless Widnows Ends **************************/

/********* getRadioSelectedIndex text starts ********************************/
/*getRadioSelectedIndex starts*/
function getRadioSelectedIndex(radioButton){
    var returnValue = -2;
    if (typeof radioButton!="undefined"){
      if (typeof radioButton.length !="undefined"){
        for(index = 0 ; index < radioButton.length ;index++){
          if(radioButton[index].checked){
            return index;
          }
        }
      }else{
        if(radioButton.checked){
          return -1;
        }
      }
    }else{
      return returnValue;
    }    
    return returnValue;      
}

/********* getRadioSelectedIndex text ends ********************************/
/*****************Findobj and ShowHider Layers from Dream Weaver - Starts*****************/

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'':(v=='hide')?'none':v; }
    obj.display=v; }
}
/*****************Findobj and ShowHider Layers from Dream Weaver - Ends*****************/


/*****************Trims a string - Starts*****************/
function trim(inputString) {
// Removes leading and trailing spaces from the passed string. Also removes
// consecutive spaces and replaces it with one space. If something besides
// a string is passed in (null, custom object, etc.) then return the input.
if (typeof inputString != "string") { return inputString; }

var retValue = inputString;
var ch = retValue.substring(0, 1);

while (ch == " ") { // Check for spaces at the beginning of the string
retValue = retValue.substring(1, retValue.length);
ch = retValue.substring(0, 1);
}

ch = retValue.substring(retValue.length-1, retValue.length);
while (ch == " ") { // Check for spaces at the end of the string
retValue = retValue.substring(0, retValue.length-1);
ch = retValue.substring(retValue.length-1, retValue.length);
}

return retValue; // Return the trimmed string back to the user
} // Ends the "trim" function 

/*****************Trims a string - Ends*****************/

/*Integer only text starts*/
function integerOnly(e){
	var key;
	if (window.event){
	   key = window.event.keyCode;
	}else if (e){
	   key = e.which;
	}else{
	   return true;
	}
	// control keys
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) ){
	   return true;
	// numbers
	}else if ((("0123456789").indexOf(String.fromCharCode(key)) > -1)){
			return true;
	}else{
	   return false;
	}
}

/********* Integer only text ends ********************************/

/*Decimal only text starts */
function decimalOnly(thisField, e, dec){
	var key;
	var keychar;

	if (window.event){
	   key = window.event.keyCode;
	}else if (e){
	   key = e.which;
	}else{
	   return true;
  }
	
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// numbers
	else if ((("0123456789.").indexOf(keychar) > -1)){
		if (thisField.value.indexOf(".")>-1){
			if ((("0123456789").indexOf(keychar) > -1)){
				if ((thisField.value.length) >(thisField.value.indexOf(".")+ dec)) {
				 return false;
				}else{
				return true;
				}
			}else{
			 return false;
			}
		}else{
			return true;
		}
	}else{
	   return false;
	}
}
/************** Decimal only text ends ***********************************/

/* phoneNos only text starts */
function phoneNosOnly(e){
	var key;
	if (window.event){
	   key = window.event.keyCode;
	}else if (e){
	   key = e.which;
	}else{
	   return true;
	}
	// control keys
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) ){
	   return true;
	// numbers
	}else if ((("0123456789-()+ ").indexOf(String.fromCharCode(key)) > -1)){
			return true;
	}else{
	   return false;
	}
}
/********* phoneNos only text ends***************************/

/************** Handles Enter Key ***********************************/
function handleEnter(thisField,e){
	var key;
  var keychar;
	if (window.event){
	   key = window.event.keyCode;
	}else if (e){
	   key = e.which;
	}else{
	   return true;
  }
	
	keychar = String.fromCharCode(key);
	// control keys
	if (key==13) {
    return 1;
    }
}
/************** Handles Enter Key ends***********************************/

/************************************Ajax Related Starts***********************/
/*
method - GET or POST
url - url to sumbit
asynchronous - true or false
sendData - data to be sent in the form text=abc&value=bcd etc
readyStateMethod - method that handles the response
cache - true or false
*/

function ajaxRequest(method,url,asynchronous,sendData,readyStateMethod,cache){
  var httpRequest=null;
  if (window.XMLHttpRequest){     // Object of the current windows
    httpRequest = new XMLHttpRequest();     // Firefox, Safari, ...
  }else{
    if (window.ActiveXObject){   // ActiveX version
      // Internet Explorer
      try{
        httpRequest=new ActiveXObject("Msxml2.XMLHTTP");
      }catch(e){
        try{
          httpRequest=new ActiveXObject("Microsoft.XMLHTTP");
        }catch(e){
          httpRequest=null;
        }
      }
    }
  }
  if(httpRequest!=null){
    httpRequest.onreadystatechange=function(){
//      try {
//        eval('readyStateMethod(httpRequest)');
//      }catch(e){
//        alert('Caught Exception: ' + e.description);
//      }

      eval('readyStateMethod(httpRequest)');
    };

    httpRequest.open(method,url,asynchronous);
    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if(cache!==null && !cache){
      httpRequest.setRequestHeader('Cache-Control','no-cache');
    }
    if(httpRequest.overrideMimeType){
      httpRequest.overrideMimeType('text/html');
    }
    httpRequest.send(sendData);
  }else{
    alert('Unable to Make Request');
  }
}

/************************************Ajax Related Ends***********************/
