<%@ page import="java.io.*, java.net.*" %>
<HTML>
    <HEAD>
        <TITLE>CLIENT.jsp</TITLE>
    </HEAD>
    	<script>
    		function ClearFields() 
    		{
		     document.getElementById("number1").value = "";
		     document.getElementById("number2").value = "";
		     document.getElementById("bits").value = "";
		}
    	</script>
        <BODY>
        <form action="res.jsp">
        <br><br><br><br>
	<center>Enter two numbers:</center>
	<br>
	<center><input type="text" name="number1" id="number1"/></center>
	<br>
	<center><input type="text" name="number2" id="number2"/></center>
	<br><br>

	<center>Enter number of bits: </center>
	<br>
	<center><input type="text" name="bits" id="bits"/></center>
	<br><br>
	
	<center><input type="submit" name="sub" id="sub" value="Multiply"/></center>
	<br><br>
	<center><input type="button" name="sub" id="sub" value="Clear fields" onclick="ClearFields();"/></center>
    </BODY>
</HTML>
