<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>User Login</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<div class="mt-2 container">
	    <% response.setHeader("Cache-Control","no-cache"); %>
	    <% response.setHeader("Cache-Control","no-store"); %>
	    <% response.setHeader("Pragma","no-cache"); %>
	    <% response.setDateHeader ("Expires", 0); %>
		<% if (session != null && session.getAttribute("user") != null) { %>
			<script>
			    function() {
					window.location.href = "/UserProfile.jsp"
			    }
		    </script>
		<% } else { %>
		<form action="UserLogin" method="post">
			<div class="form-group">
				<label for="userName" class="row">User Name: </label>
				<input id="userName" class="row col-6" name="userName" value="" />
				<p id="alertUserName" style="color: red">${alerts.userName}</p>
			</div>
			<div class="form-group">
				<label for="password" class="row">Password: </label>
				<input id="password" type="password" class="row col-6" name="password" value="" />
				<p id="alertPassword" style="color: red">${alerts.password}</p>
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary row col-6">Login</button>
			</div>
			<div class="form-group">
				<p id="alertLogin" style="color: red">${alerts.login}</p>
			</div>
		</form>
		<% } %>
	</div>
</body>
</html>