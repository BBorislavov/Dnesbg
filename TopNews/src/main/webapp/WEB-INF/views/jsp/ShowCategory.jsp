<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Top News: ${name}</title>
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
			<span>
			<spring:message code="AllNewsIn" text="All news in" /> 
			<spring:message code="${name}" text="${name}" /> 
 			<spring:message code="Category" text="Category" /></span>
		</h2>
		<div class="newsSection">
			<div class="single_page">
				<ul>
					<c:forEach var="listCategory" items="${news}">
						<h3>
							<a href="./News?category=${name}&id=${listCategory.id}">${listCategory.title}
							</a>
							<c:if test="${not empty isAdmin}">
							<i class="fa fa-arrow-up" aria-hidden="true"></i>
							<i class="fa fa-arrow-down" aria-hidden="true"></i>
							<a style = "color: red" class="deleteNews" href="./DeleteNews?id=${listCategory.id}">
							<i class="fa fa-times" aria-hidden="true"></i>
							</a>
							</c:if>
							<c:if test="${empty isAdmin}">
							<c:if test="${not empty user}">
							<a class="addFavourite" href="./AddFavourite?id=${listCategory.id}">
							<i class="fa fa-heart" aria-hidden="true"></i>
							</a>
							</c:if>
							</c:if>
						</h3>
						<a style = "border: 5px solid #89d2f0" href="./News?category=${name}&id=${listCategory.id}"><img
							height="100" width="150" src="${listCategory.photoUrl}"></a>
						<h5>${listCategory.text}<a
								href="./News?category=${name}&id=${listCategory.id}"><img
								src="./images/more.gif"></a>
						</h5>
						<div class="post_commentbox">
							<span><i class="fa fa-calendar"></i>${listCategory.dateOfPost}</span>
							<span><i class="fa fa-eye"></i>${listCategory.rating}</span>
						</div>
						<br>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<c:forEach var="pages" items="${pages}">
	<a style="font-size: 40px" href="Category?name=${name}&page=${pages}">${pages}</a>
	</c:forEach>
		<jsp:include page="user_panel/footer.jsp"></jsp:include>

	<script type="text/javascript">
		$('.deleteNews').click(function(e) {
			if (!confirm('Are you sure you want to delete this new ?')) {
				e.preventDefault();
			}

		});
	</script>