<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Compose Email</title>
<link href="../assets/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/css/app.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>

<div style="width:100%">
     <form class="form-horizontal" action="send.htm" method="post">
	     <div class="control-group form-header" >
	    	<h3>Compose Email</h3>
	    </div>
	    <div class="control-group">
		    <label class="control-label" for="recipient">Recipients</label>
		    <div class="controls">
		    	<input type="text" class="span5" id="recipients" name="recipients" placeholder="recipients email">
		    </div>
	    </div>
	    <div class="control-group">
		    <label class="control-label" for="subject">Subject</label>
		    <div class="controls">
		    	<input type="text" class="span5" id="subject" name="subject" placeholder="subject">
		    </div>
	    </div>
	    <div class="control-group">
		    <label class="control-label" for="message">Message</label>
		    <div class="controls">
		    	<textarea id="message" name="message" rows="15" class="span5" placeholder="email message here"></textarea>
		    </div>
	    </div>
	    <div class="controls">
	    	<label id="lblStatusMessage" class="${success?'text-success':'text-error'}">${status}</label>
	    </div>
	    <div class="control-group">
	    <div class="controls">
	    
	    <button type="submit" class="btn btn-primary btn-large">Send</button>
	    </div>
	    </div>
    </form>

</div>
   

</body>
</html>