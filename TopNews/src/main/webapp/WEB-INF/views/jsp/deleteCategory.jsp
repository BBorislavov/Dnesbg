<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Delete Category</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
</head>
<body>
<jsp:include page="admin_panel/header.jsp"></jsp:include>
 <jsp:include page="admin_panel/navigation.jsp">
 <jsp:param value="${categories}" name="categories"/>
 </jsp:include>
	<section id="contentSection">
	<div class="row">
		<div class="col-lg-7 col-md-7 col-sm-7">
			<div class="left_content">
				<div class="contact_area">
					<h2>Delete Category</h2>
					<br /> 
					<h3 style="color: green">${message}</h3>
					<form:form class="contact_form" method="POST" action="DeleteCategory" commandName="category">
						<form:select path="subcategory" class="form-control" type="text">
							<c:forEach var="categories" items="${categories}">
								<option value="${categories}">${categories}</option>
							</c:forEach>
						</form:select>
						<br />
						<input type="submit" value="Delete Category">
					</form:form>
				</div>
			</div>
		</div>
	</div>
	</section>
	<jsp:include page="admin_panel/footer.jsp"></jsp:include>