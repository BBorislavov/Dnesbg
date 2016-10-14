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
					<c:if test="${empty user.username}"><spring:message code="guest" text="default text"/>.</c:if>
					</p>
					<br>
					<p style="text-align: justify;"><spring:message code="WelcomeText" text="defsault text" /></p>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="footer_widget wow fadeInDown">
					<h2><spring:message code="Contacts" text="default text" /></h2>
					<ul class="tag_nav">
						<li><a href="" id="call"><spring:message code="CallUs" text="default text" /></a></li>
						<li><a href="" id="address"><spring:message code="Address" text="default text" /></a></li>
					</ul>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-4">
				<div class="footer_widget wow fadeInRightBig">
					<h2><spring:message code="About" text="default text" /></h2>
					<address style="text-align: justify;"><spring:message code="AboutUs" text="default text" /></address>
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

<div id="Calls" class="modal">
  <div class="modal-content">
    <span class="close"><spring:message code="CallUs" text="Call Us" /></span>
    <h3><img width="25px" height="25px" src="images/phone.png"> +359 888 99 88 77 - Sofia, Bulgaria</h3>
    <h3><img width="25px" height="25px" src="images/phone.png"> +447 722 24 41 82 - London, England</h3>
    <h3><img width="25px" height="25px" src="images/phone.png"> +349 17 81 81 81 - Madrid, Spain</h3>
  </div>
</div>
<div id="Addresses" class="modal">
  <div class="modal-content">
    <span class="close"><spring:message code="Address" text="Address" /></span>
    <h3><img width="25px" height="25px" src="images/address.png"> Bulgaria, Sofia. blvd. Bulgaria, N:133</h3>
    <h3><img width="25px" height="25px" src="images/address.png"> England, London. Monck Street, N:31-35</h3>
    <h3><img width="25px" height="25px" src="images/address.png"> Spain, Madrid, Paseo de la Castellana, N:20</h3>
  </div>
</div>

<script>

var modal = document.getElementById('Calls');
var modalTwo = document.getElementById('Addresses');

var btn = document.getElementById("call");
var btnTwo = document.getElementById('address');

var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
	event.preventDefault();
    modal.style.display = "block";
}
btnTwo.onclick = function() {
	event.preventDefault();
    modalTwo.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
    if (event.target == modalTwo){
        modalTwo.style.display = "none";
    }
}
</script>
</body>
</html>