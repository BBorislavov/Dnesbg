<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="addCategory" text="Add Category" /></title>
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
					<h2><spring:message code="addCategory" text="Add Category" /></h2>
					<h3 style="color: green"><spring:message code="${message}" text="" /></h3><br> 
					<form:form class="contact_form" method="POST" action="AddCategory" commandName="category">
					<h4><span><spring:message code="mainCategory" text="Main category" />:</span></h4>
						<form:select path="name" class="form-control" type="text">
						<option value=""><spring:message code="newCategory" text="New Category" /></option>
							<c:forEach var="categories" items="${categories}">
								<option value="${categories}"><spring:message code="${categories}" text="Category" /></option>
							</c:forEach>
						</form:select>
						<h4><span><spring:message code="subCategory" text="Subcategory" />:</span></h4>
						<form:input path="subcategory" class="form-control" type="text" />
						<input type="submit" value="<spring:message code="addToCategories" text="Add Category" />">
					</form:form>
				</div>
			</div>
		</div>
	</div>
	</section>
	<jsp:include page="user_panel/footer.jsp"></jsp:include>