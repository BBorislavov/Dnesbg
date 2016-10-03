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
          <li class="active"><a href="AdminPanel"><span class="fa fa-home desktop-home"></span><span class="mobile-show">Home</span></a></li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">SHOW NEWS BY CATEGORY</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="Category?name=World">WORLD</a></li>
              <li><a href="Category?name=Bulgaria">BULGARIA</a></li>
              <li><a href="Category?name=Politics">BULGARIA: POLITICS</a></li>
              <li><a href="Category?name=Education">BULGARIA: EDUCATION</a></li>
              <li><a href="Category?name=Sofia">BULGARIA: SOFIA</a></li>
              <li><a href="Category?name=Sport">SPORT</a></li>
              <li><a href="Category?name=Football">SPORT: FOOTBALL</a></li>
              <li><a href="Category?name=Volleyball">SPORT: VOLLEYBALL</a></li>
              <li><a href="Category?name=Tennis">SPORT: TENNIS</a></li>
              <li><a href="Category?name=Other">SPORT: OTHER SPORTS</a></li>
              <li><a href="Category?name=Health">HEALTH</a></li>
              <li><a href="Category?name=Business">BUSINESS</a></li>
              <li><a href="Category?name=Technologies">TECHNOLOGIES</a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">NEWS EDIT</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="AddNews">ADD NEWS</a></li>
              <li><a href="AdminPanel/AddNews">EDIT NEWS</a></li>
              <li><a href="AdminPanel/AddPhoto">ADD/CHANGE PHOTO</a></li>
              <li><a href="AdminPanel/AddNews">CREATE NEW CATEGORY</a></li>
              <li><a style="color: red" href="AdminPanel/AddNews">DELETE EXISTING CATEGORY</a></li>
              <li><a style="color: red" href="DeleteNews">DELETE NEWS</a></li>
            </ul>
          </li>
          <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">USERS</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="./admin_panel?userOption=rights">USER ADD/REMOVE ADMIN RIGHTS</a></li>
              <li><a style="color: red" href="./admin_panel?userOption=comments">DELETE COMMENTS</a></li>
              <li><a style="color: red" href="./admin_panel?userOption=delete">DELETE USER</a></li>
            </ul>
          </li>
          <li><a href="./Logout">LOGOUT</a></li>
        </ul>
      </div>
    </nav>
  </section>
</body>
</html>