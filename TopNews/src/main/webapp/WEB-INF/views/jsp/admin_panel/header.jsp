<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
								<c:if test="${not empty sessionScope.isAdmin}">
									<li><a href="Logout"><spring:message code="logout" text="Logout" /></a></li>
								</c:if>
						</ul>
					</div>
					<div class="header_top_right">
						<h3>
							<iframe
								src="http://free.timeanddate.com/clock/i5etg7b8/n238/tluk/fn9/fs16/fcfff/tct/pct/ahr/avt/tt0/tw1"
								frameborder="0" width="256" height="20" allowTransparency="true"></iframe>
						</h3>
					</div>
				</div>
			</div>
      <div class="col-lg-12 col-md-12 col-sm-12">
        <div class="header_bottom">
          <div class="logo_area"><a href="./AdminPanel" class="logo"><img src="./images/topNewsLogo.png" alt=""></a>
          <a href="./Language?language=en"><img height="14%" width="14%" src="./images/en-flag.png"></a>
							<a href="./Language?language=es"><img height="14%" width="14%" src="./images/es-flag.png"></a></div>
          <div class="add_banner"><a href="#"><img src="./images/banner.jpg" alt=""></a></div>
        </div>
      </div>
    </div>
  </header>