<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="preloader">
	<div id="status">&nbsp;</div>
</div>
<a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
<div class="container">
	<header id="header">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="header_top">
					<div class="header_top_left">
						<ul class="top_nav">
							<c:choose>
								<c:when test="${not empty sessionScope.isAdmin}">
									<li><a href="AdminPanel">Admin Panel</a></li>
									<li><a href="Logout">Logout</a></li>
								</c:when>
								<c:when test="${not empty sessionScope.user}">

									<li><a href="Logout">Logout</a></li>
								</c:when>
								<c:otherwise>
									<li><a id="modal_trigger" href="Login">Login</a></li>
									<li><a id="modal_trigger" href="Register">Register</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<div class="header_top_right">
					<h3>
					<iframe src="http://free.timeanddate.com/clock/i5etg7b8/n238/tluk/fn9/fs16/fcfff/tct/pct/ahr/avt/tt0/tw1" frameborder="0" width="256" height="20" allowTransparency="true"></iframe>
					</h3>
					<%--<%
							if (session.getAttribute("user") != null) {
						%>
						<p style="font-size: 15px">Welcome, you are logged as
							${user.username}</p>
						<%}%>--%>
					</div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="header_bottom">
					<div class="logo_area">
						<a href="./AdminPanel" class="logo"><img
							src="./images/topNewsLogo.png" alt=""></a>
					</div>
					<div class="add_banner">
						<a href="#"><img src="./images/top_news_banner.png" alt=""></a>
					</div>
				</div>
			</div>
		</div>
	</header>