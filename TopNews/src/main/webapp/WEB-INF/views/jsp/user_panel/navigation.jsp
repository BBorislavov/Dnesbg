<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<section id="navArea"> <nav class="navbar navbar-inverse"
		role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#navbar" aria-expanded="false"
			aria-controls="navbar">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>
	<div id="navbar" class="navbar-collapse collapse">
		<ul class="nav navbar-nav main_nav">
			<li class="active"><a href="./"><span
					class="fa fa-home desktop-home"></span><span class="mobile-show">Home</span></a></li>
					<li><a href="Category?name=World&page=1"><spring:message code="World" text="World" /></a></li>
			<c:forEach var="category" items="${allCategories}">
				<li class="dropdown"><a href="Category?name=${category.key}&page=1" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="${category.key}" text="${category.key}" /></a>
					<ul class="dropdown-menu" role="menu">
						<c:forEach var="name" items="${category.value}">
						<c:if test="${not empty name}">
							<li><a href="Category?name=${name}&page=1"><spring:message code="${name}" text="${name.replaceAll('%20',' ')}" /></a></li>
						</c:if>
						<c:if test="${empty name}">
							<li><a href="Category?name=${category.key}&page=1">${category.key}</a></li>
						</c:if>
						</c:forEach>
					</ul>
					</li>
			</c:forEach>
			<c:choose>
				<c:when test="${not empty sessionScope.isAdmin}">
				</c:when>
				<c:when test="${not empty sessionScope.user}">
					<li><a style="color: red" href="Alert"><spring:message code="alertUs" text="Alert us" /></a></li>
					<li><a href="Favourites?page=1"><spring:message code="Favourites" text="Favourites" /></a></li>
				</c:when>
			</c:choose>
			<li><a href="Weather"><spring:message code="Weather" text="default text" /></a></li>	
		</ul>
	</div>
	</nav> </section>
</body>
</html>