<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
          <li><a href="./">BACK TO SITE</a></li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">SHOW NEWS BY CATEGORY</a>
            <ul class="dropdown-menu" role="menu">
             <c:forEach var="categories" items="${categories}">
              <li><a href="Category?name=${categories}">${categories}</a></li>
              </c:forEach>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">NEWS EDIT</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AddNews">ADD NEWS</a></li>
              <li><a href="AddCategory">CREATE NEW CATEGORY</a></li>
              <li><a style="color: red" href="DeleteCategory">DELETE EXISTING CATEGORY</a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">USERS</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AddUserRights">ADD ADMIN RIGHTS</a></li>
                <li><a href="RemoveUserRights">REMOVE ADMIN RIGHTS</a></li>
              <li><a style="color: red" href="DeleteUser">DELETE USER</a></li>
            </ul>
          </li>
          <li><a href="./Logout">LOGOUT</a></li>
        </ul>
  </section> 