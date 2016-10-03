<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
<title>Add News</title>
</head>
<body>
<div id="preloader">
  <div id="status">&nbsp;</div>
</div>
<a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
<div class="container">
 <jsp:include page="admin_panel/header.jsp"></jsp:include>
 <jsp:include page="admin_panel/navigation.jsp"></jsp:include>
   <section id="contentSection">
    <div class="row">
      <div class="col-lg-7 col-md-7 col-sm-7">
        <div class="left_content">
          <div class="contact_area">
            <h2>Add News</h2>
            <br/>
            <form:form action="AddNews" class="contact_form" commandName="news">
            <form:select path="category" class="form-control" type="text">
            <optgroup label="Categories:"></optgroup>
  			<option value="World">World</option>
  			<optgroup label="Bulgaria"></optgroup>
  			<option value="Politics">Politics</option>
 			<option value="Education">Education</option>
 			<option value="Sofia">Sofia</option>
 			<optgroup label="Sport"></optgroup>
 			<option value="Football">Football</option>
 			<option value="Volleyball">Volleyball</option>
 			<option value="Tennis">Tennis</option>
 			<option value="Other">Other sports</option>
 			<optgroup label="Other Categories"></optgroup>
 			<option value="Health">Health</option>
 			<option value="Business">Business</option>
 			<option value="Technologies">Technologies</option>
 			</form:select>
 			<br/>
            <form:input path="title" class="form-control" type="text" placeholder="Enter title*"/>
              <h4>Photo upload</h4>
            <form:input path="photoUrl" class="form-control" type="file" accept="image/*"/>
              <br/>
            <form:textarea path="text" class="form-control" name="text" cols="30" rows="10" placeholder="Enter text here*"/></textarea>
              <input type="submit" value="Add to Site">
            </form:form>
          </div>
        </div>
      </div>
    </div>
  </section>
 <jsp:include page="admin_panel/footer.jsp"></jsp:include>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/wow.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/slick.min.js"></script>
<script src="assets/js/jquery.li-scroller.1.0.js"></script>
<script src="assets/js/jquery.newsTicker.min.js"></script>
<script src="assets/js/jquery.fancybox.pack.js"></script>
<script src="assets/js/custom.js"></script>
</body>
</html>