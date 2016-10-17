<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="addNews" text="Add News" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="admin_panel/panel_style.jsp"></jsp:include>
<script type="text/javascript">
	function Validatebodypanelbumper(theForm) {
		var regexp;
		var extension = new FormData(theForm).get("file").value
				.lastIndexOf('.');
		if ((extension.toLowerCase() != ".gif")
				&& (extension.toLowerCase() != ".jpg") && (extension != "")) {
			alert("The \"FileUpload\" field contains an unapproved filename.");
			theForm.file.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<jsp:include page="admin_panel/header.jsp"></jsp:include>
	<jsp:include page="admin_panel/navigation.jsp">
		<jsp:param value="${categories}" name="categories" />
	</jsp:include>
	<section id="contentSection">
	<div class="row">
		<div class="col-lg-7 col-md-7 col-sm-7">
			<div class="left_content">
				<div class="contact_area">
					<h2>
						<spring:message code="addNews" text="Add News" />
					</h2>
					<h3 style="color: green">
						<spring:message code="${message}" text="" />
					</h3>
					<h3 style="color: red">
						<spring:message code="${error}" text="" />
					</h3>
					<h4>
						<span><spring:message code="Category" text="Category" />:</span>
					</h4>
					<form:form class="contact_form" method="POST" action="AddNews"
						enctype="multipart/form-data" onsubmit="Validatebodypanelbumper()"
						commandName="news">
						<form:select path="category" class="form-control" type="text">
							<c:forEach var="subcategories" items="${subcategories}">
								<option value="${subcategories}"><spring:message
										code="${subcategories}" text="${subcategories}" /></option>
							</c:forEach>
						</form:select>
						<h4>
							<spring:message code="title" text="Category" />
						</h4>
						<form:input path="title" class="form-control" type="text" />
						<h4>
							<spring:message code="photoUpload" text="Category" />
						</h4>
						<form:input path="photoUrl" id="file" name="file"
							class="form-control" type="file" accept="image/*" />
						<h4>
							<spring:message code="description" text="Category" />
						</h4>
						<form:textarea path="text" class="form-control" name="text"
							cols="30" rows="10" />
						<input type="submit"
							value="<spring:message code="addToSite" text="Add to site" />">
					</form:form>
				</div>
			</div>
		</div>
	</div>
	</section>
	<jsp:include page="user_panel/footer.jsp"></jsp:include>