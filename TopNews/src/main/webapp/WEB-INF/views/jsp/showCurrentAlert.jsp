<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Top News: ${category}</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="admin_panel/header.jsp"></jsp:include>
	<jsp:include page="admin_panel/navigation.jsp">
		<jsp:param value="${categories}" name="categories" />
	</jsp:include>
	<section id="contentSection">
	<div class="row">
		<div class="col-lg-8 col-md-8 col-sm-8">
			<div class="left_content">
				<div class="single_page">
					<ol class="breadcrumb">
						<li><a href="./">Home</a></li>
						<li><a>${alert.sender}</a></li>
					</ol>
					<h1>${alert.subject}</h1>
					<div class="post_commentbox">
						<a href="#"><i class="fa fa-user"></i>${alert.sender}</a> 
						<span><i class="fa fa-calendar"></i>${alert.date}</span> 
					</div>
					
					<div style="text-align: justify; text-indent: 50px;" class="single_page_content">
						<img class="img-center" src="${alert.photo}" alt="">
						${alert.text}
					</div>
					<div class="social_link">
						<ul class="sociallink_nav">
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
							<li><a href="#"><i class="fa fa-linkedin"></i></a></li>
							<li><a href="#"><i class="fa fa-pinterest"></i></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="user_panel/footer.jsp"></jsp:include>
