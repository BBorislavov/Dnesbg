<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<section id="navArea">
    <nav class="navbar navbar-inverse" role="navigation">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav main_nav">
          <li class="active"><a href="./AdminPanel"><span class="fa fa-home desktop-home"></span><span class="mobile-show">Home</span></a></li>
          <li><a href="./"><spring:message code="backToSite" text="Back to site" /></a></li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="ShowNewsByCategory" text="Show news by category" /></a>
            <ul class="dropdown-menu" role="menu">
             <c:forEach var="categories" items="${categories}">
              <li><a href="Category?name=${categories}&page=1"><spring:message code="${categories}" text="${categories}" /></a></li>
              </c:forEach>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="newsEdit" text="News Edit" /></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AddNews"><spring:message code="addNews" text="Add News" /></a></li>
              <li><a href="AddCategory"><spring:message code="CreateCategory" text="Create New Category" /></a></li>
              <li><a style="color: red" href="DeleteCategory"><spring:message code="DeleteExisting" text="Delete Existing Category" /></a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="Users" text="Users" /></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AddUserRights"><spring:message code="AddRights" text="Add admin rights" /></a></li>
                <li><a href="RemoveUserRights"><spring:message code="RemRights" text="Remove admin rights" /></a></li>
              <li><a style="color: red" href="DeleteUser"><spring:message code="DeleteUser" text="Delete user" /></a></li>
            </ul>
          </li>
        </ul>
  </section> 