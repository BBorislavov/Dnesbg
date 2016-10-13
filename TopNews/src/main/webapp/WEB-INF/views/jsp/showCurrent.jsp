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
	<c:if test="${not empty isAdmin}">
		<jsp:include page="admin_panel/header.jsp"></jsp:include>
	<jsp:include page="admin_panel/navigation.jsp">
		<jsp:param value="${categories}" name="categories" />
	</jsp:include>
	</c:if>
	<c:if test="${empty isAdmin}">
	<jsp:include page="./user_panel/header.jsp"></jsp:include>
 <jsp:include page="./user_panel/navigation.jsp"></jsp:include>
	</c:if>
	<section id="contentSection">
	<div class="row">
	<div class="col-lg-12 col-md-12">
			<div class="latest_newsarea">
				<span>Latest News</span>
				<ul id="ticker01" class="news_sticker">
				 <c:forEach var="latestNews" items="${latestNews}">
					<li><a href="#"><img src="${latestNews.photoUrl}"
							alt="">${latestNews.title}</a></li>
				</c:forEach>
				</ul>
				<div class="social_area">
					<ul class="social_nav">
						<li class="facebook"><a href="#"></a></li>
						<li class="twitter"><a href="#"></a></li>
						<li class="flickr"><a href="#"></a></li>
						<li class="pinterest"><a href="#"></a></li>
						<li class="googleplus"><a href="#"></a></li>
						<li class="vimeo"><a href="#"></a></li>
						<li class="youtube"><a href="#"></a></li>
						<li class="mail"><a href="#"></a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-lg-8 col-md-8 col-sm-8">
			<div class="left_content">
			<br>
				<div class="single_page">
					<ol class="breadcrumb">
							<li><a href="./">Home</a></li>
						<li><a href="Category?name=${category}">${category}</a></li>
					</ol>
					<h1>${news.title}</h1>
					<div class="post_commentbox">
						<a href="#"><i class="fa fa-user"></i>IT Talents team</a> <span><i
							class="fa fa-calendar"></i>${news.dateOfPost}</span> <a href="#"><i
							class="fa fa-tags"></i>${category}</a> <a href="#"><i
							class="fa fa-eye"></i>${news.rating+1}</a>
					</div>

					<div style="text-align: justify; text-indent: 50px;"
						class="single_page_content">
						<img class="img-center" src="${news.photoUrl}" alt="">
						${news.text}
					</div>
					<div>
						<form:form id="form" action="AddComment" class="contact_form"
							commandName="comment">
							<form:textarea id="newComment" path="text" class="form-control"
								type="text" cols="30" rows="10"
								placeholder="Enter your comment*" />
							<input type="submit" id="add-comment" value="Send" />
						</form:form>
						<br>
					</div>
					<div id="comments"></div>
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
		<div class="col-lg-4 col-md-4 col-sm-4">
			<aside class="right_content">
			<div class="single_sidebar">
			<br>
				<h2>
					<span>Latest News in ${category}</span>
				</h2>
				<c:forEach var="lastNews" items="${lastNews}">
					<ul class="spost_nav">
						<li>
							<div class="media wow fadeInDown">
								<a href="./News?category=${category}&id=${lastNews.id}"
									class="media-left"> <img alt="" src="${lastNews.photoUrl}">
								</a>
								<div class="media-body">
									<a href="./News?category=${category}&id=${lastNews.id}"
										class="catg_title">${lastNews.title}
										<p>${lastNews.dateOfPost}</p>
									</a>
								</div>
							</div>
						</li>
					</ul>
				</c:forEach>
			</div>
		</div>
	</div>
	<input type='hidden' id="currUsername"
		value="${sessionScope.user.username}" /> <input type='hidden'
		id="isAdmin" value="${sessionScope.isAdmin}" /> <jsp:include
		page="admin_panel/footer.jsp"></jsp:include> <script
		type="text/javascript">
			$(document)
					.ready(
							function() {
								function commentsRender(data) {

									$("#comments").empty();
									var object, commentString, deleteButton, currUsername = $(
											'#currUsername').val(), isAdmin = $(
											'#isAdmin').val();
									console.log(isAdmin);
									for (index in data) {
										object = data[index];

										if (isAdmin
												|| (currUsername !== '' && currUsername === object.user)) {

											deleteButton = '<a href="DeleteComment?id='
													+ object.commentId
													+ '" class="deleteButton" >Delete</a>';
										} else {
											deleteButton = '';
										}

										commentString = '<div class="comment">'
												+ '<h5>User: ' + object.user
												+ ' | Posted at: 	'
												+ object.date + '</h5>'
												+ '<h4>Comment: ' + object.text
												+ '</h4>' + deleteButton
												+ '</div>';

										$("#comments").append(commentString);
									}

									$('.deleteButton')
											.click(
													function(event) {

														event.preventDefault();
														if (confirm('Are you sure you want to delete this comment ?')) {
															$
																	.ajax({
																		url : 'http://localhost:8080/TopNews/'
																				+ $(
																						this)
																						.attr(
																								'href'),
																		type : 'DELETE',
																		success : function(
																				res) {
																			reloadComments(res);
																		}
																	});
														}
													});

								}

								function reloadComments(data) {
									if (data) {
										commentsRender(data);
									} else {
										$
												.get(
														"http://localhost:8080/TopNews/ShowComments",
														function(data) {
															commentsRender(data)
														});
									}
								}

								$('#add-comment')
										.click(
												function(e) {
													e.preventDefault();
													var newCommentVal = $(
															'#newComment')
															.val();
													$
															.ajax({
																url : "http://localhost:8080/TopNews/AddComment",
																method : 'POST',
																data : {
																	text : newCommentVal
																},
																success : function(
																		res) {
																	reloadComments(res);
																}
															});
												});

								reloadComments();

							});
		</script>