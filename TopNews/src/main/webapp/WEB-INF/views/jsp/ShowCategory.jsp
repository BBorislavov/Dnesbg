<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>

<title>News for ${name}</title>
</head>
<body>
<div id="preloader">
  <div id="status">&nbsp;</div>
</div>
<a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
<div class="container">
  <header id="header">
    <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12">
        <div class="header_bottom">
          <div class="logo_area"><a href="/Home" class="logo"><img src="./images/logo.jpg" alt=""></a></div>
          <div class="add_banner"><a href="#"><img src="./images/banners-news.jpg" alt=""></a></div>
        </div>
      </div>
    </div>
  </header>
 <jsp:include page="admin_panel/navigation.jsp"></jsp:include>
 <h1>All news for ${name} category</h1>
 <ul>
 <c:forEach var="listCategory" items="${news}">
 <li><img height="50" width="50" src="${listCategory.photoUrl}"></li>
 <li>${listCategory.title}</li>
 <li>${listCategory.dateOfPost}</li>
 </br>
  </c:forEach>
 </ul>
 <jsp:include page="admin_panel/footer.jsp"></jsp:include>
</div>
<script src="./assets/js/jquery.min.js"></script>
<script src="./assets/js/wow.min.js"></script>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/js/slick.min.js"></script>
<script src="./assets/js/jquery.li-scroller.1.0.js"></script>
<script src="./assets/js/jquery.newsTicker.min.js"></script>
<script src="./assets/js/jquery.fancybox.pack.js"></script>
<script src="./assets/js/custom.js"></script>
</body>
</html>