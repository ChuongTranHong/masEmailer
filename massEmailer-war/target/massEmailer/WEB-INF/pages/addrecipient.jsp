<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add Recipient</title>
<link href="../assets/css/app.css" rel="stylesheet">
<link href="../assets/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div style="width:100%">
	  <form class="form-horizontal" method="post">
     	<div class="control-group form-header" >
	    	<h3>Add recipient</h3>
	    </div>
	    <div class="control-group">
	    	<label class="control-label" for="firstname">First Name</label>
	    	<div class="controls">
	    	<input type="text" class="span5" id="firstname" placeholder="First Name" name="firstName">
	    </div>
	    </div>
	    <div class="control-group">
	    	<label class="control-label" for="lastname">Last Name</label>
	    	<div class="controls">
	    		<input type="text" class="span5" id="lastname" placeholder="Last Name" name="lastName">
	    	</div>
	    </div>
	    <div class="control-group">
	    	<label class="control-label" for="email">Email</label>
	    	<div class="controls">
	    		<input type="text" class="span5" id="email" placeholder="Email: e.g. raymond@odd.com" name="email">
	    	</div>
	    </div>
	    <div class="controls">
	    	<label id="lblStatusMessage" class="${addRecipientResult ? 'text-sucess' : 'text-error' } ">${addRecipientResultMessage} </label>
	    </div>
	    <div class="control-group">
		    <div class="controls">
		    	<button type="submit" class="btn btn-primary btn-large">Add</button>
		    </div>
	    </div>
	   </form>
	
	</div>

</body>
</html>