<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*, java.net.*, java.lang.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String n1 = request.getParameter("num1");
	String n2 = request.getParameter("num2");
	String b = request.getParameter("bit");
	
	int num1 = Integer.parseInt(n1);
	int num2 = Integer.parseInt(n2);
	int bit = Integer.parseInt(b);
	
	Socket s = new Socket("localhost",7876);
	DataInputStream dis = new DataInputStream(s.getInputStream());
	DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	
	dos.writeInt(num1);
	dos.writeInt(num2);
	dos.writeInt(bit);
	dos.flush();
	
	String ans = dis.readUTF();
%>

Ans is:
<%=ans%>
</body>
</html>