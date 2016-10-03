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
 <jsp:include page="admin_panel/header.jsp"></jsp:include>
 <jsp:include page="admin_panel/navigation.jsp"></jsp:include>
 <h1>All news for ${name} category</h1>
 <div class="container">
 <ul>
 <c:forEach var="listCategory" items="${news}">
 <li><img height="50" width="50" src="${listCategory.photoUrl}"></li>
 <li><a href="./News?category=${name}&id=${listCategory.id}">${listCategory.title}</a></li>
 <li>${listCategory.dateOfPost}</li>
 </br>
  </c:forEach>
 </ul>
 </div>
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