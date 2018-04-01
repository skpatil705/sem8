<%@ page import="java.io.*, java.net.*" %>
<HTML>
    <HEAD>
        <TITLE>RESULT</TITLE>
    </HEAD>
        <BODY>
        <H1>RESULT</H1>
        <% 
        try
        {      
            String str1 = request.getParameter("number1");
            String str2 = request.getParameter("number2");
	    String b = request.getParameter("bits");

	    Socket s=new Socket("localhost",8776);  
	    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
	    DataInputStream inSocket = new DataInputStream(s.getInputStream());

	    dout.writeUTF(str1);  
	    dout.writeUTF(str2); 
	    dout.writeUTF(b);  
	    dout.flush();  

	    String strr;
	    
	    try
	    {
		    while(inSocket.available() == 0)
		    {
		    
		    	strr=(String)inSocket.readUTF();
		    	out.print("Result of "+str1+" * "+str2+" is: "+strr);
		    	%>
		    	<form action="Client.jsp">
        		<br><br>	
			<input type="submit" name="sub" id="sub" value="Back"/>
		    	<%
			inSocket.close();    
		    }
            }
            catch(Exception e){}
            dout.close();  
            s.close();  
    	}
    	catch(ConnectException e)
    	{%>
    		Please connect to the Server...
    	<%	
    	}  
        %>
    </BODY>
</HTML>
