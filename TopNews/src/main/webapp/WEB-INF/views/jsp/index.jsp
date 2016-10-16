<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="WelcomeToTopNews"
		text="default text" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="./admin_panel/panel_style.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="./user_panel/header.jsp"></jsp:include>
	<jsp:include page="./user_panel/navigation.jsp"></jsp:include>
	<section id="newsSection">
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="latest_newsarea">
				<span><spring:message code="LatestNews" text="Latest News" /></span>
				<ul id="ticker01" class="news_sticker">
					<c:forEach var="latestNews" items="${latestNews}">
						<li><a
							href="News?category=${latestNews.category}&id=${latestNews.id}"><img
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
				<c:forEach var="allNews" items="${allNews}">
					<div class="single_iteam">
						<a href="News?category=${allNews.category}&id=${allNews.id}">
							<img src="${allNews.photoUrl}" alt="">
						</a>
						<div class="slider_article">
							<h2>
								<a class="slider_tittle" href="pages/single_page.html">|
									${allNews.category} | -> ${allNews.title}</a>
							</h2>
							<p>${allNews.text}</p>
						</div>
					</div>
				</c:forEach>
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
											class="catg_title">${popularNews.title} <br>
										<span><i class="fa fa-eye"></i>${popularNews.rating}</span></a>
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
	</div>
	</section>
	<section id="contentSection">
	<div class="row">
		<div class="col-lg-8 col-md-8 col-sm-8">
			<div class="left_content">
				<div class="single_post_content">
					<h2>
						<span><spring:message code="Sport" text="Sport" /></span>
					</h2>
					<div class="single_post_content_left">
						<ul class="business_catgnav  wow fadeInDown">
							<li><figure class="bsbig_fig"> <a
									href="News?category=${firstSportNews.category}&id=${firstSportNews.id}"
									class="featured_img"> <img alt=""
									src="${firstSportNews.photoUrl}"> <span class="overlay"></span>
								</a> <figcaption> <a
									href="News?category=${firstSportNews.category}&id=${firstSportNews.id}">${firstSportNews.title}</a>
								</figcaption>
								<p style="text-align: justify;">${firstSportNews.text}</p>
								</figure></li>
						</ul>
					</div>
					<div class="single_post_content_right">
						<ul class="spost_nav">
							<c:forEach var="sportNews" items="${sportNews}">
								<li>
									<div class="media wow fadeInDown">
										<a
											href="News?category=${sportNews.category}&id=${sportNews.id}"
											class="media-left"> <img alt=""
											src="${sportNews.photoUrl}">
										</a>
										<div class="media-body">
											<a
												href="News?category=${sportNews.category}&id=${sportNews.id}"
												class="catg_title">
												<h4>${sportNews.title}</h4>
											</a>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="fashion_technology_area">
					<div class="fashion">
						<div class="single_post_content">
							<h2>
								<span><spring:message code="Business" text="Business" /></span>
							</h2>
							<ul class="business_catgnav wow fadeInDown">
								<li><figure class="bsbig_fig"> <a
										href="News?category=${firstBusinessNews.category}&id=${firstBusinessNews.id}"
										class="featured_img"> <img alt=""
										src="${firstBusinessNews.photoUrl}"> <span
										class="overlay"></span>
									</a> <figcaption> <a
										href="News?category=${firstBusinessNews.category}&id=${firstBusinessNews.id}">${firstBusinessNews.title}</a>
									</figcaption>
									<p style="text-align: justify;">${firstBusinessNews.text}</p>
									</figure></li>
							</ul>
						</div>
					</div>
					<div class="technology">
						<div class="single_post_content">
							<h2>
								<span><spring:message code="Technologies"
										text="Technologies" /></span>
							</h2>
							<ul class="business_catgnav">
								<li><figure class="bsbig_fig wow fadeInDown"> <a
										href="News?category=${firstTechnologiesNews.category}&id=${firstTechnologiesNews.id}"
										class="featured_img"> <img alt=""
										src="${firstTechnologiesNews.photoUrl}"> <span
										class="overlay"></span>
									</a> <figcaption> <a
										href="News?category=${firstTechnologiesNews.category}&id=${firstTechnologiesNews.id}">${firstTechnologiesNews.title}</a>
									</figcaption>
									<p style="text-align: justify;">${firstTechnologiesNews.text}</p>
									</figure></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="single_post_content">
					<h2>
						<span><spring:message code="Bulgaria" text="Bulgaria" /></span>
					</h2>
					<div class="single_post_content_left">
						<ul class="business_catgnav  wow fadeInDown">
							<li><figure class="bsbig_fig"> <a
									href="News?category=${firstBulgariaNews.category}&id=${firstBulgariaNews.id}"
									class="featured_img"> <img alt=""
									src="${firstBulgariaNews.photoUrl}"> <span
									class="overlay"></span>
								</a> <figcaption> <a
									href="${firstBulgariaNews.category}&id=${firstBulgariaNews.id}">${firstBulgariaNews.title}</a>
								</figcaption>
								<p style="text-align: justify;">${firstBulgariaNews.text}</p>
								</figure></li>
						</ul>
					</div>
					<div class="single_post_content_right">
						<ul class="spost_nav">
							<c:forEach var="bulgariaNews" items="${bulgariaNews}">
								<li>
									<div class="media wow fadeInDown">
										<a
											href="News?category=${bulgariaNews.category}&id=${bulgariaNews.id}"
											class="media-left"> <img alt=""
											src="${bulgariaNews.photoUrl}">
										</a>
										<div class="media-body">
											<a
												href="News?category=${bulgariaNews.category}&id=${bulgariaNews.id}"
												class="catg_title">
												<h4>${bulgariaNews.title}</h4>
											</a>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4">
			<aside class="right_content">
			<div class="single_sidebar">
            <h2><span>Most commented news</span></h2>
            <ul class="spost_nav">
             <c:forEach var="mostCommented" items="${mostCommented}">
               <li>
                <div class="media wow fadeInDown"> <a href="News?category=${mostCommented.category}&id=${mostCommented.id}" class="media-left"> <img alt="" src="${mostCommented.photoUrl}"> </a>
                  <div class="media-body"> <a href="News?category=${mostCommented.category}&id=${mostCommented.id}" class="catg_title">${mostCommented.title} <br><i class="fa fa-comments-o" aria-hidden="true">${mostCommented.rating}</i></a> </div>
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
		<jsp:include page="user_panel/footer.jsp"></jsp:include>