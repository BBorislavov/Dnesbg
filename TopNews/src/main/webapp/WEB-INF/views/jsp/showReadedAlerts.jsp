<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="readedAlerts" text="Readed Alerts" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
</head>
<body>
	<c:if test="${not empty isAdmin}">
		<jsp:include page="admin_panel/header.jsp"></jsp:include>
		<jsp:include page="admin_panel/navigation.jsp">
			<jsp:param value="${categories}" name="categories" />
		</jsp:include>
	</c:if>
	<c:if test="${empty isAdmin}">
		<jsp:include page="user_panel/header.jsp"></jsp:include>
		<jsp:include page="user_panel/navigation.jsp">
			<jsp:param value="${categories}" name="categories" />
		</jsp:include>
	</c:if>
	<div class="single_sidebar">
		<h2>
			<span><spring:message code="readedAlerts" text="Readed Alerts" /></span>
		</h2>
		<div class="newsSection">
			<div class="single_page">
				<c:choose>
					<c:when test="${not empty alerts}">
						<ul>
							<c:forEach var="email" items="${alerts}">
								<h5>
									<a><spring:message code="User" text="User" />:
										${email.sender}</a>
								</h5>
								<h3>
									<a href="./Alerts?id=${email.id}">${email.subject}</a> <a
										class="deleteAlert" href="./DeleteAlert?id=${email.id}"><i style="color: red" class="fa fa-times" aria-hidden="true"></i></a>
								</h3>
								<h5>
									<span><i class="fa fa-calendar"></i> ${email.date}</span>
								</h5>
								<br>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<ul>
							<h3 style="color: red" align="center">
								<spring:message code="emptyBox" text="The alert box is empty" />
							</h3>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<jsp:include page="user_panel/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$('.deleteNews').click(function(e) {
			if (!confirm('Are you sure you want to delete this alert?')) {
				e.preventDefault();
			}

		});
	</script>