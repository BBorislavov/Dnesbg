<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="WelcomeToTopNews" text="Welcome to Top News" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="./admin_panel/panel_style.jsp"></jsp:include>
<script type="text/javascript">
function Validatebodypanelbumper(theForm)
{
   var regexp;
   var extension = new FormData(theForm).get("file").value.lastIndexOf('.');
   if ((extension.toLowerCase() != ".gif") &&
       (extension.toLowerCase() != ".jpg") &&
       (extension != ""))
   {
      alert("The \"FileUpload\" field contains an unapproved filename.");
      theForm.file.focus();
      return false;
   }
   return true;
}
</script>
</head>
<body>
<<jsp:include page="./user_panel/header.jsp"></jsp:include>
 <jsp:include page="./user_panel/navigation.jsp"></jsp:include>
 <section id="newsSection">
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="latest_newsarea">
				<span><spring:message code="LatestNews" text="Latest News" /></span>
				<ul id="ticker01" class="news_sticker">
				 <c:forEach var="latestNews" items="${latestNews}">
					<li><a href="#"><img src="${latestNews.photoUrl}"
							alt="">${latestNews.title}</a></li>
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
       <div class="col-lg-7 col-md-7 col-sm-7">
			<div class="left_content">
				<div class="contact_area">
					<h2><spring:message code="alertUs" text="Alert us" /></h2>
					<br /> 
					<h3 style="color: green"><spring:message code="${message}" text="" /></h3>
					<h3 style="color: red"><spring:message code="${error}" text="" /></h3>
					<form:form class="contact_form" method="POST" action="Alert" enctype="multipart/form-data" onsubmit="Validatebodypanelbumper()" commandName="email">
						<h4><spring:message code="subject" text="Subject" /></h4>
						<form:input path="subject" class="form-control" type="text"/>
						<h4><spring:message code="photoUpload" text="Photo Upload" /></h4>
						<form:input path="photo"  id="file" name="file" class="form-control" type="file"
							accept="image/*" />
						<br />
						<h4><spring:message code="description" text="Description" /></h4>
						<form:textarea path="text" class="form-control" name="text" cols="30" rows="10"/> 
						<input type="submit" value="<spring:message code="alert" text="Alert" />">
					</form:form>
				</div>
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
 <jsp:include page="user_panel/footer.jsp"></jsp:include>

