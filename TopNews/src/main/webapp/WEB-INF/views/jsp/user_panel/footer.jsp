<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<footer id="footer">
	<div class="footer_top">
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="footer_widget wow fadeInLeftBig">
					<h2><spring:message code="Welcome" text="default text" /></h2>
					<p><spring:message code="Hello" text="default text" />, 
					<c:if test="${not empty user.username}">${user.username}.</c:if>
					<c:if test="${empty user.username}">guest.</c:if>
					</p>
					
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="footer_widget wow fadeInDown">
					<h2><spring:message code="QuickUserPanel" text="default text" /></h2>
					<ul class="tag_nav">
						<li><a href="#">Add news</a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="footer_widget wow fadeInRightBig">
					<h2><spring:message code="About" text="default text" /></h2>
					<address>Some info about the site.</address>
				</div>
			</div>
		</div>
	</div>
	<div class="footer_bottom">
		<p class="copyright">
			<spring:message code="Copyright" text="default text" /> &copy; 2016 <a href="./">TopNews</a>
		</p>
		<p style="color: lightgray" class="developer"><spring:message code="DevelopedBy" text="default text" /> Zdravko & Borislav / <spring:message code="IT-Talents" text="default text" /></p>
	</div>
</footer>
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