<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="WelcomeToTopNews" text="default text" /></title>
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
				<span><spring:message code="LatestNews" text="default text" /></span>
				<ul id="ticker01" class="news_sticker">
				 <c:forEach var="latestNews" items="${latestNews}">
					<li><a href="#"><img src="${latestNews.photoUrl}"
							alt="">${latestNews.title}</a></li>
				</c:forEach>
				</ul>
				<div class="social_area">
					<ul class="social_nav">
						<li class="facebook"><a href="http://www.facebook.com" target="_blank"></a></li>
						<li class="twitter"><a href="https://twitter.com/" target="_blank"></a></li>
						<li class="flickr"><a href="https://www.flickr.com/" target="_blank"></a></li>
						<li class="pinterest"><a href="https://www.pinterest.com/" target="_blank"></a></li>
						<li class="googleplus"><a href="https://plus.google.com/" target="_blank"></a></li>
						<li class="vimeo"><a href="https://vimeo.com/" target="_blank"></a></li>
						<li class="youtube"><a href="https://www.youtube.com/" target="_blank"></a></li>
						<li class="mail"><a href="https://www.google.com/gmail/" target="_blank"></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	</section>
   <section id="sliderSection">
    <div class="row">
      <section id="contentSection">
    <div class="row">
      <div class="col-lg-8 col-md-8 col-sm-8">
        <div class="left_content">
          <div class="error_page">
            <h3><spring:message code="Sorry" text="We are sorry" /></h3>
            <h1>404</h1>
            <h3 style="color: red"><spring:message code="${message}" text="" /></h3><br>
            <h3 style="color: red"><spring:message code="${invalidImage}" text="${invalidImage}" /></h3><br>
            <span></span> <a href="./" class="wow fadeInLeftBig"><spring:message code="Homepage" text="Go to homepage" /></a> </div>
        </div>
      </div>
  <div class="col-lg-4 col-md-4 col-sm-4">
        <div class="latest_post">
          <h2><span><spring:message code="PopularNews" text="default text" /></span></h2>
          <div class="latest_post_container">
            <div id="prev-button"><i class="fa fa-chevron-up"></i></div>
            <ul class="latest_postnav">
            <c:forEach var="popularNews" items="${popularNews}">
              <li>
                <div class="media"> <a href="News?category=${popularNews.category}&id=${popularNews.id}" class="media-left"> <img alt="" src="${popularNews.photoUrl}"> </a>
                  <div class="media-body"> <a href="News?category=${popularNews.category}&id=${popularNews.id}" class="catg_title">${popularNews.title} <br><span><i class="fa fa-eye"></i>${popularNews.rating}</span></a> </div>
                </div>
              </li>
              </c:forEach>
            </ul>
            <div id="next-button"><i class="fa  fa-chevron-down"></i></div>
          </div>
        </div>
      </div>
    </div>
  </section>
        </div>
  </section>
 <jsp:include page="user_panel/footer.jsp"></jsp:include>

