function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
  if ((evt.keyCode == 13) && (node.type=="number"))  {return false;} 
  if ((evt.keyCode == 13) && (node.type=="checkbox"))  {return false;} 
  if ((evt.keyCode == 13) && (node.type=="radio"))  {return false;} 
  if ((evt.keyCode == 13) && (node.type=="password"))  {return false;} 
  if ((evt.keyCode == 13) && (node.type=="email"))  {return false;} 
} 

document.onkeypress = stopRKey; 