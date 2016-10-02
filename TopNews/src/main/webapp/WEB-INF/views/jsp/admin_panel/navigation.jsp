<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<section id="navArea">
    <nav class="navbar navbar-inverse" role="navigation">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav main_nav">
          <li class="active"><a href="admin_panel"><span class="fa fa-home desktop-home"></span><span class="mobile-show">Home</span></a></li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">SHOW NEWS BY CATEGORY</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AdminPanel?Category=World">WORLD</a></li>
              <li><a href="AdminPanel/Category/Bulgaria">BULGARIA</a></li>
              <li><a href="AdminPanel/Category/Sport">SPORT</a></li>
              <li><a href="AdminPanel/Category/Weather">WEATHER</a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">NEWS EDIT</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="admin_panel?news=add">ADD NEWS</a></li>
              <li><a href="./admin_panel?news=remove">REMOVE NEWS</a></li>
              <li><a href="./admin_panel?news=edit">EDIT NEWS</a></li>
              <li><a href="./admin_panel?news=photo">ADD/CHANGE PHOTO</a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">USERS</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="./admin_panel?userOption=delete">DELETE USER</a></li>
              <li><a href="./admin_panel?userOption=comments">DELETE COMMENTS</a></li>
              <li><a href="./admin_panel?userOption=rights">USER ADD/REMOVE ADMIN RIGHTS</a></li>
            </ul>
          </li>
          <li><a href="./Logout">LOGOUT</a></li>
        </ul>
      </div>
    </nav>
  </section>
</body>
</html>