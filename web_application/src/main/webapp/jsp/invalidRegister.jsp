<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="./Register" method="post">
		<label>Username:</label> <input type="text" name="username" /> 
		<br />
		<label>Email: </label> <input type="text" name="email" /> 
		<br />
		<label>Password:</label> <input type="password" name="password" /> 
		<br /> <input
			type="submit" value="Register" />
	</form>
	<%
	String massage;
	if ((String)request.getAttribute("massage") != null) {
	massage = (String)request.getAttribute("massage");
	} else {
	massage = "";
	}%>
	<p style="color: red"><%= massage %></p>

</body>
</html>