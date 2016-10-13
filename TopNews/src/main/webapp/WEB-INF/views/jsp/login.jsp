<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

</head>
<body>
	<div align="center">
		<h2>Hello, guest. Please login.</h2>
		<table border="0" width="90%">
			<form:form action="Login" commandName="user">
				<tr>
					<td align="left" width="20%">Username:</td>
					<td align="left" width="40%"><form:input path="username" size="30" /></td>
					<td align="left"><form:errors path="username" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><form:password path="password" size="30" /></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="center"><input type="submit" value="Login" /></td>
					<td></td>
				</tr>
			</form:form>
		</table>
		<p style="color: red; font-weight: bold;">${message}</p>
	</div>
</body>
</html>