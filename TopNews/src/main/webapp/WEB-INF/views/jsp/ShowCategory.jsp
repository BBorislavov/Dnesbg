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
	<section id="newsSection">
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="latest_newsarea">
				<span><spring:message code="LatestNews" text="Latest News" /></span>
				<ul id="ticker01" class="news_sticker">
					<c:forEach var="latestNews" items="${latestNews}">
						<li><a href="News?category=${latestNews.category}&id=${latestNews.id}"><img
								src="${latestNews.photoUrl}" alt="">${latestNews.title}</a></li>
					</c:forEach>
				</ul>
				<div class="social_area">
					<ul class="social_nav">
						<li class="facebook"><a href="http://www.facebook.com"
							target="_blank"></a></li>
						<li class="twitter"><a href="https://twitter.com/"
							target="_blank"></a></li>
						<li class="flickr"><a href="https://www.flickr.com/"
							target="_blank"></a></li>
						<li class="pinterest"><a href="https://www.pinterest.com/"
							target="_blank"></a></li>
						<li class="googleplus"><a href="https://plus.google.com/"
							target="_blank"></a></li>
						<li class="vimeo"><a href="https://vimeo.com/"
							target="_blank"></a></li>
						<li class="youtube"><a href="https://www.youtube.com/"
							target="_blank"></a></li>
						<li class="mail"><a href="https://www.google.com/gmail/"
							target="_blank"></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	</section>
	<section id="sliderSection">
	<div class="row">
		<div class="col-lg-8 col-md-8 col-sm-8">
			<div class="slick_slider">
				<ul>
					<c:forEach var="listCategory" items="${news}">
						<h3>
							<a href="./News?category=${name}&id=${listCategory.id}">${listCategory.title}
							</a>
							<c:if test="${not empty isAdmin}">
								<a style="color: red" class="deleteNews"
									href="./DeleteNews?id=${listCategory.id}"> <i
									class="fa fa-times" aria-hidden="true"></i>
									</a>
							</c:if>
							<c:if test="${not  isAdmin}">
								<c:if test="${not empty user}">
									<c:if test="${favourites.get(listCategory.id)>0}">
										
											<i style="color: red; cursor:pointer" data-id="${listCategory.id}"  class="fa fa-heart heart" aria-hidden="true"></i>
										
									</c:if>
									<c:if test="${favourites.get(listCategory.id)==0}">
											<i style="color: red; cursor:pointer" data-id="${listCategory.id}"  class="fa fa-heart-o heart" aria-hidden="true"></i>	
									</c:if>
								</c:if>
							</c:if>
						</h3>
						<a style="border: 5px solid #89d2f0"
							href="./News?category=${name}&id=${listCategory.id}"><img
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
				<ul style="text-align: center;">
				<c:if test="${param.page!=1}">
					<a href="Category?name=${name}&page=${param.page-1}" style="margin-top:3px;"><i
						class="fa fa-chevron-left fa-2x" aria-hidden="true"></i></a>
				</c:if>
				<c:forEach var="pages" items="${pages}">
					<a style="font-size: 20px;background: #89d2f0;color:white;margin-left:1px; padding:3px 5px 3px 5px; "
						href="Category?name=${name}&page=${pages}"><span><i>${pages}</i></span></a>
						
				</c:forEach>
				<c:if test="${param.page!= pages.size()}">
					<c:if test="${pages.size()>1}">
					<a href="Category?name=${name}&page=${param.page+1}"><i
						class="fa fa-chevron-right fa-2x" aria-hidden="true"></i></a>
					</c:if>
				</c:if>
				</ul>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4">
			<div class="latest_post">
				<h2>
					<span><spring:message code="PopularNews" text="default text" /></span>
				</h2>
				<div class="latest_post_container">
					<div id="prev-button">
						<i class="fa fa-chevron-up"></i>
					</div>
					<ul class="latest_postnav">
						<c:forEach var="popularNews" items="${popularNews}">
							<li>
								<div class="media">
									<a
										href="News?category=${popularNews.category}&id=${popularNews.id}"
										class="media-left"> <img alt=""
										src="${popularNews.photoUrl}">
									</a>
									<div class="media-body">
										<a
											href="News?category=${popularNews.category}&id=${popularNews.id}"
											class="catg_title">${popularNews.title} <br> <span><i
												class="fa fa-eye"></i>${popularNews.rating}</span></a>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<div id="next-button">
						<i class="fa  fa-chevron-down"></i>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4" style="float:right">
			<aside class="right_content">
			<div class="single_sidebar">
				<h2>
					<span>Most commented news</span>
				</h2>
				<ul class="spost_nav">
					<c:forEach var="mostCommented" items="${mostCommented}">
						<li>
							<div class="media wow fadeInDown">
								<a
									href="News?category=${mostCommented.category}&id=${mostCommented.id}"
									class="media-left"> <img alt=""
									src="${mostCommented.photoUrl}">
								</a>
								<div class="media-body">
									<a
										href="News?category=${mostCommented.category}&id=${mostCommented.id}"
										class="catg_title">${mostCommented.title} <br>
									<i class="fa fa-comments-o" aria-hidden="true">${mostCommented.rating}</i></a>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="single_sidebar">
				<div class="single_sidebar wow fadeInDown">
					<h2>
						<span>Sponsor</span>
					</h2>
					<a class="sideAdd" href="http://ittalents.bg/"><img
						src="images/logo-black.png" alt=""></a>
				</div>
			</div>
			</aside>
		</div>
	</div>
	</section>
	<jsp:include page="user_panel/footer.jsp"></jsp:include>

	<script type="text/javascript">
		$(document).ready(function(){
			
		

			$('.heart').click(function (e) {
			
				var id = $(this).data().id;
				console.log(id)
				if( $(this).attr('class').indexOf('fa fa-heart-o heart') !== -1){
					$(this).attr('class', 'fa fa-heart heart' );
					
					$.ajax({
                        url: "./AddToFavourites?id=" + id,
                        method: 'GET',
                
                    });
				}
				else {
					$(this).attr('class', 'fa fa-heart-o heart' );
					$.ajax({
                        url: "./RemoveFavourites?id=" + id,
                        method: 'GET',
                      
                    });
				}
				
				
			});
				
			
			
			$('.deleteNews').click(function(e) {
				if (!confirm('Are you sure you want to delete this news?')) {
					e.preventDefault();
				}

			});
		    
		});
	    
	    
		
	</script>