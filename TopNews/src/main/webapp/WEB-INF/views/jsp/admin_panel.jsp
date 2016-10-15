<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Panel</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
</head>
<body>
<jsp:include page="admin_panel/header.jsp"></jsp:include>
 <jsp:include page="admin_panel/navigation.jsp">
 <jsp:param value="${categories}" name="categories"/>
 </jsp:include>
 <span><a  href="UnreadedAlerts"><i style="color: red; font-weight: bold;" class="fa fa-folder-o"> <spring:message code="unreaded" text="unreaded" /> (${unreaded})</color></i></a></span> 
   <span><a  href="ReadedAlerts"><i style="color: green; font-weight: bold;" class="fa fa-folder-open-o"> <spring:message code="readed" text="readed" /> (${readed})</color></i></a></span>
  <h3><spring:message code="Welcome" text="Welcome" />, ${user.username}</h3>
 <jsp:include page="user_panel/footer.jsp"></jsp:include>